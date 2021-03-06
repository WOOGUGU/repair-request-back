package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Notice;
import com.kkkoke.networkrepair.result.ResultPage;

import java.util.List;

public interface NoticeService {
    // 添加通知
    Notice addNotice(String content, String author, Integer displayStatus);

    // 删除通知
    Integer deleteNotice(Integer noticeId) throws DataHasNotExistedException;

    // 修改通知
    Notice updateNotice(Integer noticeId, String content, String author, Integer displayStatus) throws DataHasNotExistedException;

    // 通过id查找通知
    Notice selectNoticeById(Integer noticeId) throws DataHasNotExistedException;

    // 通过author查找通知
    List<Notice> selectNoticeByAuthor(String author) throws DataHasNotExistedException;

    // 查看所有通知
    ResultPage<Notice> selectAllNotice(Integer pageNum, Integer pageSize) throws DataHasNotExistedException;

    // 查看通知 后台接口
    ResultPage<Notice> selectNotice(Integer noticeId, String author, Integer displayStatus, Integer pageNum, Integer pageSize) throws DataHasNotExistedException;
}
