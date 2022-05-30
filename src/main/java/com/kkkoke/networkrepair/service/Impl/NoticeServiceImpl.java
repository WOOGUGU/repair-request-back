package com.kkkoke.networkrepair.service.Impl;

import com.github.pagehelper.PageHelper;
import com.kkkoke.networkrepair.dao.NoticeDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Notice;
import com.kkkoke.networkrepair.result.ResultPage;
import com.kkkoke.networkrepair.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice addNotice(String content, String author, Integer displayStatus) {
        String creatTime = LocalDateTime.now().toString();
        String updateTime = LocalDateTime.now().toString();
        String announceTime = "";
        if (Objects.equals(displayStatus, 1)) {
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            announceTime = dtf2.format(LocalDateTime.now());
        }
        Notice notice = new Notice(creatTime, announceTime, updateTime, content, author, displayStatus);
        noticeDao.addNotice(notice);
        return notice;
    }

    @Override
    public Integer deleteNotice(Integer noticeId) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(noticeDao.selectNoticeById(noticeId))) {
            throw new DataHasNotExistedException("Notice has not existed");
        } else {
            return noticeDao.deleteNotice(noticeId);
        }
    }

    @Override
    public Notice updateNotice(Integer noticeId, String content, String author, Integer displayStatus) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(noticeDao.selectNoticeById(noticeId))) {
            throw new DataHasNotExistedException("Notice has not existed");
        } else {
            String updateTime = LocalDateTime.now().toString();
            String announceTime = "";
            if (Objects.equals(displayStatus, 1)) {
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                announceTime = dtf2.format(LocalDateTime.now());
            }
            Notice notice = new Notice(noticeId, announceTime, updateTime, content, author, displayStatus);
            noticeDao.updateNotice(notice);
            return notice;
        }
    }

    @Override
    public Notice selectNoticeById(Integer noticeId) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(noticeDao.selectNoticeById(noticeId))) {
            throw new DataHasNotExistedException("Notice has not existed");
        }
        return noticeDao.selectNoticeById(noticeId);
    }

    @Override
    public List<Notice> selectNoticeByAuthor(String author) throws DataHasNotExistedException {
        List<Notice> notices = noticeDao.selectNoticeByAuthor(author);
        if (ObjectUtils.isEmpty(notices)) {
            throw new DataHasNotExistedException("Notice has not existed");
        }
        return notices;
    }

    @Override
    public ResultPage<Notice> selectAllNotice(Integer pageNum, Integer pageSize) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeDao.selectAllNotice();
        ResultPage<Notice> resultPage = ResultPage.restPage(notices);
        if (ObjectUtils.isEmpty(notices)) {
            throw new DataHasNotExistedException("Notice has not existed");
        }
        return resultPage;
    }

    @Override
    public ResultPage<Notice> selectNotice(Integer noticeId, String author, Integer displayStatus, Integer pageNum, Integer pageSize) throws DataHasNotExistedException {
        if (ObjectUtils.isEmpty(pageNum)) {
            pageNum = 1;
        }
        if (ObjectUtils.isEmpty(pageSize)) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeDao.selectNotice(noticeId, author, displayStatus);
        return ResultPage.restPage(notices);
    }
}
