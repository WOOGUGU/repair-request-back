package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin {
    private Long id; // 管理员id
    private String username; // 管理员账号
    private String password; // 管理员密码
    private String name; // 管理员真实姓名
}
