import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public enum ThymeleafUtil {

    INSTANCE;

    private TemplateEngine templateEngine;
    private boolean initialized;

    /**
     * {@link HttpServlet#init()} method is an ideal place to call this method.
     * This method can be called from any servlet because there is only one {@link ServletContext} per JVM application.
     * This method should be called only once.
     *
     * @param servletContext The servlet context {@link ServletContext}
     * @throws IllegalStateException if called more than once
     */
    public void init(final ServletContext servletContext) {
        if (initialized) {
            throw new IllegalStateException("Already initialized");
        }
        initialized = true;

        final ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/"); //TODO parametric
        templateResolver.setSuffix(".html"); //TODO parametric
        templateResolver.setCacheTTLMs(3600000L); //TODO parametric
        templateResolver.setCacheable(false); //TODO parametric
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    /**
     * Should be called after {@link ThymeleafUtil#init(ServletContext)} is called.
     *
     * @param templateName
     * @param request
     * @param response
     * @param variables
     * @throws IOException
     */
    public void process(String templateName, HttpServletRequest request, HttpServletResponse response, Map<String, Object> variables)
            throws IOException {
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
        ctx.setVariables(variables);
        templateEngine.process(templateName, ctx, response.getWriter());
    }

    /**
     * Calling this method is equivalent to call {@code ThymeleafUtil#process(String, HttpServletRequest, HttpServletResponse, null)}
     *
     * @see ThymeleafUtil#process(String, HttpServletRequest, HttpServletResponse, Map)
     * @param templateName
     * @param request
     * @param response
     * @throws IOException
     */
    public void process(String templateName, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        process(templateName, request, response, null);
    }
}