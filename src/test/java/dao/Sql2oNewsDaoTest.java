package dao;

import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2oNewsDaoTest {
    private static Sql2oNewsDao newsDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "kepha", "0703874756");

        newsDao = new Sql2oNewsDao(sql2o);
        conn =  sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        newsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingNewsSetsId() {
        News news = setUpNews();
        int originalNewsId = news.getId();
        newsDao.add(news);
        assertNotEquals(originalNewsId, news.getId());
    }

    @Test
    public void add_individualNewsCanBeFoundById() {
        News news = setUpNews();
        newsDao.add(news);
        News foundNews = newsDao.findById(news.getId());
        assertEquals(news, foundNews);
    }

    @Test
    public void findById_individualNewsCanBeFoundById() {
        News news = setUpNews();
        newsDao.add(news);
        News foundNews = newsDao.findById(news.getId());
        assertEquals(news, foundNews);
    }

    @Test
    public void getAll_allNewsAreReturnedCorrectly() {
        News news = setUpNews();
        newsDao.add(news);
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void update_newsIsUpdatedCorrectly() {
        News news = setUpNews();
        newsDao.add(news);
        int currentId = news.getId();
        newsDao.update(currentId, "uchumi supermarket", "Mugo");
        News updatedNews = newsDao.findById(currentId);
        assertNotEquals(news, updatedNews);
    }

    @Test
    public void deleteById_individualNewsIsDeletedCorrectlyByItsId() {
        News news = setUpNews();
        News otherNews = new News("BBI is null and void","Justice Kiage");
        newsDao.add(news);
        newsDao.add(otherNews);
        newsDao.deleteById(news.getId());
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void clearAll_allAddedNewsCanBeCleared() {
        News news = setUpNews();
        News otherNews = new News("BBI is null and void","Justice Kiage");
        newsDao.add(news);
        newsDao.add(otherNews);
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }

    public News setUpNews(){
        return new News("Covid jab", "N Katule");
    }
}
