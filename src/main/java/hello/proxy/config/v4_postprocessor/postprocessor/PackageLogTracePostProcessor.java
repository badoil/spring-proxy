package hello.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTracePostProcessor  implements BeanPostProcessor {

    private final String basePackage;

    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        log.info("bean: {}, beanName: {}", bean.getClass(), beanName);
        // 프록시 대상 여부 체크
        String packgeName = bean.getClass().getPackageName();
        // 패키지 이름이 basePackage와 다르면 그냥 빈에 등록
        if(!packgeName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시 대상이면 프록시를 컨테이너에 등록
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy");
        return proxy;
    }

}
