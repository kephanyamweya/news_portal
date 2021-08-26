package dao;

import models.DepartmentNews;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentNewsDaoTest {
    private static Sql2oDepartmentNewsDao departmentNewsDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "kepha", "0703874756");
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        conn = (Connection) sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        departmentNewsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingNewsSetsId() {
        DepartmentNews news = setUpNews();
        int originalNewsId = news.getId();
        departmentNewsDao.add(news);
        assertNotEquals(originalNewsId, news.getId());
    }

    @Test
    public void add_individualNewsCanBeFoundById() {
        DepartmentNews news = setUpNews();
        departmentNewsDao.add(news);
        DepartmentNews foundNews = departmentNewsDao.findById(news.getId());
        assertEquals(news, foundNews);
    }

    @Test
    public void findById_individualNewsCanBeFoundById() {
        DepartmentNews news = setUpNews();
        departmentNewsDao.add(news);
        DepartmentNews foundNews = departmentNewsDao.findById(news.getId());
        assertEquals(news, foundNews);
    }

    @Test
    public void getAll_allNewsAreReturnedCorrectly() {
        DepartmentNews news = setUpNews();
        departmentNewsDao.add(news);
        assertEquals(1, departmentNewsDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, departmentNewsDao.getAll().size());
    }

    @Test
    public void update_newsIsUpdatedCorrectly() {
        DepartmentNews news = setUpNews();
        departmentNewsDao.add(news);
        int currentId = news.getId();
        departmentNewsDao.update(currentId, "maths", "maths subjects", 2);
        DepartmentNews updatedNews = departmentNewsDao.findById(currentId);
        assertNotEquals(news, updatedNews);
    }

    @Test
    public void deleteById_individualNewsIsDeletedCorrectlyByItsId() {
        DepartmentNews news = setUpNews();
        DepartmentNews otherNews = new DepartmentNews("Schools to open","Mutali", 1);
        departmentNewsDao.add(news);
        departmentNewsDao.add(otherNews);
        departmentNewsDao.deleteById(news.getId());
        assertEquals(1, departmentNewsDao.getAll().size());
    }

    @Test
    public void clearAll_allAddedNewsCanBeCleared() {
        DepartmentNews news = setUpNews();
        DepartmentNews otherNews = new DepartmentNews("Schools to open","Mutali", 1);
        departmentNewsDao.add(news);
        departmentNewsDao.add(otherNews);
        departmentNewsDao.clearAll();
        assertEquals(0, departmentNewsDao.getAll().size());
    }

    public DepartmentNews setUpNews(){
        return new DepartmentNews("Schools to close", "M Kitavi", 1);
    }
}