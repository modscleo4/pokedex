package app.web.acl;

import app.web.Auth;
import com.modscleo4.framework.web.Main;
import com.modscleo4.framework.web.acl.ACL;

import javax.servlet.http.HttpServletResponse;

public class Admin extends ACL {
    @Override
    public boolean pass() {
        try {
            return Auth.isAdmin();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void fail() throws Exception {
        Main.response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
