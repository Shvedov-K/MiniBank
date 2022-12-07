package com.IFutureTestTask.MiniBank.metrics;

import com.IFutureTestTask.MiniBank.service.impl.MetricServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class MetricFilter implements Filter {

    @Autowired
    private MetricServiceImpl metricServiceImpl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();

        chain.doFilter(request, response);

        //int status = ((HttpServletResponse) response).getStatus();
        metricServiceImpl.increaseCount(req);
    }
}
