package hello.proxy.proxypattern;

import hello.proxy.proxypattern.code.CacheProxy;
import hello.proxy.proxypattern.code.ProxyPatternClient;
import hello.proxy.proxypattern.code.Subject;
import hello.proxy.proxypattern.code.SubjectImpl;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxy () {
        Subject subject = new SubjectImpl();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void proxy() {
        Subject subject = new SubjectImpl();
        Subject proxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(proxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
