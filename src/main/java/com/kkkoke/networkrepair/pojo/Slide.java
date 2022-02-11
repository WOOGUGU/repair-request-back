package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Slide {
    private Integer id;
    private String imgPath;
    private String submitTime;
    private String author;
    private String displayTime;

    public Slide(String imgPath, String submitTime, String author) {
        this.imgPath = imgPath;
        this.submitTime = submitTime;
        this.author = author;
    }
}
