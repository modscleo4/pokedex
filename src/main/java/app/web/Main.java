package app.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Main {
    public static HttpServletRequest request;
    public static HttpServletResponse response;

    public static void start(HttpServletRequest request, HttpServletResponse response) {
        Main.request = request;
        Main.response = response;
    }
}
