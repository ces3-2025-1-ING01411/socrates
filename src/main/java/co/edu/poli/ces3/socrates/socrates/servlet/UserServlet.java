package co.edu.poli.ces3.socrates.socrates.servlet;

import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.services.UserService;
import co.edu.poli.ces3.socrates.socrates.utils.HashUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

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
        out.flush();
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        //leer y parsear el JSON recibido
        JsonObject jsonUser = getParamsFromBody(req);
        Class<?> classUser = User.class;
        Field[] fields = classUser.getDeclaredFields();
        User userUpgrade = new User();

        try {
            for (Field f : fields) {
                if (jsonUser.has(f.getName())) {
                    System.out.println("Nombre json: " + jsonUser.has(f.getName()));
                    System.out.println("Nombre campo: " + f.getName());
                    Class<?> fieldType = f.getType();

                    Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);

                    f.setAccessible(true);
                    f.set(userUpgrade, value);
                }
            }

            userUpgrade.setId(Integer.parseInt(req.getParameter("id")));

            User user = userService.upgrade(userUpgrade);

            JSONObject json = new JSONObject(user);

            out.println(json.toString());
            out.flush();

        } catch (IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al acceder a los campos\"}");
            return;
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al asignar el valor\"}");
            return;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JsonObject jsonUser = getParamsFromBody(req);
        Class<?> classUser = User.class;
        Field[] fields = classUser.getDeclaredFields();
        User userPost = new User();

        try {
            for (Field f: fields) {
                if (jsonUser.has(f.getName())) {
                    f.setAccessible(true);
                    Class<?> fieldType = f.getType();
                    if (f.getName().equals("password")) {
                        String rawPassword = jsonUser.get(f.getName()).getAsString();
                        String hashedPassword = HashUtil.sha256(rawPassword);
                        f.set(userPost, hashedPassword);
                    } else {
                        Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);
                        f.set(userPost, value);
                    }
                }
            }

            User user = userService.create(userPost);
            JSONObject response = new JSONObject(user);

            out.println(response.toString());
            out.flush();

        } catch (IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al acceder a los campos\"}");
            return;
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al asignar el valor\"}");
            return;
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        //leer y parsear el JSON recibido
        JsonObject jsonUser = getParamsFromBody(req);
        Class<?> classUser = User.class;
        Field[] fields = classUser.getDeclaredFields();
        User userUpdate = new User();

        try {
            for (Field f : fields) {
                if (jsonUser.has(f.getName())) {

                    Class<?> fieldType = f.getType();

                    Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);

                    f.setAccessible(true);
                    f.set(userUpdate, value);
                }
            }

            userUpdate.setId(Integer.parseInt(req.getParameter("id")));

            User user = userService.update(userUpdate);

            JSONObject json = new JSONObject(user);

            out.println(json.toString());
            out.flush();

        } catch (IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al acceder a los campos\"}");
            return;
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al asignar el valor\"}");
            return;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean deletedUser = userService.delete(id);

            if (deletedUser) {
                out.println("{\"message\":\"Usuario eliminado con éxito\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\":\"Usuario no encontrado\"}");
            }

            out.flush();
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"ID inválido\"}");
        }
    }
}
