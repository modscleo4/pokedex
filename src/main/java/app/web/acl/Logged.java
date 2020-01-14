package app.web.acl;

import app.web.Auth;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.acl.ACL;

import java.io.IOException;

public class Logged extends ACL {
    @Override
    public boolean pass() {
        return Auth.isLogged();
    }

    @Override
    public void fail() throws IOException {
        Main.response.sendRedirect("/login");
    }
}
