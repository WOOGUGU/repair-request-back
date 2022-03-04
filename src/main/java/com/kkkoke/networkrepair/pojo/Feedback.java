package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
   private Integer id;
   private Integer uid;
   private String submitTime;
   private String content;
   private String tel;

   public Feedback(Integer uid, String submitTime, String content, String tel) {
      this.uid = uid;
      this.submitTime = submitTime;
      this.content = content;
      this.tel = tel;
   }

   public Feedback(Integer id, Integer uid, String content, String tel) {
      this.id = id;
      this.uid = uid;
      this.content = content;
      this.tel = tel;
   }
}
