package com.example.manan.library;

/**
 * Model class for the book object.
 * Used for JSON serialization and RecyclerViews.
 */
public class Book {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private String categories;
    private String lastCheckedOut;
    private String lastCheckedOutBy;
    private String url;


    public Book() {
    }

    public Book(String id, String title, String author, String publisher, String categories, String lastCheckedOut, String lastCheckedOutBy, String url) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.categories = categories;
        this.lastCheckedOut = lastCheckedOut;
        this.lastCheckedOutBy = lastCheckedOutBy;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCategories() {
        return categories;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", categories='" + categories + '\'' +
                ", lastCheckedOut='" + lastCheckedOut + '\'' +
                ", lastCheckedOutBy='" + lastCheckedOutBy + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
