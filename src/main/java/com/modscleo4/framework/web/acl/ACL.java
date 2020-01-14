package com.modscleo4.framework.web.acl;

public abstract class ACL {
    public abstract boolean pass();

    public abstract void fail() throws Exception;
}
