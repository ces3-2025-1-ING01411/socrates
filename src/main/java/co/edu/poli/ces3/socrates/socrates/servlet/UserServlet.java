package co.edu.poli.ces3.socrates.socrates.servlet;

import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;

@WebServlet(name = "userServlet", value = "/v2/users")
public class UserServlet extends MyServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
        System.out.println("UserServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        List list = userService.getAllUsers();
        JSONArray json = new JSONArray(list);
        out.println(json);

        /*
        Gson gson = new Gson();
        String json = gson.toJson(userService.getAllUsers());
        out.println(json);

         */
        out.flush();
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JsonObject jsonUser = getParamsFromBody(req);

        Class<?> classUser = User.class;

        Field [] fields = classUser.getDeclaredFields();

        for (Field f : fields) {
            System.out.println("Nombre campo: " + f.getName());
        }

        /*
        List list = userService.updateUser();
        JSONArray json = new JSONArray(list);
        out.println(json);
         */
        out.flush();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
