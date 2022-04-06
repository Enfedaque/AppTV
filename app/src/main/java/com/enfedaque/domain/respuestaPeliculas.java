package com.enfedaque.domain;

import java.util.ArrayList;

public class respuestaPeliculas {

    private int page;
    private ArrayList<peliculas> results;
    private int total_results;
    private int totalpages;

    public respuestaPeliculas(int page, ArrayList<peliculas> results, int total_results, int totalpages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.totalpages = totalpages;
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

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    @Override
    public String toString() {
        return "respuesta{" +
                "page=" + page +
                ", results=" + results.toString() +
                ", total_results=" + total_results +
                ", totalpages=" + totalpages +
                '}';
    }
}

