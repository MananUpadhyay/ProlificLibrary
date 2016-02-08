package com.example.manan.library;

public class BookBuilder {
    private String id = null;
    private String title = null;
    private String author = null;
    private String publisher = null;
    private String categories = null;
    private String lastCheckedOut = null;
    private String lastCheckedOutBy = null;
    private String url = null;

    public BookBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder withPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookBuilder withCategories(String categories) {
        this.categories = categories;
        return this;
    }

    public BookBuilder withLastCheckedOut(String lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
        return this;
    }

    public BookBuilder withLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
        return this;
    }

    public BookBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public Book build() {
        return new Book(id, title, author, publisher, categories, lastCheckedOut, lastCheckedOutBy, url);
    }
}