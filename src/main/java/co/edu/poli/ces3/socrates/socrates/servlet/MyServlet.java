package co.edu.poli.ces3.socrates.socrates.servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ResourceBundle;

public class MyServlet extends HttpServlet {
    private static final String METHOD_PATCH = "PATCH";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("jakarta.servlet.http.LocalStrings");

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals(METHOD_PATCH)) {
            doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = "Metodo patch no soportado.";
        resp.sendError(this.getMethodNotSupportedCode(protocol), msg);
    }

    private int getMethodNotSupportedCode(String protocol) {
        switch (protocol) {
            case "HTTP/0.9":
            case "HTTP/1.0":
                return 400;
            default:
                return 405;
        }
    }

    protected JsonObject getParamsFromBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }
}
