package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.OrderDao;
import com.kkkoke.networkrepair.dao.UserDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.exception.IllegalFormDataException;
import com.kkkoke.networkrepair.exception.IllegalOperationException;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.pojo.User;
import com.kkkoke.networkrepair.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    // 增加报修工单
    @Override
    public Order addOrder(String username, String sender, String tel, String type,
                          String des, String position, String timeSubscribe, String imgPath) throws IllegalFormDataException {
        String timeStart = LocalDateTime.now().toString();
        LocalDate now = LocalDate.now();
        LocalDate after = LocalDate.now().plusDays(3);
        LocalDate tempTime = LocalDate.parse(timeSubscribe.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (tempTime.isBefore(now) || tempTime.isEqual(now) || tempTime.isAfter(after)) {
            throw new IllegalFormDataException("预约时间过近，无法及时处理");
        }

        Order order = new Order(username, sender, tel, type, des, position, timeSubscribe, timeStart, imgPath);

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
                                   String timeStart, String timeDistribution, String timeEnd, String feedback, Integer stars) throws DataHasNotExistedException {
        return orderDao.selectOrder(orderId, username, sender, tel, type, des, position, timeSubscribe, progress, solver, timeStart, timeDistribution, timeEnd, feedback, stars);
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
                             String solver, String timeStart, String timeDistribution, String timeEnd,
                             String feedback, Integer stars, String imgPath) throws DataHasNotExistedException {
        Order order = orderDao.selectOrderById(orderId);
        // 查找数据库中是否存在此工单
        if (ObjectUtils.isEmpty(order)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果工单存在就更新数据
            if (!ObjectUtils.isEmpty(username)) {
                order.setUsername(username);
            }
            if (!ObjectUtils.isEmpty(sender)) {
                order.setSender(sender);
            }
            if (!ObjectUtils.isEmpty(tel)) {
                order.setTel(tel);
            }
            if (!ObjectUtils.isEmpty(type)) {
                order.setType(type);
            }
            if (!ObjectUtils.isEmpty(des)) {
                order.setDes(des);
            }
            if (!ObjectUtils.isEmpty(position)) {
                order.setPosition(position);
            }
            if (!ObjectUtils.isEmpty(timeSubscribe)) {
                order.setTimeSubscribe(timeSubscribe);
            }
            if (!ObjectUtils.isEmpty(progress)) {
                order.setProgress(progress);
            }
            if (!ObjectUtils.isEmpty(solver)) {
                order.setSolver(solver);
            }
            if (!ObjectUtils.isEmpty(timeStart)) {
                order.setTimeStart(timeStart);
            }
            if (!ObjectUtils.isEmpty(timeDistribution)) {
                order.setTimeDistribution(timeDistribution);
            }
            if (!ObjectUtils.isEmpty(timeEnd)) {
                order.setTimeEnd(timeEnd);
            }
            if (!ObjectUtils.isEmpty(feedback)) {
                order.setFeedback(feedback);
            }
            if (!ObjectUtils.isEmpty(stars)) {
                order.setStars(stars);
            }
            if (!ObjectUtils.isEmpty(imgPath)) {
                order.setImgPath(imgPath);
            }
            orderDao.updateOrder(order);
            return order;
        }
    }

    // 修改报修工单
    @Override
    public Integer updateOrderFeedback(Integer orderId, String feedback, Integer stars) throws DataHasNotExistedException {
        // 查找数据库中是否存在此用户
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就更新数据
            orderDao.updateOrderFeedback(orderId, feedback, stars);
            return 0;
        }
    }

    // 审核工单
    @Override
    public Integer checkOrder(Integer orderId, Integer progress, String remark) throws DataHasNotExistedException {
        // 查找数据库中是否存在此用户
        Order order = orderDao.selectOrderById(orderId);
        if (ObjectUtils.isEmpty(order)) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果用户存在就修改工单状态
            if (progress == 4) {
                order.setTimeDistribution(null);
                order.setSolver(null);
                order.setTimeEnd(null);
                order.setFeedback(null);
                order.setStars(null);
                orderDao.updateOrder(order);
            }
            orderDao.checkOrder(orderId, progress, remark);
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
            User repairman = userDao.selectUserByUsername(solver);
            orderDao.checkOrder(orderId, 1, "");
            return orderDao.sendRepairman(orderId, repairman.getName() + " " + repairman.getTel(), timeDistribution);
        }
    }

    // 维修人员确定完成工单
    @Override
    public Integer finishOrder(Integer orderId, String feedback) throws DataHasNotExistedException {
        // 查找数据库中是否存在此工单
        if (ObjectUtils.isEmpty(orderDao.selectOrderById(orderId))) {
            throw new DataHasNotExistedException("Order has not existed");
        } else {
            // 如果工单存在就修改工单状态
            orderDao.finishOrder(orderId, feedback, 2);
            orderDao.updateTimeEnd(orderId, LocalDateTime.now().toString());
            return 0;
        }
    }
}
