package mybeans;

import javax.ejb.Stateless;

/**
 * Created by guolei on 12/7/14.
 */
@Stateless(name = "HelloWorldEJB")
public class HelloWorldBean {
    public HelloWorldBean() {
    }
    public String sayHello() {
        return "Hello, World!";
    }
}
