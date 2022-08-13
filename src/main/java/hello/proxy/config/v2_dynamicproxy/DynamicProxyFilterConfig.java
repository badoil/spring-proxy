package hello.proxy.config.v2_dynamicproxy;


import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private final String[] PATTERN = {"request*", "order*", "save*"};

    @Bean
    OrderControllerV1 orderControllerV1(LogTrace logTrace) {

        LogTraceFilterHandler handler = new LogTraceFilterHandler(new OrderControllerV1Impl(orderServiceV1(logTrace)), logTrace, PATTERN);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                handler);
        return proxy;
    }

    @Bean
    OrderServiceV1 orderServiceV1 (LogTrace logTrace) {

        LogTraceFilterHandler handler = new LogTraceFilterHandler(new OrderServiceV1Impl(orderRepositoryV1(logTrace)), logTrace, PATTERN);
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                handler);
        return proxy;
    }

    @Bean
    OrderRepositoryV1 orderRepositoryV1 (LogTrace logTrace) {

        OrderRepositoryV1 orderRepositoryImpl = new OrderRepositoryV1Impl();
//        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
//                new Class[]{OrderRepositoryV1.class},
//                new LogTraceFilterHandler(orderRepositoryImpl, logTrace, PATTERN));
                LogTraceFilterHandler handler = new LogTraceFilterHandler(orderRepositoryImpl, logTrace, PATTERN);
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                handler);
        return proxy;
    }
}
