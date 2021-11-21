package com.kkkoke.networkrepair.controller.pojoController;

import com.alibaba.fastjson.JSONObject;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.service.OrderService;
import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 增加报修工单
    @PostMapping("/addOrder")
    public StatusAndDataFeedback addOrder(@RequestBody JSONObject orderJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(orderJson.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 获取工单中的数据
        String sender = (String) orderJson.get("sender"); // 工单发起者（用户）
        String tel = (String) orderJson.get("tel"); // 工单发起者联系方式
        String type = (String) orderJson.get("type"); // 工单类型
        String des = (String) orderJson.get("des"); // 故障描述
        String position = (String) orderJson.get("position"); // 故障位置
        String timeSubscribe = (String) orderJson.get("timeSubscribe"); // 工单预约上门时间
//        Integer progress = Integer.parseInt((String) orderJson.get("progress")); // -2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理
//        String solver = (String) orderJson.get("solver"); // 解决工单的技术人员
        String timeStart = LocalDateTime.now().toString(); // 工单发起时间
//        String timeDistribution = (String) orderJson.get("timeDistribution"); // 工单分配时间
//        String timeEnd = (String) orderJson.get("timeEnd"); // 工单解决时间
//        String feedback = (String) orderJson.get("feedBack"); // 用户反馈

        Order order = new Order(sender, tel, type, des, position, timeSubscribe, timeStart);
        // 增加工单
        orderService.addOrder(order);

        return new StatusAndDataFeedback(order, "handle_success");
    }

    // 通过id删除报修工单
    @PostMapping("/deleteOrder")
    public StatusAndDataFeedback deleteOrder(@RequestBody JSONObject idJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(idJson.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) idJson.get("id"));
        // 查询数据库，查看要删除的工单是否存在
        if (Objects.equals(orderService.selectOrderById(id), null)) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            orderService.deleteOrder(id);
        }

        return new StatusAndDataFeedback(null, "handle_success");
    }

    // 通过工单id查找报修工单
    @PostMapping("/selectOrderById")
    public StatusAndDataFeedback selectOrderById(@RequestBody JSONObject idJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(idJson.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }
        // 从json字符串中获取要添加的数据
        Long id = Long.parseLong((String) idJson.get("id"));

        // 根据id查找工单
        Order order = orderService.selectOrderById(id);
        // 判断查询结果是否为空
        if (Objects.equals(order, null)) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            return new StatusAndDataFeedback(order, "handle_success");
        }
    }

    // 查找所有报修工单
    @PostMapping("/selectAllOrder")
    public StatusAndDataFeedback selectAllOrder() {
        List<Order> orders = orderService.selectAllOrder();
        // 判断查询结果是否为空
        if (orders.isEmpty()) {
            return new StatusAndDataFeedback(null, "data_not_exist");
        }
        else {
            return new StatusAndDataFeedback(orders, "handle_success");
        }
    }

    // 修改报修工单
    @PostMapping("/updateOrder")
    public StatusAndDataFeedback updateOrder(@RequestBody JSONObject orderJson) {
        // 判断前端传过来的参数是否为空
        if (Objects.equals(orderJson.toJSONString(), null)) {
            return new StatusAndDataFeedback(null, "Incomplete_data");
        }

        // 获取工单中的数据
        Long id = (Long) orderJson.get("id"); // 工单id
        String sender = (String) orderJson.get("sender"); // 工单发起者（用户）
        String tel = (String) orderJson.get("tel"); // 工单发起者联系方式
        String type = (String) orderJson.get("type"); // 工单类型
        String des = (String) orderJson.get("des"); // 故障描述
        String position = (String) orderJson.get("position"); // 故障位置
        String timeSubscribe = (String) orderJson.get("timeSubscribe"); // 工单预约上门时间
        Integer progress = Integer.parseInt((String) orderJson.get("progress")); // -2：审核不通过，-1：用户取消，0：待审核，1：待处理，2：已处理
        String solver = (String) orderJson.get("solver"); // 解决工单的技术人员
        String timeStart = (String) orderJson.get("timeStart"); // 工单发起时间
        String timeDistribution = (String) orderJson.get("timeDistribution"); // 工单分配时间
        String timeEnd = (String) orderJson.get("timeEnd"); // 工单解决时间
        String feedback = (String) orderJson.get("feedBack"); // 用户反馈
        Order order = new Order(id, sender, tel, type, des, position, timeSubscribe, progress, solver, timeStart, timeDistribution, timeEnd, feedback);

        // 查找数据库中是否存在此工单
        if (Objects.equals(orderService.selectOrderById(id), null)) {
            return new StatusAndDataFeedback(order, "data_not_exist");
        }
        else {
            // 如果此工单存在就更新数据
            orderService.updateOrder(order);
            return new StatusAndDataFeedback(order, "handle_success");
        }
    }
}
