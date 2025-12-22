package org.example.combatereapoluariiapp.src.model;

public class SearchResult {
    private String title;
    private String url;
    private String snippet;
    private String author;
    private String year;
    private String source;

    public SearchResult(String title, String url, String snippet, String author, String year, String source) {
        this.title = title;
        this.url = url;
        this.snippet = snippet;
        this.author = author;
        this.year = year;
        this.source = source;
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getSnippet() { return snippet; }
    public String getAuthor() { return author; }
    public String getYear() { return year; }
    public String getSource() { return source; }

    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
    public void setAuthor(String author) { this.author = author; }
    public void setYear(String year) { this.year = year; }
    public void setSource(String source) { this.source = source; }

    @Override
    public String toString() {
        return "SearchResult{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}