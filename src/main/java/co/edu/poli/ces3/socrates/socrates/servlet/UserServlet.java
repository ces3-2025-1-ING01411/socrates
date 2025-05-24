package co.edu.poli.ces3.socrates.socrates.servlet;

import co.edu.poli.ces3.socrates.socrates.dao.User;
import co.edu.poli.ces3.socrates.socrates.services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
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

        //leer y parsear el JSON recibido
        JsonObject jsonUser = getParamsFromBody(req);
        Class<?> classUser = User.class;
        Field [] fields = classUser.getDeclaredFields();

        User userUpdate = new User();

        try {
            for (Field f : fields) {
                if (jsonUser.has(f.getName())) {
                    System.out.println("Nombre json: " + jsonUser.has(f.getName()));
                    System.out.println("Nombre campo: " + f.getName());
                    f.setAccessible(true);
                    Class<?> fieldType = f.getType();
                    Object value = convertJsonElementToFieldType(jsonUser.get(f.getName()), fieldType);
                    f.set(userUpdate, value);
                }
            }

            userUpdate.setId(Integer.parseInt(req.getParameter("id")));

            userService.upgradeUser(userUpdate);

        } catch (IllegalAccessException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al acceder a los campos\"}");
            return;
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Error al asignar el valor\"}");
            return;
        }

        System.out.println("Usuario actualizado: " + userUpdate);

        /*
        //recuperar la entidad que vamos a editar
        int id = Integer.parseInt(req.getParameter("id"));
        User user = userService.findById(id);
        if (user==null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\":\"Usuario no encontrado\"}");
            return;
        }

        //aplicar cambios con reflection
        Class<?> classUser = User.class;
        for (String key: jsonUser.keySet()) {
            try {
                Field field = classUser.getDeclaredField(key);
                field.setAccessible(true);

                JsonElement jsonValue = jsonUser.get(key);
                Class<?> type = field.getType();

                Object value = switch (type.getSimpleName()) {
                    case "int" -> jsonValue.getAsInt();
                    case "boolean" -> jsonValue.getAsBoolean();
                    case "String" -> jsonValue.getAsString();
                    case "Date" -> new java.util.Date(jsonValue.getAsLong());
                    default -> throw new IllegalArgumentException(
                            "Tipo no soportado: " + type.getName());
                };

                //asignar el valor
                field.set(user, value);


            } catch (NoSuchFieldException e) {
                // El campo no existe en la entidad → 400 Bad Request
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"Campo '" + key + "' no existe\"}");
                return;
            } catch (IllegalAccessException | IllegalArgumentException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"Valor inválido para '" + key + "'\"}");
                return;
            }
        }
        */

        //Field [] fields = classUser.getDeclaredFields();
        /*
        for (Field f : fields) {
            System.out.println("Nombre campo: " + f.getName());
        }

         */

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
