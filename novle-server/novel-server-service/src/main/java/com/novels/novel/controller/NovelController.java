package com.novels.novel.controller;

import com.novels.common.bean.Result;
import com.novels.novel.domain.model.Carousel;
import com.novels.novel.domain.model.Novel;
import com.novels.novel.domain.vo.NovelSearchVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小说
 * @author 王兴
 * @date 2023/5/28 21:46
 */
@RestController
@RequestMapping("/api/1.0/novel/book")
public class NovelController {

    /**
     * 搜索小说
     * @param searchVo 搜索需要用到的参数
     * @return
     */
    public Result<Novel> search(NovelSearchVo searchVo) {
        return null;
    }

    /**
     * 轮播图,查看当前内容期限内可用的
     * @return
     */
    public Result<Carousel> carousel() {
        return null;
    }

    /**
     * 推荐小说,根据小说点击量等等相关性推荐,后续优化成根据用户兴趣推荐
     * @return
     */
    public Result<Novel> recommend(){
        return null;
    }

    /**
     * 热门小说排行榜,点击率,评论量,收藏量
     * @return
     */
    public Result<Novel> topNovel(){
        return null;
    }

    /**
     * 当前有效的公告
     * @return
     */
    public Result<Novel> announcement(){
        return null;
    }

    /**
     * 热门书单
     * @return
     */
    public Result<Novel> topBookSelf(){
        return null;
    }

    public Result<Novel> comment(){
        return null;
    }

}
