package app.web.acl;

import app.web.Auth;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.acl.ACL;

public class Guest extends ACL {
    @Override
    public boolean pass() {
        return !Auth.isLogged();
    }

    @Override
    public void fail() throws Exception {
        Main.response.sendRedirect("/home");
    }
}
