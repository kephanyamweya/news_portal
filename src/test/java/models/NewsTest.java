package models;

import org.junit.Test;
import models.News;

import static org.junit.Assert.*;

public class NewsTest {

    @Test
    public void getId_idInstantiatesCorrectly() {
        News news = setUpNews();
        news.setId(1);
        assertEquals(1, news.getId());
    }

    @Test
    public void getContent_contentIstantiatesCorrectly() {
        News news = setUpNews();
        assertEquals("Schools to open", news.getContent());
    }

    @Test
    public void getAuthor_AuthorInstantiatesCorrectly() {
        News news = setUpNews();
        assertEquals("Kerich", news.getAuthor());
    }

    @Test
    public void getCreatedAt_createdAtInstantiatesCorrectly() {
        News news = setUpNews();
        long saved = news.getCreatedAt();
        assertEquals(saved, news.getCreatedAt());
    }

    @Test
    public void getFormattedCreatedAt() {
        News news = setUpNews();
        String saved = news.getFormattedCreatedAt();
        news.setCreatedAt();
        news.setFormattedCreatedAt();
        assertEquals(saved, news.getFormattedCreatedAt());
    }

    @Test
    public void setId_idIsSetCorrectly() {
        News news = setUpNews();
        news.setId(1);
        assertEquals(1, news.getId());
    }

    @Test
    public void setCreatedAt() {
        News news = setUpNews();
        news.setCreatedAt();
        long saved = news.getCreatedAt();
        assertEquals(saved, news.getCreatedAt());
    }

    @Test
    public void setFormattedCreatedAt() {
        News news = setUpNews();
        String saved = news.getFormattedCreatedAt();
        news.setCreatedAt();
        news.setFormattedCreatedAt();
        assertEquals(saved, news.getFormattedCreatedAt());
    }

    @Test
    public void testEquals_newsWithSameContentAndAuthorAreEqual() {
        News news = setUpNews();
        News otherNews = setUpNews();
        assertEquals(true, news.equals(otherNews));
    }

    @Test
    public void testHashCode_newsWithSameContentAndAuthorAreEqual() {
        News news = setUpNews();
        News otherNews = setUpNews();
        assertEquals(true, news.equals(otherNews));
    }

    //helpers
    public News setUpNews() {
        return new News("Schools to open", "Kerich");
    }
}