package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Announcement;
import com.novels.novel.dao.AnnouncementMapper;
import com.novels.novel.service.AnnouncementService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService{

}
