package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.FeedbackDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Feedback;
import com.kkkoke.networkrepair.pojo.helper.FeedbackResult;
import com.kkkoke.networkrepair.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public Feedback addFeedback(Integer uid, String content, String tel) {
        String submitTime = LocalDateTime.now().toString();
        Feedback feedback = new Feedback(uid, submitTime, content, tel);
        feedbackDao.addFeedback(feedback);
        return feedback;
    }

    @Override
    public int deleteFeedback(Integer feedbackId) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(feedbackDao.selectFeedbackById(feedbackId))) {
            throw new DataHasNotExistedException("feedback is not exist");
        }
        return feedbackDao.deleteFeedback(feedbackId);
    }

    @Override
    public Feedback updateFeedback(Integer feedbackId, Integer uid, String content, String tel) throws DataHasNotExistedException {
        FeedbackResult feedbackResult = feedbackDao.selectFeedbackById(feedbackId);
        if (ObjectUtils.isEmpty(feedbackResult)) {
            throw new DataHasNotExistedException("feedback is not exist");
        }
        Feedback feedback = new Feedback();
        feedback.setContent(feedbackResult.getContent());
        feedback.setTel(feedbackResult.getTel());
        feedback.setUid(feedbackResult.getUser().getId());
        String submitTime = LocalDateTime.now().toString();
        feedback.setSubmitTime(submitTime);
        if (!ObjectUtils.isEmpty(content)) {
            feedback.setContent(content);
        }
        if (!ObjectUtils.isEmpty(uid)) {
            feedback.setId(uid);
        }
        if (!ObjectUtils.isEmpty(tel)) {
            feedback.setTel(tel);
        }

        feedbackDao.updateFeedback(feedback);
        return feedback;
    }

    @Override
    public FeedbackResult selectFeedbackById(Integer feedbackId) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(feedbackDao.selectFeedbackById(feedbackId))) {
            throw new DataHasNotExistedException("feedback is not exist");
        }
        return feedbackDao.selectFeedbackById(feedbackId);
    }

    @Override
    public List<FeedbackResult> selectFeedback(Integer feedbackId, Integer uid, String content, String tel) {
        Feedback feedback = new Feedback(feedbackId, uid, content, tel);
        return feedbackDao.selectFeedback(feedback);
    }
}
