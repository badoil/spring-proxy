package hello.proxy.proxypattern.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubjectImpl implements Subject{
    @Override
    public String operation() {
        log.info("객체호출");
        sleep(1000);
        return "data";
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
