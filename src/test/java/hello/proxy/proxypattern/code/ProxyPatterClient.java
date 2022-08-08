package hello.proxy.proxypattern.code;

public class ProxyPatterClient {

    private final Subject subject;

    public ProxyPatterClient(Subject subject) {
        this.subject = subject;
    }

    public void execute(){
        subject.operation();
    }
}
