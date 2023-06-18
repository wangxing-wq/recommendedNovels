package com.novels.novel.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.dao.BookshelfMapper;
import com.novels.novel.domain.model.Bookshelf;
import com.novels.novel.service.BookshelfService;
/**
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
public class BookshelfServiceImpl extends ServiceImpl<BookshelfMapper, Bookshelf> implements BookshelfService{

}
