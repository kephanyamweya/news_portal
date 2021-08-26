package dao;

import models.Department;
import models.DepartmentNews;
import models.User;
import org.junit.*;
import org.sql2o.*;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oDepartmentNewsDao departmentNewsDao;
    private static Sql2oUserDao userDao;
    private static Connection conn;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "kepha", "0703874756");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        departmentNewsDao = new Sql2oDepartmentNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn =  sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Clearing database");
        departmentDao.clearAll();
        userDao.clearAll();
        departmentDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("Connection closed");
    }

    @Test
    public void addingDepartmentsSetsId() {
        Department department = setNewDepartment();
        int originalDepartmentId = department.getId();
        departmentDao.add(department);
        assertNotEquals(originalDepartmentId, department.getId());
    }

    @Test
    public void add_individualDepartmentsCanBeFoundById() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        Department foundDepartment = departmentDao.findById(department.getId());
        assertEquals(department, foundDepartment);
    }

    @Test
    public void findById_individualDepartmentCanBeFoundById() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        Department foundDepartment = departmentDao.findById(department.getId());
        assertEquals(department, foundDepartment);
    }

    @Test
    public void getAll_allDepartmentAreReturnedCorrectly() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void getAll_nothingIsReturnedFromAnEmptyDatabase() {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void update_departmentIsUpdatedCorrectly() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        int currentId = department.getId();
        departmentDao.update(currentId, "maths", "maths subjects", 2);
        Department updatedDepartment = departmentDao.findById(currentId);
        assertNotEquals(department, updatedDepartment);
    }

    @Test
    public void deleteById_individualDepartmentIsDeletedCorrectlyByItsId() {
        Department department = setNewDepartment();
        Department otherDepartment = new Department("programming","tech subjects", 1);
        departmentDao.add(department);
        departmentDao.add(otherDepartment);
        departmentDao.deleteById(department.getId());
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void clearAll_allAddedDepartmentCanBeCleared() {
        Department department = setNewDepartment();
        Department otherDepartment = new Department("History","Ethics subjects", 1);
        departmentDao.add(department);
        departmentDao.add(otherDepartment);
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void getAllUsersByDepartmentId_usersOfDepartmentAreReturnedCorrectly() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        User firstUser = new User("kip", "manager", department.getId());
        userDao.add(firstUser);
        User secondUser = new User("nancy", "director", department.getId());
        userDao.add(secondUser);
        User thirdUser = new User("jymo", "barber", 3);
        userDao.add(thirdUser);
        assertEquals(2, departmentDao.getAllUsersByDepartment(department.getId()).size());
        assertTrue(departmentDao.getAllUsersByDepartment(department.getId()).contains(secondUser));
        assertFalse(departmentDao.getAllUsersByDepartment(department.getId()).contains(thirdUser));
    }

    @Test
    public void getAllNewsByDepartmentId_newsOfDepartmentAreReturnedCorrectly() {
        Department department = setNewDepartment();
        departmentDao.add(department);
        DepartmentNews firstNews = new DepartmentNews("coding", "nesky", department.getId());
        departmentNewsDao.add(firstNews);
        DepartmentNews secondNews = new DepartmentNews("dancing", "kevin", department.getId());
        departmentNewsDao.add(secondNews);
        DepartmentNews thirdNews = new DepartmentNews("shopping", "shinski", 3);
        departmentNewsDao.add(thirdNews);
        assertEquals(2, departmentDao.getAllNewsByDepartment(department.getId()).size());
        assertTrue(departmentDao.getAllNewsByDepartment(department.getId()).contains(secondNews));
        assertFalse(departmentDao.getAllNewsByDepartment(department.getId()).contains(thirdNews));
    }

    //helpers
    public DepartmentNews setUpNews(){ return new DepartmentNews("Schools to close", "M Kitavi", 1); }
    public User setUpUser(){
        return new User("Mkitavi", "Teacher", 1);
    }
    public Department setNewDepartment(){
        return new Department("History", "Government subjects", 5);
    }
}