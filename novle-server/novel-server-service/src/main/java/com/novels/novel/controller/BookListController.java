package com.novels.novel.controller;

import com.novels.common.annotation.LogHelper;
import com.novels.common.bean.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王兴
 * @date 2023/5/29 23:13
 */
@RestController
@LogHelper
@RequestMapping("/api/1.0/novel/bookList")
public class BookListController {
}
