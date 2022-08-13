package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;

    private final LogTrace logTrace;

    private final String[] PATTERN;

    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] pattern) {
        this.target = target;
        this.logTrace = logTrace;
        PATTERN = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            String methodName = method.getName();
            if (!PatternMatchUtils.simpleMatch(PATTERN, methodName)) {
                log.info("no -log");
                return method.invoke(target, args);         // 패턴에 안맞는 메소드면 르그 안남기고, 메소드 실행시키고 바로 리턴
            }

            String message = method.getDeclaringClass().getSimpleName()+'.'+method.getName()+ "()";
            status = logTrace.begin(message);

            Object result = method.invoke(target, args);
//            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
