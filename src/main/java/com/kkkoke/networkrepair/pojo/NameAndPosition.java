package com.kkkoke.networkrepair.pojo;

import java.util.List;

public class NameAndPosition {
    String name;
    List<String> position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPosition() {
        return position;
    }

    public void setPosition(List<String> position) {
        this.position = position;
    }

    public NameAndPosition(String k, List<String> v) {
        this.name = k;
        this.position = v;
    }
}
