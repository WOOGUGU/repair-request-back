package com.kkkoke.networkrepair.pojo.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private Integer id; // 用户id
    private String username; // 用户名/账号
    private String password; // 用户密码
    private String name; // 用户真实姓名
    private String tel; // 用户联系方式
    private List<Integer> roleTypes = new ArrayList<>(); // 关系属性  用来存储当前用户所有角色信息
}
