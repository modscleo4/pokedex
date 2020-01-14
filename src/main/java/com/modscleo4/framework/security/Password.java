package com.modscleo4.framework.security;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    public static boolean matches(String hash, String password) {
        return BCrypt.checkpw(password, hash);
    }

    public static String generateHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}
