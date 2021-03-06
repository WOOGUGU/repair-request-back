package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.IllegalFormDataException;
import com.kkkoke.networkrepair.exception.PasswordWrongException;
import com.kkkoke.networkrepair.exception.UserHasExistedException;
import com.kkkoke.networkrepair.exception.UserHasNotExistedException;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.pojo.helper.UserDto;
import com.kkkoke.networkrepair.result.ApiResult;
import com.kkkoke.networkrepair.result.ResultCode;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
            @ApiImplicitParam(name = "name", value = "用户真实姓名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "roleTypes", value = "权限编号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "用户联系方式", required = true, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/addUser")
    public ApiResult addUser(@RequestBody UserDto userDto) throws UserHasExistedException, IllegalFormDataException {
        if (ObjectUtils.isEmpty(userDto)) {
            throw new IllegalFormDataException("缺少必要的参数");
        }
        Integer userId = userService.addUser(userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getRoleTypes(), userDto.getTel());
        return ApiResult.success(userId, "用户添加成功");
    }

    @ApiOperation(value = "通过用户名删除用户")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query")
    @Secured({"ROLE_admin"})
    @PostMapping("/deleteUser")
    public ApiResult deleteUser(Integer userId, String username) throws UserHasNotExistedException {
        if (ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(username)) {
            return ApiResult.fail(ResultCode.MISSING_PARAM, "用户Id和用户名不能同时为空", ApiResult.MISSING_PARAM);
        }
        userService.deleteUser(userId, username);
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

    @ApiOperation(value = "搜索用户 后台搜索接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户Id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户真实姓名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "用户的权限Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "用户联系方式", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectUser")
    public ApiResult selectUser(Integer userId, String username, String name, @NotNull(message = "roleId can not be null") Integer roleId, String tel, Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        ResultPage<User> users = userService.selectUser(userId, username, name, roleId, tel, pageNum, pageSize);
        return ApiResult.success(users, "查找成功");
    }

    @ApiOperation(value = "查找所有用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectAllUser")
    public ApiResult selectAllUser(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        ResultPage<User> users = userService.selectAllUser(pageNum, pageSize);
        return ApiResult.success(users, "查找成功");
    }

    @ApiOperation(value = "查找所有管理员")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured("ROLE_admin")
    @GetMapping("/selectAllAdmin")
    public ApiResult selectAllAdmin(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        ResultPage<User> admins = userService.selectAllAdmin(pageNum, pageSize);
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "查找所有维修员")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured("ROLE_admin")
    @GetMapping("/selectAllRepairman")
    public ApiResult selectAllRepairman(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        ResultPage<User> admins = userService.selectAllRepairman(pageNum, pageSize);
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "查找所有普通用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "当前页码 默认是1", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量 默认是10", required = false, paramType = "query")})
    @Secured("ROLE_admin")
    @GetMapping("/selectAllNorUser")
    public ApiResult selectAllNorUser(Integer pageNum, Integer pageSize) throws UserHasNotExistedException {
        ResultPage<User> admins = userService.selectAllNorUser(pageNum, pageSize);
        return ApiResult.success(admins, "查找成功");
    }

    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户Id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码（已加密）", required = false, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户真实姓名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "roleTypes", value = "用户权限Id列表", required = false, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "用户联系方式", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/updateUser")
    public ApiResult updateUser(@RequestBody UserDto userDto) throws UserHasNotExistedException, PasswordWrongException {
        userService.updateUser(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getRoleTypes(), userDto.getTel());
        return ApiResult.success("用户修改成功");
    }
}
