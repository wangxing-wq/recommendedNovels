package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Dictionary;
import com.novels.novel.dao.DictionaryMapper;
import com.novels.novel.service.DictionaryService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService{

}
