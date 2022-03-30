package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDao {
    // 增加报修工单
    int addOrder(Order order);

    // 通过id删除报修工单
    int deleteOrder(Integer id);

    // 通过工单id查找报修工单
    Order selectOrderById(Integer id);

    // 查找报修工单 后台接口
    List<Order> selectOrder(Integer id, String username, String sender, String tel, String type,
                            String des, String position, String timeSubscribe, Integer progress, String solver,
                            String timeStart, String timeDistribution, String timeEnd, String feedback);

    // 审核工单
    Integer checkOrder(Integer id, Integer progress);

    // 查找所有报修工单
    List<Order> selectAllOrder();

    // 修改报修工单
    Integer updateOrder(Order order);

    // 提交报修工单反馈
    Integer updateOrderFeedback(Integer id, String feedback);

    // 查找某用户发起的所有工单
    List<Order> selectAllOrderOfUser(String username);

    // 查找某维修员被分配的所有工单
    List<Order> selectAllOrderOfRepairman(String name);

    // 取消工单 用户接口
    Integer cancelOrder(Order order);

    // 分配维修员
    Integer sendRepairman(Integer id, String solver, String timeDistribution);

    // 修改工单解决时间
    Integer updateTimeEnd(Integer id, String timeEnd);
}
