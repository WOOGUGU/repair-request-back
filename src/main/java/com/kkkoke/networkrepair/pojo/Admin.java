package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 管理员id
    private String username; // 管理员账号
    private String password; // 管理员密码
    private String name; // 管理员真实姓名

    // 不带id的管理员构造函数
    public Admin(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
