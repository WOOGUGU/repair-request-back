package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.OrderDao;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    // 增加报修工单
    public int addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    // 通过id删除报修工单
    public int deleteOrder(Long id) {
        return orderDao.deleteOrder(id);
    }

    @Override
    // 通过工单id查找报修工单
    public Order selectOrderById(Long id) {
        return orderDao.selectOrderById(id);
    }

    @Override
    // 查找所有报修工单
    public List<Order> selectAllOrder() {
        return orderDao.selectAllOrder();
    }

    @Override
    // 修改报修工单
    public Integer updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Override
    // 查找某用户发起的所有工单
    public List<Order> selectAllOrderOfUser(String username) {
        return orderDao.selectAllOrderOfUser(username);
    }
}
