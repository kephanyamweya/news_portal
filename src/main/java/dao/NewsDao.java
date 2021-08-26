package dao;

import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add (News news);

    //read
    List<News> getAll();
    News findById(int id);

    //update
    void update(int id, String content, String author);

    //delete
    void deleteById(int id);
    void clearAll();
}