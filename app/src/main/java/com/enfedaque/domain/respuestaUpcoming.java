package com.enfedaque.domain;

import java.util.ArrayList;

public class respuestaUpcoming {

    private int page;
    private ArrayList<peliculas> results;
    private Dates dates;
    private int total_pages;
    private int total_results;

    public respuestaUpcoming(int page, ArrayList<peliculas> results, Dates dates, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.dates = dates;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<peliculas> getResults() {
        return results;
    }

    public void setResults(ArrayList<peliculas> results) {
        this.results = results;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
