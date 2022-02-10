package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id; // 用户id
    private String username; // 用户名/账号
    private String password; // 用户密码
    private String name; // 用户真实姓名
    private List<Role> roles = new ArrayList<>(); // 关系属性  用来存储当前用户所有角色信息

    // 不带id的用户构造函数
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
