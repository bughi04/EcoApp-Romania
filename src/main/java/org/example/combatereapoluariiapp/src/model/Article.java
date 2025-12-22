package org.example.combatereapoluariiapp.src.model;

import java.util.List;

public class Article {
    private String title;
    private String citation;
    private String link;
    private String summary;
    private List<String> solutions;

    public Article(String title, String citation, String link, String summary, List<String> solutions) {
        this.title = title;
        this.citation = citation;
        this.link = link;
        this.summary = summary;
        this.solutions = solutions;
    }

    public String getTitle() { return title; }
    public String getCitation() { return citation; }
    public String getLink() { return link; }
    public String getSummary() { return summary; }
    public List<String> getSolutions() { return solutions; }

    public void setTitle(String title) { this.title = title; }
    public void setCitation(String citation) { this.citation = citation; }
    public void setLink(String link) { this.link = link; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setSolutions(List<String> solutions) { this.solutions = solutions; }
}