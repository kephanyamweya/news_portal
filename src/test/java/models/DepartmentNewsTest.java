package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentNewsTest {

    @Test
    public void getDepartmentId_departmentIdInstantiatesCorrectly() {
        DepartmentNews news = setUpDepNews();
        assertEquals(1, news.getDepartmentId());
    }

    @Test
    public void testEquals_newsWithsameAuthorContentDepartmentIdAreEqual() {
        DepartmentNews news = setUpDepNews();
        DepartmentNews otherNews = setUpDepNews();
        assertTrue(news.equals(otherNews));
    }

    @Test
    public void testHashCode_newsWithsameAuthorContentDepartmentIdAreEqual() {
        DepartmentNews news = setUpDepNews();
        DepartmentNews otherNews = setUpDepNews();
        assertTrue(news.equals(otherNews));
    }

    //helper
    public DepartmentNews setUpDepNews() {
        return new DepartmentNews("well done", "alvin", 1);
    }
}