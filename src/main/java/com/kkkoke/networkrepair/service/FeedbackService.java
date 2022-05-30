package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Feedback;
import com.kkkoke.networkrepair.pojo.helper.FeedbackResult;
import com.kkkoke.networkrepair.result.ResultPage;

import java.util.List;

public interface FeedbackService {
    // 添加反馈
    Feedback addFeedback(Integer uid, String content, String tel);

    // 根据id删除反馈
    int deleteFeedback(Integer feedbackId) throws DataHasNotExistedException;

    // 修改反馈
    Feedback updateFeedback(Integer feedbackId, Integer uid, String content, String tel) throws DataHasNotExistedException;

    // 根据id查找反馈
    FeedbackResult selectFeedbackById(Integer feedbackId) throws DataHasNotExistedException;

    // 查找反馈
    ResultPage<FeedbackResult> selectFeedback(Integer feedbackId, Integer uid, String content, String tel, Integer pageNum, Integer pageSize);
}
