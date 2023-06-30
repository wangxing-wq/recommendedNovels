package com.novels.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novels.common.bean.Result;
import com.novels.common.properties.TokenHelper;
import com.novels.common.properties.UserInfo;
import com.novels.gateway.service.UrlBlackListService;
import com.novels.gateway.service.UserBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;


/**
 * 安全过滤器
 * 1. 入参出参的打印
 * 2. 耗时打印
 * 3. 支持黑白名单(暂时没实现数读取数据的方式)
 * 4. JWT 拦截
 * 5. 后续优化异常返回信息responseErrMsg
 * @author 22343
 */
@Slf4j
@Component
public class SecurityFilter implements GlobalFilter, Ordered {

    private static final String UNKNOWN = "unknown";
    private static final char SPLIT = ',';

    public static final String X_FORWARDED_FOR = "x-forwarded-for";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private final UserBlackListService userBlackListService;

    private final UrlBlackListService urlBlackListService;

    private final ObjectMapper objectMapper;

    private final TokenHelper tokenHelper;

    public SecurityFilter(UrlBlackListService urlBlackListService, UserBlackListService userBlackListService, ObjectMapper objectMapper, TokenHelper tokenHelper) {
        this.urlBlackListService = urlBlackListService;
        this.userBlackListService = userBlackListService;
        this.objectMapper = objectMapper;
        this.tokenHelper = tokenHelper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 分布式ID
        // 是否启用
        // 预取信息
        ServerWebExchange build = buildGlobalTraceId(exchange);
        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        HttpHeaders headers = request.getHeaders();
        String clientIp = getClientIp(headers, request.getRemoteAddress());
        log.info("来自客户端的请求信息-{},method:{},ip:{}", url, request.getMethodValue(), clientIp);
        // 校验黑名单
        if (urlBlackListService.isBlackList(url)) {
            log.info("黑名单返回处理 url:{}",url);
            return responseErrMsg(exchange,403, "forbidden request url!");
        }
        // 校验白名单
        if (urlBlackListService.isWhiteList(url)) {
            log.info("白名单请求直接放行.");
            return filter(chain, build, exchange, url);
        }
        // 从header Authorization中获取
        String authorization = headers.getFirst(TokenHelper.AUTHORIZATION);
        if (StrUtil.isEmpty(authorization)) {
            log.error("invalid token or not login! authorization:{}", authorization);
            return responseErrMsg(exchange, 401, "invalid token or not login");
        }
        // 校验头跟用户名单
        Optional<UserInfo> userInfo = tokenHelper.userInfo(authorization);
        if (!userInfo.isPresent() || userBlackListService.isBlackList(userInfo.get().getId())) {
            log.error("user id blacklist ! user Id:{}", userInfo.get().getId());
            return responseErrMsg(exchange, 401, "invalid token or not login");
        }
        return filter(chain, build, exchange, url);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 返回错误的信息
     */
    private Mono<Void> responseErrMsg(ServerWebExchange exchange,Integer code,String message) {
        String msg = null;
        try {
            msg = objectMapper.writeValueAsString(Result.fail(code, message));
        } catch (JsonProcessingException e) {
            msg = "";
        }
        DataBuffer buffer = responseMsg(exchange, msg);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    private Mono<Void> filter(GatewayFilterChain chain, ServerWebExchange build, ServerWebExchange exchange, String url) {
        return chain.filter(build).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute("start_time");
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);
                log.info("请求结束:{},总耗时:{}ms", url, executeTime);
            }
        }));
    }

    /**
     * 设置返回信息
     */
    private DataBuffer responseMsg(ServerWebExchange exchange, String msg) {
        ServerHttpResponse origin = exchange.getResponse();
        origin.setStatusCode(HttpStatus.OK);
        origin.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
        byte[] response = msg.getBytes(StandardCharsets.UTF_8);
        return origin.bufferFactory().wrap(response);
    }

    /**
     * 功能: 插入全局的traceId
     */
    private ServerWebExchange buildGlobalTraceId(ServerWebExchange exchange) {
//        TraceHelper.clearCurrentTrace();
//        TraceHelper.getCurrentTrace();
//        String[] headerArray = {MDC.get(Trace.TRACE)};
//        ServerHttpRequest req = exchange.getRequest().mutate().header(TraceHttpHeaderEnum.HEADER_TRACE_ID.getCode(),
//                headerArray).build();
        ServerHttpRequest req = exchange.getRequest();
        return exchange.mutate().request(req).build();
    }

    /**
     * 获取真实IP
     *
     * @param headers       http请求头参数
     * @param remoteAddress Inet套接字地址
     * @return ip
     */
    private static String getClientIp(HttpHeaders headers, InetSocketAddress remoteAddress) {
        String ip = headers.getFirst(X_FORWARDED_FOR);
        if (StrUtil.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(SPLIT) != -1) {
                ip = ip.split(String.valueOf(SPLIT))[0];
            }
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(WL_PROXY_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_CLIENT_IP);
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = headers.getFirst(HTTP_X_FORWARDED_FOR);
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            InetSocketAddress host = headers.getHost();
            ip = Objects.isNull(host) ? "" :host.getHostString();
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            if (remoteAddress != null) {
                ip = remoteAddress.getAddress().getHostAddress();
            }
        }
        return ip;
    }

}
