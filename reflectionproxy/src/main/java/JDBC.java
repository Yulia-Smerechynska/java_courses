import java.sql.*;
import java.util.ArrayList;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class JDBC {

    public static void main(String[] args) {
        ArrayList<String> users = new ArrayList<>();

        UserDb userDb = new UserDb();
        MethodInterceptor handler = new Handler(userDb);
        UserDb proxyClass = (UserDb) Enhancer.create(UserDb.class, handler);

        try {
            proxyClass.setNewUser(" ", 41);
            users = UserDb.getUsersNames();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println(users);
        }
    }
}
