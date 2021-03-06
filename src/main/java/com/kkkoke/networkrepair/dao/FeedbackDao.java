package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Feedback;
import com.kkkoke.networkrepair.pojo.helper.FeedbackResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackDao {
    // 添加反馈
    int addFeedback(Feedback feedback);

    // 根据id删除反馈
    int deleteFeedback(Integer id);

    // 修改反馈
    int updateFeedback(Feedback feedback);

    // 根据id查找反馈
    FeedbackResult selectFeedbackById(Integer id);

    // 查找反馈
    List<FeedbackResult> selectFeedback(Feedback feedback);
}
