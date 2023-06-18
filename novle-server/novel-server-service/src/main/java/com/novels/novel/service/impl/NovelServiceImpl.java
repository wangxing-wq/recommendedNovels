package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Novel;
import com.novels.novel.dao.NovelMapper;
import com.novels.novel.service.NovelService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class NovelServiceImpl extends ServiceImpl<NovelMapper, Novel> implements NovelService{

}
