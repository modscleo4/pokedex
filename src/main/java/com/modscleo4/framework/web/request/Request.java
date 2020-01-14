package com.modscleo4.framework.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Request extends HttpServletRequestWrapper implements IRequest {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public Request(HttpServletRequest request) {
        super(request);
    }
}
