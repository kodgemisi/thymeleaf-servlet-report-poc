import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by destan on 12/3/16.
 */
@WebServlet(urlPatterns = "/report", loadOnStartup = 1)
public class ExampleReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        ThymeleafUtil.INSTANCE.init(getServletContext());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Map<String, Object> variables = new HashMap<>();
        variables.put("currentDate", new Date());
        variables.put("companies", Dao.listAllCompanies());

        ThymeleafUtil.INSTANCE.process("report_2", request, response, variables);
    }
}
