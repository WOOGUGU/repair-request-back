package com.kkkoke.networkrepair.controller.pojoController;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.exception.IllegalOperationException;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.result.ResultCode;
import com.kkkoke.networkrepair.service.OrderService;
import com.kkkoke.networkrepair.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author kkkoke
 */
@Api(tags = "工单管理")
@Slf4j
@Validated
@RequestMapping("/v2/order")
@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "增加报修工单")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query"),
            @ApiImplicitParam(name = "sender", value = "工单发起者（用户）", required = true, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "工单发起者联系方式", required = true, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "工单类型", required = true, paramType = "query"),
            @ApiImplicitParam(name = "des", value = "故障描述", required = true, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "故障位置", required = true, paramType = "query"),
            @ApiImplicitParam(name = "timeSubscribe", value = "工单预约上门时间", required = true, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @PostMapping("/addOrder")
    public ApiResult addOrder(@NotBlank(message = "username can not be null") String username, @NotBlank(message = "sender can not be null") String sender,
                              @NotBlank(message = "tel can not be null") String tel, @NotBlank(message = "type can not be null") String type,
                              @NotBlank(message = "des can not be null") String des, @NotBlank(message = "position can not be null") String position,
                              @NotBlank(message = "timeSubscribe can not be null") String timeSubscribe) {
        orderService.addOrder(username, sender, tel, type, des, position, timeSubscribe);
        return ApiResult.success("工单添加成功");
    }

    @ApiOperation(value = "通过id删除报修工单")
    @ApiImplicitParam(name = "orderId", value = "工单Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user"})
    @PostMapping("/deleteOrder")
    public ApiResult deleteOrder(@NotNull(message = "orderId can not be null") Integer orderId) throws DataHasNotExistedException {
        orderService.deleteOrder(orderId);
        return ApiResult.success("工单删除成功");
    }

    @ApiOperation(value = "通过工单id查找报修工单")
    @ApiImplicitParam(name = "orderId", value = "工单Id", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectOrderById")
    public ApiResult selectOrderById(@NotNull(message = "orderId can not be null") Integer orderId) throws DataHasNotExistedException {
        Order order = orderService.selectOrderById(orderId);
        return ApiResult.success(order, "查找成功");
    }

    @ApiOperation(value = "查找报修工单 后台接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "工单id", required = false, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "sender", value = "工单发起者（用户）", required = false, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "工单发起者联系方式", required = false, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "工单类型", required = false, paramType = "query"),
            @ApiImplicitParam(name = "des", value = "故障描述", required = false, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "故障位置", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeSubscribe", value = "工单预约上门时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "progress", value = "-2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理", required = false, paramType = "query"),
            @ApiImplicitParam(name = "solver", value = "解决工单的技术人员", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeStart", value = "工单发起时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeDistribution", value = "工单分配时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeEnd", value = "工单解决时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "feedback", value = "用户反馈", required = false, paramType = "query")})
    @Secured("ROLE_admin")
    @GetMapping("/selectOrder")
    public ApiResult selectOrder(Integer orderId, String username, String sender, String tel, String type,
                                 String des, String position, String timeSubscribe, Integer progress, String solver, String timeStart, String timeDistribution,
                                 String timeEnd, String feedback) throws DataHasNotExistedException {
        List<Order> orders = orderService.selectOrder(orderId, username, sender, tel, type, des, position, timeSubscribe, progress,
                solver, timeStart, timeDistribution, timeEnd, feedback);
        return ApiResult.success(orders, "查找成功");
    }

    @ApiOperation(value = "查找所有报修工单")
    @Secured({"ROLE_admin", "ROLE_repairman"})
    @GetMapping("/selectAllOrder")
    public ApiResult selectAllOrder() throws DataHasNotExistedException {
        List<Order> orders = orderService.selectAllOrder();
        return ApiResult.success(orders, "查找成功");
    }

    @ApiOperation(value = "修改报修工单")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "工单id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = false, paramType = "query"),
            @ApiImplicitParam(name = "sender", value = "工单发起者（用户）", required = false, paramType = "query"),
            @ApiImplicitParam(name = "tel", value = "工单发起者联系方式", required = false, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "工单类型", required = false, paramType = "query"),
            @ApiImplicitParam(name = "des", value = "故障描述", required = false, paramType = "query"),
            @ApiImplicitParam(name = "position", value = "故障位置", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeSubscribe", value = "工单预约上门时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "progress", value = "-2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理", required = false, paramType = "query"),
            @ApiImplicitParam(name = "solver", value = "解决工单的技术人员", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeStart", value = "工单发起时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeDistribution", value = "工单分配时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "timeEnd", value = "工单解决时间", required = false, paramType = "query"),
            @ApiImplicitParam(name = "feedback", value = "用户反馈", required = false, paramType = "query")})
    @Secured("ROLE_admin")
    @PostMapping("/updateOrder")
    public ApiResult updateOrder(@NotNull(message = "orderId can not be null") Integer orderId, String username, String sender, String tel, String type,
                                 String des, String position, String timeSubscribe, Integer progress, String solver, String timeStart, String timeDistribution,
                                 String timeEnd, String feedback) throws DataHasNotExistedException {
        orderService.updateOrder(orderId, username, sender, tel, type, des, position, timeSubscribe, progress,
                solver, timeStart, timeDistribution, timeEnd, feedback);
        return ApiResult.success("更新成功");
    }

    @ApiOperation(value = "提交报修工单反馈")
    @ApiImplicitParam(name = "feedback", value = "用户反馈", required = false, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user"})
    @PostMapping("/updateOrderFeedback")
    public ApiResult updateOrderFeedback(@NotNull(message = "orderId can not be null") Integer orderId,
                                         @NotBlank(message = "feedback can not be null") @Size(min = 1, max = 100) String feedback) throws DataHasNotExistedException {
        orderService.updateOrderFeedback(orderId, feedback);
        return ApiResult.success("更新成功");
    }

    @ApiOperation(value = "审核工单")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "工单id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "progress", value = "-2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理", required = false, paramType = "query")})
    @Secured({"ROLE_admin"})
    @PostMapping("/checkOrder")
    public ApiResult checkOrder(@NotNull(message = "orderId can not be null") Integer orderId, Integer progress) throws DataHasNotExistedException {
        orderService.checkOrder(orderId, progress);
        return ApiResult.success("审核成功");
    }

    @ApiOperation(value = "查找某用户发布的所有工单")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query")
    @Secured({"ROLE_admin", "ROLE_user", "ROLE_repairman"})
    @GetMapping("/selectAllOrderOfUser")
    public ApiResult selectAllOrderOfUser(@NotBlank(message = "username can not be null") String username) throws DataHasNotExistedException {
        List<Order> orders = orderService.selectAllOrderOfUser(username);
        return ApiResult.success(orders, "查找成功");
    }

    @ApiOperation(value = "取消报修工单 用户接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "orderId", value = "工单id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query")})
    @Secured({"ROLE_admin", "ROLE_user"})
    @PostMapping("/cancelOrder")
    public ApiResult cancelOrder(@NotNull(message = "orderId can not be null") Integer orderId,
                                 @NotBlank(message = "username can not be null") String username) throws DataHasNotExistedException, IllegalOperationException {
        orderService.cancelOrder(orderId, username);
        return ApiResult.success("取消成功");
    }
}
