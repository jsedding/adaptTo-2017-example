package net.distilledcode.examples;

import org.apache.commons.lang3.RandomUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component(
        service = Servlet.class,
        property = {
                "sling.auth.requirements=-/bin/slow",
                "sling.servlet.paths=/bin/slow",
                "sling.servlet.methods=GET"
        }
)
public class SlowServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
        try {
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(500, 1500));
            response.getWriter().append("adaptTo() 2017");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
