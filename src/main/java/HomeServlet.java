import com.kodgemisi.reports.NightmareWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by destan on 12/2/16.
 */

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET");

        //http://stackoverflow.com/a/16675399/878361
        System.out.println(req.getRequestURL());

        req.getRequestDispatcher("sendJsonForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");

        final String dataAsJson = req.getParameter("data");
        System.out.println(dataAsJson);

        Path nightmarePath = Paths.get("/home/destan/Desktop/reporting/nightmareTryout");
//        URL templateUrl = new URL("file:///home/destan/Desktop/reporting/report_template.html");
        URL templateUrl = new URL("http://localhost:8080/report");

        // Create data file
        Path dir = Files.createTempDirectory("reports");
        Path dataFile = Files.createTempFile(dir, "reports", ".json");

        System.out.println(dir);
        System.out.println(dataFile);

        // https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
        try(BufferedWriter writer = Files.newBufferedWriter(dataFile, Charset.forName("UTF-8"));) {
            writer.write(dataAsJson);
        }

        // options
        Map<String, String> options = new HashMap<>();
        options.put("inputDataFile", dataFile.toString());//"/home/destan/Desktop/data.json"
        options.put("outputFolder", dir.toString());

        try {
            new NightmareWrapper(nightmarePath).generatePdf(templateUrl, options);
        } catch (InterruptedException e) {
            e.printStackTrace();
            resp.getWriter().write(e.getMessage());
        }

        req.getRequestDispatcher("sendJsonForm.jsp").forward(req, resp);
    }
}
