package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.dao.BookListMapper;
import com.novels.novel.domain.model.BookList;
import com.novels.novel.service.BookListService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class BookListServiceImpl extends ServiceImpl<BookListMapper, BookList> implements BookListService{

}
