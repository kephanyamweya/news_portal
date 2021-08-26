
package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class News {
    public int id;
    public String content;
    public String author;
    public long createdAt;
    public String formattedCreatedAt;
    private final static String DATABASETYPE = "News";

    public News(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = System.currentTimeMillis();
        setFormattedCreatedAt();
    }

    public static String getDATABASETYPE() {
        return DATABASETYPE;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAt() {
        Date date = new Date(createdAt);
        String datePattern = "MMM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        return sdf.format(date);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedAt() {
        this.createdAt = System.currentTimeMillis();
    }

    public void setFormattedCreatedAt() {
        Date date = new Date(createdAt);
        String datePattern = "MMM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        this.formattedCreatedAt = sdf.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(content, news.content) &&
                Objects.equals(author, news.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, author);
    }
}
