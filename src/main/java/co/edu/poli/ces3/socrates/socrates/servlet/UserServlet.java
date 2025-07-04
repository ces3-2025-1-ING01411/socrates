package co.edu.poli.ces3.socrates.socrates.servlet;

import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.services.UserService;
import co.edu.poli.ces3.socrates.socrates.utils.HashUtil;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
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

        String idUserParam = req.getParameter("id");
        boolean idUser = idUserParam != null && !idUserParam.isEmpty() && !idUserParam.equals("null");


        if (idUser) {
            int id = Integer.parseInt(idUserParam);
            User user = userService.findById(id);
            if (user != null) {
                JSONObject json = new JSONObject(user);
                out.println(json.toString());
                out.flush();
                return;
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\":\"Usuario con id "+ idUserParam + " no existe\"}");
                out.flush();
                return;
            }
        } else {
            List list = userService.getAllUsers();
            JSONArray json = new JSONArray(list);
            out.println(json.toString());
            out.flush();
            return;
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String idUserParam = req.getParameter("id");
        System.out.println("idUserParam: " + idUserParam);

        boolean idUser = idUserParam != null && !idUserParam.isEmpty() && !idUserParam.equals("null");
        System.out.println("idUser: " + idUser);

        if (idUser) {
            int idUserUpdate = Integer.parseInt(idUserParam);
            User existsUser = userService.findById(idUserUpdate);
            if (existsUser == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\":\"Usuario con id "+ idUserParam + " no existe\"}");
                out.flush();
                return;
            }

            System.out.println("sigue patch");

            //leer y parsear el JSON recibido
            JsonObject jsonUser = getParamsFromBody(req);
            Class<?> classUser = User.class;
            Field[] fields = classUser.getDeclaredFields();
            User userUpgrade = new User();

            try {
                for (Field f : fields) {
                    if (jsonUser.has(f.getName())) {
                        f.setAccessible(true);
                        Class<?> fieldType = f.getType();

                        if (f.getName().equals("password")) {
                            String rawPassword = jsonUser.get(f.getName()).getAsString();
                            String hashedPassword = HashUtil.sha256(rawPassword);
                            f.set(userUpgrade, hashedPassword);
                        } else {
                            Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);
                            f.set(userUpgrade, value);
                        }
                    }
                }

                userUpgrade.setId(idUserUpdate);

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
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"ID de usuario no proporcionado\"}");
            out.flush();
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

        String idUserParam = req.getParameter("id");
        System.out.println("idUserParam: " + idUserParam);

        boolean idUser = idUserParam != null && !idUserParam.isEmpty() && !idUserParam.equals("null");
        System.out.println("idUser: " + idUser);

        if (idUser) {
            int idUserUpdate = Integer.parseInt(idUserParam);
            User existsUser = userService.findById(idUserUpdate);

            if (existsUser == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\":\"Usuario con id "+ idUserParam + " no existe\"}");
                out.flush();
                return;
            }

            System.out.println("sigue put");

            //leer y parsear el JSON recibido
            JsonObject jsonUser = getParamsFromBody(req);
            Class<?> classUser = User.class;
            Field[] fields = classUser.getDeclaredFields();
            User userUpdate = new User();

            try {
                for (Field f : fields) {
                    if (jsonUser.has(f.getName())) {
                        f.setAccessible(true);
                        Class<?> fieldType = f.getType();

                        if (f.getName().equals("password")) {
                            String rawPassword = jsonUser.get(f.getName()).getAsString();
                            String hashedPassword = HashUtil.sha256(rawPassword);
                            f.set(userUpdate, hashedPassword);
                        } else {
                            Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);
                            f.set(userUpdate, value);
                        }
                    }
                }

                userUpdate.setId(idUserUpdate);

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


        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"ID de usuario no proporcionado\"}");
            out.flush();
            return;
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        try {
            String idUserParam = req.getParameter("id");
            System.out.println("idUserParam: " + idUserParam);

            boolean idUser = idUserParam != null && !idUserParam.isEmpty() && !idUserParam.equals("null");
            System.out.println("idUser: " + idUser);

            if (!idUser) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"ID de usuario no proporcionado\"}");
                out.flush();
                return;
            }

            int id = Integer.parseInt(idUserParam);
            User user = userService.findById(id);

            if (user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("{\"error\":\"Usuario con id "+ idUserParam + " no existe\"}");
                out.flush();
                return;
            }

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
