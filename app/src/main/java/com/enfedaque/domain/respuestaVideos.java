package com.enfedaque.domain;

import java.util.ArrayList;

public class respuestaVideos {

    private int id;
    private ArrayList<video> results;

    public respuestaVideos(int id, ArrayList<video> results) {
        this.id = id;
        this.results = results;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<video> getResults() {
        return results;
    }

    public void setResults(ArrayList<video> results) {
        this.results = results;
    }
}
