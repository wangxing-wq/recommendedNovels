package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Author;
import com.novels.novel.dao.AuthorMapper;
import com.novels.novel.service.AuthorService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService{

}
