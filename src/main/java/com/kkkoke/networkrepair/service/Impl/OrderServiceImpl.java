package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.OrderDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.exception.IllegalOperationException;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    // 增加报修工单
    @Override
    public Order addOrder(String username, String sender, String tel, String type,
                          String des, String position, String timeSubscribe) {
        String timeStart = LocalDateTime.now().toString();
        Order order = new Order(username, sender, tel, type, des, position, timeSubscribe, timeStart);
        orderDao.addOrder(order);
        return order;
    }

    // 通过id删除报修工单
    @Override
    public int deleteOrder(Integer orderId) throws DataHasNotExistedException {
        // 查询数据库，查看要删除的工单是否存在
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            return orderDao.deleteOrder(orderId);
        }
    }

    // 通过工单id查找报修工单
    @Override
    public Order selectOrderById(Integer orderId) throws DataHasNotExistedException {
        // 根据工单Id查找用户
        Order order = orderDao.selectOrderById(orderId);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(order)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            return order;
        }
    }

    // 查找报修工单 后台接口
    @Override
    public List<Order> selectOrder(Integer orderId, String username, String sender, String tel, String type,
                                   String des, String position, String timeSubscribe, Integer progress, String solver,
                                   String timeStart, String timeDistribution, String timeEnd, String feedback) throws DataHasNotExistedException {
        return orderDao.selectOrder(orderId, username, sender, tel, type, des, position, timeSubscribe, progress, solver, timeStart, timeDistribution, timeEnd, feedback);
    }

    // 查找所有报修工单
    @Override
    public List<Order> selectAllOrder() throws DataHasNotExistedException {
        List<Order> orders = orderDao.selectAllOrder();
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(orders)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            return orders;
        }
    }

    // 修改报修工单
    @Override
    public Order updateOrder(Integer orderId, String username, String sender, String tel, String type,
                               String des, String position, String timeSubscribe, Integer progress,
                               String solver, String timeStart, String timeDistribution, String timeEnd, String feedback) throws DataHasNotExistedException {
        // 创建要修改的order对象
        Order order = new Order(orderId, username, sender, tel, type, des, position, timeSubscribe, progress, solver, timeStart, timeDistribution, timeEnd, feedback);
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就更新数据
            orderDao.updateOrder(order);
            return order;
        }
    }

    // 修改报修工单
    @Override
    public Integer updateOrderFeedback(Integer orderId, String feedback) throws DataHasNotExistedException {
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就更新数据
            orderDao.updateOrderFeedback(orderId, feedback);
            return 0;
        }
    }

    // 审核工单
    @Override
    public Integer checkOrder(Integer orderId, Integer progress) throws DataHasNotExistedException {
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就修改工单状态
            orderDao.checkOrder(orderId, progress);
            return 0;
        }
    }

    // 查找某用户发起的所有工单
    @Override
    public List<Order> selectAllOrderOfUser(String username) throws DataHasNotExistedException {
        List<Order> orders = orderDao.selectAllOrderOfUser(username);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(orders)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            return orders;
        }
    }

    // 查找某维修员被分配的所有工单
    @Override
    public List<Order> selectAllOrderOfRepairman(String name) throws DataHasNotExistedException {
        List<Order> orders = orderDao.selectAllOrderOfRepairman(name);
        // 判断查询结果是否为空
        if (ObjectUtils.isEmpty(orders)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            return orders;
        }
    }

    // 取消工单 用户接口
    @Override
    public Order cancelOrder(Integer orderId, String username) throws DataHasNotExistedException, IllegalOperationException {
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 确认是否是自己的工单
            if (orderDao.selectOrderById(orderId).getUsername().equals(username)) {
                Order order = new Order(orderId, 3);
                orderDao.cancelOrder(order);
                return order;
            } else {
                throw new IllegalOperationException("Illegal Operation");
            }
        }
    }

    // 分配维修员
    @Override
    public Integer sendRepairman(Integer orderId, String solver) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            String timeDistribution = LocalDateTime.now().toString();
            return orderDao.sendRepairman(orderId, solver, timeDistribution);
        }
    }

    // 维修人员确定完成工单
    @Override
    public Integer finishOrder(Integer orderId) throws DataHasNotExistedException {
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就修改工单状态
            orderDao.checkOrder(orderId, 2);
            return 0;
        }
    }
}
