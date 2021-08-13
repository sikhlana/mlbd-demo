package name.saifmahmud.demo.http.filters;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestIdFilter implements Filter {
    MessageSource messageSource;

    @Autowired
    RequestIdFilter(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestId = req.getHeader("Request-Id");

        if (requestId == null || requestId.equals("")) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, messageSource.getMessage("exceptions.invalid_request_id", null, LocaleContextHolder.getLocale()));

            return;
        }

        MDC.put("REQUEST_ID", requestId);
        filterChain.doFilter(request, response);
    }
}
