package com.rahulsoni0.knownews.model;

public class SourceModel {
    String id;
    String name;

    @Override
    public String toString() {
        return "SourceModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceModel(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
