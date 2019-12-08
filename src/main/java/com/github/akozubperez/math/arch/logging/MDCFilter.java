package com.github.akozubperez.math.arch.logging;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@WebFilter("/*")
public class MDCFilter implements Filter {

    public static final String MDC_REQ_ID = "req.id";
    public static final String MDC_USERNAME = "username";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // vacio
    }

    @Override
    @Logging(disabled = true)
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            MDC.put(MDC_USERNAME, authentication.getName());
        }
        if (MDC.get(MDC_REQ_ID) == null) {
            MDC.put(MDC_REQ_ID, UUID.randomUUID().toString());
        }
        try {
            chain.doFilter(req, resp);
        } finally {
            if (authentication != null) {
                MDC.remove(MDC_USERNAME);
            }
            MDC.remove(MDC_REQ_ID);
        }
    }

    @Override
    public void destroy() {
        // vacio
    }
}
