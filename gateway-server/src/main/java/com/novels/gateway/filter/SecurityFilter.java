package com.novels.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novels.gateway.service.UrlBlackListService;
import com.novels.gateway.service.UserBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

import static com.sun.org.glassfish.external.statistics.impl.StatisticImpl.START_TIME;

/**
 * 安全校验
 *
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

    public static final String TOKEN = "token";

    private final UserBlackListService userBlackListService;

    private final UrlBlackListService urlBlackListService;

    private final ObjectMapper objectMapper;

    public SecurityFilter(UrlBlackListService urlBlackListService, UserBlackListService userBlackListService, ObjectMapper objectMapper) {
        this.urlBlackListService = urlBlackListService;
        this.userBlackListService = userBlackListService;
        this.objectMapper = objectMapper;
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
//            TODO 黑名单返回处理
        }
        // 校验白名单
        if (urlBlackListService.isWhiteList(url)) {
//            TODO 白名单返回处理
        }
//        校验JWT
        if (StrUtil.isNotBlank(headers.getFirst(TOKEN))) {
            // 校验头跟用户名单
        }
        return filter(chain,build,exchange,url);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> filter(GatewayFilterChain chain, ServerWebExchange build, ServerWebExchange exchange,String url){
        return chain.filter(build).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                Long executeTime = (System.currentTimeMillis() - startTime);
                log.info("请求结束:{},总耗时:{}ms", url, executeTime);
            }
        }));
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
            ip = headers.getHost().getHostString();
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            if (remoteAddress != null) {
                ip = remoteAddress.getAddress().getHostAddress();
            }
        }
        return ip;
    }

}
