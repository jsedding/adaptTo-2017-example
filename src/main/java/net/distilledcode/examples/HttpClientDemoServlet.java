package net.distilledcode.examples;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.osgi.services.HttpClientBuilderFactory;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component(
        service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/adaptTo/2017",
                "sling.servlet.methods=GET"
        }
)
public class HttpClientDemoServlet extends SlingSafeMethodsServlet {

    private CloseableHttpClient httpClient;

    @Reference(policyOption = ReferencePolicyOption.GREEDY)
    private HttpClientBuilderFactory httpClientBuilderFactory;

    @Activate
    private void activate() {
        httpClient = httpClientBuilderFactory.newBuilder().build();
    }

    @Deactivate
    private void deactivate() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {

        long start = System.nanoTime();
        String result = fetchResult();
        long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);

        response.getWriter()
                .append(result).append('\n')
                .append("Took ").append(Long.toString(duration)).append("ms");
    }

    private String fetchResult() {
        try {
            HttpGet slow = new HttpGet("http://localhost:4502/bin/slow");
            return httpClient.execute(slow, res -> EntityUtils.toString(res.getEntity()));
        } catch (IOException e) {
            return e.toString();
        }
    }
}
