package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class User {
    private Long id; // 用户id
    private String username; // 用户名/账号
    private String password; // 用户密码
    private String name; // 用户真实姓名
}
