package com.IFutureTestTask.MiniBank.metrics;

import com.IFutureTestTask.MiniBank.service.impl.MetricServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        metricServiceImpl.increaseCount(req);
    }
}
