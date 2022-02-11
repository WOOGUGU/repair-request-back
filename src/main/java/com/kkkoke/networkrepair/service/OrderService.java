package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Order;
import java.util.List;

public interface OrderService {
    // 增加报修工单
    Order addOrder(String username, String sender, String tel, String type,
                   String des, String position, String timeSubscribe, String timeStart);

    // 通过id删除报修工单
    int deleteOrder(Integer orderId) throws DataHasNotExistedException;

    // 通过工单id查找报修工单
    Order selectOrderById(Integer orderId) throws DataHasNotExistedException;

    // 查找所有报修工单
    List<Order> selectAllOrder() throws DataHasNotExistedException;

    // 修改报修工单
    Order updateOrder(Integer orderId, String username, String sender, String tel, String type,
                        String des, String position, String timeSubscribe, Integer progress,
                        String solver, String timeStart, String timeDistribution, String timeEnd, String feedback) throws DataHasNotExistedException;

    // 查找某用户发起的所有工单
    List<Order> selectAllOrderOfUser(String username) throws DataHasNotExistedException;
}
