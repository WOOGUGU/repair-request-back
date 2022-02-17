package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.UserService;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author kkkoke
 */
@Api(tags = "用户管理")
@Slf4j
@Validated
@RequestMapping("/v2/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "添加用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码（已加密）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户真实姓名", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addUser")
    public ApiResult addUser(@NotBlank(message = "username can not be null") String username, @NotBlank(message = "password can not be null") String password,
                             @NotBlank(message = "name can not be null") String name, @NotNull(message = "roleType can not be null") Integer roleType) throws UserHasExistedException {
        userService.addUser(username, password, name, roleType);
        return ApiResult.success("用户添加成功");
    }

    @ApiOperation(value = "通过用户名删除用户")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteUser")
    public ApiResult deleteUser(@NotNull(message = "userId can not be null") Integer userId) throws UserHasNotExistedException {
        userService.deleteUser(userId);
        return ApiResult.success("用户删除成功");
    }

    @ApiOperation(value = "通过用户名查找用户")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectUserByUsername")
    public ApiResult selectUserByUsername(@NotBlank(message = "username can not be null") String username) throws UserHasNotExistedException {
        User user = userService.selectUserByUsername(username);
        return ApiResult.success(user, "查找成功");
    }

    @ApiOperation(value = "通过id查找用户")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectUserById")
    public ApiResult selectUserById(@NotNull(message = "userId can not be null") Integer userId) throws UserHasNotExistedException {
        User user = userService.selectUserById(userId);
        return ApiResult.success(user, "查找成功");
    }

    @ApiOperation(value = "查找所有用户")
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectAllUser")
    public ApiResult selectAllUser() throws UserHasNotExistedException {
        List<User> users = userService.selectAllUser();
        return ApiResult.success(users, "查找成功");
    }

    @ApiOperation(value = "查找所有管理员")
    @Secured("ROLE_admin")
    @GetMapping("/selectAllAdmin")
    public ApiResult selectAllAdmin() throws UserHasNotExistedException {
        List<User> admins = userService.selectAllAdmin();
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "查找所有维修员")
    @Secured("ROLE_admin")
    @GetMapping("/selectAllRepairman")
    public ApiResult selectAllRepairman() throws UserHasNotExistedException {
        List<User> admins = userService.selectAllRepairman();
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "查找所有普通用户")
    @Secured("ROLE_admin")
    @GetMapping("/selectAllNorUser")
    public ApiResult selectAllNorUser() throws UserHasNotExistedException {
        List<User> admins = userService.selectAllNorUser();
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码（已加密）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户真实姓名", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateUser")
    public ApiResult updateUser(@NotNull(message = "userId can not be null") Integer userId, @NotBlank(message = "username can not be null") String username,
                                @NotBlank(message = "password can not be null") String password, @NotBlank(message = "name can not be null") String name) throws UserHasNotExistedException {
        userService.updateUser(userId, username, password, name);
        return ApiResult.success("更新成功");
    }
}
