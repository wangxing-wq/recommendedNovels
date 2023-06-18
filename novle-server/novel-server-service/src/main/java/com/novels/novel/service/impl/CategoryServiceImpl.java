package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Category;
import com.novels.novel.dao.CategoryMapper;
import com.novels.novel.service.CategoryService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

}
