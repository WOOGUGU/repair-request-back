package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Order;

import java.util.List;

public interface OrderDao {
    // 增加报修工单
    int addOrder(Order order);

    // 通过id删除报修工单
    int deleteOrder(Long id);

    // 通过工单id查找报修工单
    Order selectOrderById(Long id);

    // 查找所有报修工单
    List<Order> selectAllOrder();

    // 修改报修工单
    Order updateOrder(Order order);
}
