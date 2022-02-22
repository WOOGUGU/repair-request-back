package com.kkkoke.networkrepair.dao;

import com.kkkoke.networkrepair.pojo.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeDao {
    // 添加通知
    int addNotice(Notice notice);

    // 删除通知
    int deleteNotice(Integer id);

    // 修改通知
    int updateNotice(Notice notice);

    // 通过id查找通知
    Notice selectNoticeById(Integer id);

    // 通过author查找通知
    List<Notice> selectNoticeByAuthor(String author);

    // 查看所有通知
    List<Notice> selectAllNotice();

    // 查看通知 后台接口
    List<Notice> selectNotice(Integer id, String announceTime, String updateTime, String author);
}
