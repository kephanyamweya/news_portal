import Exceptions.ApiException;
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oDepartmentNewsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import org.sql2o.*;
import models.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");
        Sql2oDepartmentNewsDao departmentNewsDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao userDao;
        Sql2oDepartmentDao departmentDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/news_portal";
        Sql2o sql2o = new Sql2o(connectionString, "kepha", "0703874756");

        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);

        conn = sql2o.open();
        //get: view all departments
        get("/", "application/json", (req, res) -> {
            Map<String, Object> models = new HashMap<>();
            models.put("Create department(POST)", "/departments/new");
            models.put("View Departments", "/departments");
            models.put("View individual department", "/departments/:id");
            models.put("Create new User", "/departments/:id/users/new");
            models.put("View all users", "/users");
            models.put("View individual user", "/users/:id");
            models.put("View department's users and news", "/departments/:id/users/news");
            models.put("Create general news", "/news/new");
            models.put("View all general news", "/news");
            models.put("Create department news", "/departments/:id/news/new");
            models.put("View news of a department", "/departments/:id/news");
            return gson.toJson(models);
        });

        //Post: Create new Department
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //get: view all departments
        get("/departments", "application/json", (req, res) -> gson.toJson(departmentDao.getAll()));

        //get: view individual department
        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with id: %s exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });

        //Post: Create new user
        post("/departments/:id/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                user.setDepartmentId(departmentId);
                userDao.add(user);
                res.status(201);
                return gson.toJson(user);
            }
        });

        //get: view all users
        get("/users", "application/json", (req, res) -> {
            if (userDao.getAll().size() == 0) {
                return "{\"message\":\"I'm sorry, but no users are listed yet.\"}";
            } else {
                return gson.toJson(userDao.getAll());
            }
        });
        //get: view individual user
        get("/users/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            if (userToFind == null) {
                throw new ApiException(404, String.format("No user with id: %s exists", req.params("id")));
            }
            Map<String, Object> jsonMap = new HashMap<>();
            Department department = departmentDao.findById(userToFind.getDepartmentId());
            jsonMap.put("user", userToFind);
            jsonMap.put("department", department);
            return gson.toJson(jsonMap);
        });
        //get: view users from a specific department and news related to them
        get("/departments/:id/users/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                Map<String, Object> models = new HashMap<>();
                List<User> users = departmentDao.getAllUsersByDepartment(departmentToFind.getId());
                String news = String.format("/departments/%s/news", req.params("id"));
                if (users.size() == 0) {
                    String message = "I'm sorry, but no users are listed yet.";
                    models.put("department", departmentToFind);
                    models.put("message", message);
                    models.put("departmentNews", news);
                    return gson.toJson(models);
                } else {
                    models.put("department", departmentToFind);
                    models.put("departmentUsers", users);
                    models.put("departmentNews", news);
                    return gson.toJson(models);
                }
            }
        });
        //post: create news
        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            news.setCreatedAt();
            news.setFormattedCreatedAt();
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //get: view all general news
        get("/news", "application/json", (req, res) -> {
            if (newsDao.getAll().size() == 0) {
                return "{\"message\":\"I'm sorry, but no news are listed yet.\"}";
            } else {
                return gson.toJson(newsDao.getAll());
            }
        });

        //post: create department news
        post("/departments/:id/news/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                DepartmentNews news = gson.fromJson(req.body(), DepartmentNews.class);
                news.setDepartmentId(departmentId);
                news.setCreatedAt();
                news.setFormattedCreatedAt();
                departmentNewsDao.add(news);
                res.status(201);
                return gson.toJson(news);
            }
        });

        //get: view news of a particular department
        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null) {
                throw new ApiException(404, String.format("No department with id: %s exists", req.params("id")));
            } else {
                Map<String, Object> models = new HashMap<>();
                List<DepartmentNews> news = departmentDao.getAllNewsByDepartment(departmentToFind.getId());
                if (news.size() == 0) {
                    String message = "I'm sorry, but no news are listed yet.";
                    models.put("department", departmentToFind);
                    models.put("message", message);
                    return gson.toJson(models);
                } else {
                    models.put("department", departmentToFind);
                    models.put("departmentNews", news);
                    return gson.toJson(models);
                }
            }
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) -> {
            res.type("application/json");
        });

    }
}