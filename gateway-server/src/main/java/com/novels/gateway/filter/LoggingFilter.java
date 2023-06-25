package com.novels.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author 22343
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();
        // 执行前置过滤逻辑，如打印请求日志
        log.info("Received request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI().getPath());
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // 执行后置过滤逻辑，如打印耗时统计信息
                    log.info("Request completed in {} ms", System.currentTimeMillis() - startTime);
                }));
    }

    @Override
    public int getOrder() {
        // 设置过滤器执行顺序
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
