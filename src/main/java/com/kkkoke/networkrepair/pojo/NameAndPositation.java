package com.kkkoke.networkrepair.pojo;

import java.util.List;

public class NameAndPositation{
    String name;
    List<String> positation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPositation() {
        return positation;
    }

    public void setPositation(List<String> positation) {
        this.positation = positation;
    }

    public NameAndPositation(String k, List<String> v){
        this.name = k;
        this.positation = v;
    }
}
