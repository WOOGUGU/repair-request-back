package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.exception.IllegalFormDataException;
import com.kkkoke.networkrepair.exception.IllegalOperationException;
import com.kkkoke.networkrepair.pojo.Order;
import com.kkkoke.networkrepair.result.ResultPage;

import java.util.List;

public interface OrderService {
    // 增加报修工单
    Order addOrder(String username, String sender, String tel, String type,
                   String des, String position, String timeSubscribe, String imgPath) throws IllegalFormDataException;

    // 通过id删除报修工单
    int deleteOrder(Integer orderId) throws DataHasNotExistedException;

    // 通过工单id查找报修工单
    Order selectOrderById(Integer orderId) throws DataHasNotExistedException;

    // 查找报修工单 后台接口
    ResultPage<Order> selectOrder(Integer orderId, String username, String sender, String tel, String type, String des, String position, String timeSubscribe,
                                  Integer progress, String solver, String timeStart, String timeDistribution, String timeEnd, String feedback, Integer stars,
                                  Integer pageNum, Integer pageSize) throws DataHasNotExistedException;

    // 查找所有报修工单
    ResultPage<Order> selectAllOrder(Integer pageNum, Integer pageSize) throws DataHasNotExistedException;

    // 修改报修工单
    Order updateOrder(Integer orderId, String username, String sender, String tel, String type,
                      String des, String position, String timeSubscribe, Integer progress,
                      String solver, String timeStart, String timeDistribution, String timeEnd,
                      String feedback, Integer stars, String imgPath) throws DataHasNotExistedException;

    // 审核工单
    Integer checkOrder(Integer orderId, Integer progress, String remark) throws DataHasNotExistedException;

    // 提交报修工单反馈
    Integer updateOrderFeedback(Integer orderId, String feedback, Integer stars) throws DataHasNotExistedException;

    // 查找某用户发起的所有工单
    ResultPage<Order> selectAllOrderOfUser(String username, Integer pageNum, Integer pageSize) throws DataHasNotExistedException;

    // 查找某维修员被分配的所有工单
    ResultPage<Order> selectAllOrderOfRepairman(String username, Integer pageNum, Integer pageSize) throws DataHasNotExistedException;

    // 取消工单 用户接口
    Order cancelOrder(Integer orderId, String username) throws DataHasNotExistedException, IllegalOperationException;

    // 分配维修员
    Integer sendRepairman(Integer orderId, String solver) throws DataHasNotExistedException;

    // 维修人员确定完成工单
    Integer finishOrder(Integer orderId, String feedback, Integer progress) throws DataHasNotExistedException;
}
