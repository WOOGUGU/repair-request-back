package com.kkkoke.networkrepair.service.Impl;

import com.kkkoke.networkrepair.dao.NoticeDao;
import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Notice;
import com.kkkoke.networkrepair.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Notice addNotice(String content, String author, Integer displayStatus) {
        String creatTime = LocalDateTime.now().toString();
        Notice notice = new Notice(creatTime, content, author, displayStatus);
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
    public Notice updateNotice(String content, String author, Integer displayStatus) throws DataHasNotExistedException {
        String updateTime = LocalDateTime.now().toString();
        String announceTime = "";
        if (Objects.equals(displayStatus, 1)) {
            announceTime = LocalDateTime.now().toString();
        }
        Notice notice = new Notice(announceTime, updateTime, content, author, displayStatus);
        noticeDao.updateNotice(notice);
        return notice;
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
    public List<Notice> selectAllNotice() throws DataHasNotExistedException {
        List<Notice> notices = noticeDao.selectAllNotice();
        if (ObjectUtils.isEmpty(notices)) {
            throw new DataHasNotExistedException("Notice has not existed");
        }
        return notices;
    }

    @Override
    public List<Notice> selectNotice(Integer noticeId, String announceTime, String updateTime, String author) throws DataHasNotExistedException {
        return noticeDao.selectNotice(noticeId, announceTime, updateTime, author);
    }
}
