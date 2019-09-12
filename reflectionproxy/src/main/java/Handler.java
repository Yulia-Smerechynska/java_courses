import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Handler implements MethodInterceptor {
    private final UserDb userDb;

    public Handler(UserDb userDb) {
        this.userDb = userDb;
    }

    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if(args[0] == " ") {
            System.out.println("Whitespace is forbidden for using as name");
            return null;
        }
        method.invoke(userDb, args);
        return null;
    }
}
