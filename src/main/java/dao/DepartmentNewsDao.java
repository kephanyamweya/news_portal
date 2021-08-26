package dao;

import models.DepartmentNews;
import java.util.List;

public interface DepartmentNewsDao {
    //create
    void add(DepartmentNews news);

    //read
    List<DepartmentNews> getAll();

    DepartmentNews findById(int id);

    //update
    void update(int id, String content, String author, int departmentId);

    //delete
    void deleteById(int id);

    void clearAll();
}