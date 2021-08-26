
package dao;

import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oUserDaoTest {
    private static Sql2oUserDao userDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString ="jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "kepha", "0703874756");
        userDao = new Sql2oUserDao(sql2o);
        conn = (Connection) sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        userDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingUserSetsId() {
        User user = setUpUser();
        int originalUserId = user.getId();
        userDao.add(user);
        assertNotEquals(originalUserId, user.getId());
    }

    @Test
    public void add_individualUserCanBeFoundById() {
        User user = setUpUser();
        userDao.add(user);
        User foundUser = userDao.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void findById_individualUserCanBeFoundById() {
        User user = setUpUser();
        userDao.add(user);
        User foundUser = userDao.findById(user.getId());
        assertEquals(user, foundUser);
    }

    @Test
    public void getAll_allUserAreReturnedCorrectly() {
        User user = setUpUser();
        userDao.add(user);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void update_userIsUpdatedCorrectly() {
        User user = setUpUser();
        userDao.add(user);
        int currentId = user.getId();
        userDao.update(currentId, "Mutali", "principal", 1);
        User updatedUser = userDao.findById(currentId);
        assertNotEquals(user, updatedUser);
    }

    @Test
    public void deleteById_individualUserIsDeletedCorrectlyByItsId() {
        User user = setUpUser();
        User otherUser = new User("Mutali", "principal", 1);
        userDao.add(user);
        userDao.add(otherUser);
        userDao.deleteById(user.getId());
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void clearAll_allAddedUserCanBeCleared() {
        User user = setUpUser();
        User otherUser = new User("Mutali", "principal", 2);
        userDao.add(user);
        userDao.add(otherUser);
        userDao.clearAll();
        assertEquals(0, userDao.getAll().size());
    }

    public User setUpUser(){
        return new User("Mkitavi", "Teacher", 1);
    }
}