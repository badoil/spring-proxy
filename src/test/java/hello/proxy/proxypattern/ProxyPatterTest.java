package hello.proxy.proxypattern;

import hello.proxy.proxypattern.code.ProxyPatterClient;
import hello.proxy.proxypattern.code.Subject;
import hello.proxy.proxypattern.code.SubjectImpl;
import org.junit.jupiter.api.Test;

public class ProxyPatterTest {

    @Test
    void noProxy () {
        Subject subject = new SubjectImpl();
        ProxyPatterClient client = new ProxyPatterClient(subject);

        client.execute();
        client.execute();
        client.execute();
    }
}
