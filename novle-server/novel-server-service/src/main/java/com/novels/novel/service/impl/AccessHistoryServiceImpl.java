package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.AccessHistory;
import com.novels.novel.dao.AccessHistoryMapper;
import com.novels.novel.service.AccessHistoryService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class AccessHistoryServiceImpl extends ServiceImpl<AccessHistoryMapper, AccessHistory> implements AccessHistoryService{

}
