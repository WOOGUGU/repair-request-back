package com.kkkoke.networkrepair.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户身份
 * 1.维修员
 * 2.管理员
 * 3.普通用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer id; // 权限Id
    private String name; // 权限名称 内部使用
    private String nameZh; // 权限名称（中文） 用以展示
}
