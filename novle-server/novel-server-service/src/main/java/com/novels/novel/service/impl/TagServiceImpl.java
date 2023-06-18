package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Tag;
import com.novels.novel.dao.TagMapper;
import com.novels.novel.service.TagService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService{

}
