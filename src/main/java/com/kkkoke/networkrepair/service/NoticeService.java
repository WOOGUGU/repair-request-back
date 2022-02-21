package com.kkkoke.networkrepair.service;

import com.kkkoke.networkrepair.exception.DataHasNotExistedException;
import com.kkkoke.networkrepair.pojo.Notice;

import java.util.List;

public interface NoticeService {
    // 添加通知
    Notice addNotice(String content, String author, Integer displayStatus);

    // 删除通知
    Integer deleteNotice(Integer noticeId) throws DataHasNotExistedException;

    // 修改通知
    Notice updateNotice(String content, String author, Integer displayStatus) throws DataHasNotExistedException;

    // 通过id查找通知
    Notice selectNoticeById(Integer noticeId) throws DataHasNotExistedException;

    // 通过author查找通知
    List<Notice> selectNoticeByAuthor(String author) throws DataHasNotExistedException;

    // 查看所有通知
    List<Notice> selectAllNotice() throws DataHasNotExistedException;

    // 查看通知 后台接口
    List<Notice> selectNotice(Integer id, String announceTime, String updateTime, String author) throws DataHasNotExistedException;
}
