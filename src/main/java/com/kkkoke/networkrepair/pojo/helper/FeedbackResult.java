package com.kkkoke.networkrepair.pojo.helper;

import com.kkkoke.networkrepair.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResult {
    private Integer id;
    private String submitTime;
    private String content;
    private String tel;
    private User user;
}
