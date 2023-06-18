package com.novels.novel.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novels.novel.domain.model.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.novels.novel.domain.vo.CarouselAdminVO;
import com.novels.novel.domain.vo.CarouselVO;
import com.novels.novel.domain.vo.PageRange;

import java.util.List;

/**
 * 旋转木马服务
 *
 * @author 王兴
 * @date 2023/5/28 21:29
 */
public interface CarouselService extends IService<Carousel>{


    /**
     * 获取时间在 开始时间 <= 当前时间 < 结束时间 内的数据
     * @return 有效期内轮播数据
     */
    List<CarouselVO> currentExpire();

    CarouselVO carouselVO(Integer id);

    void batchInsert(List<CarouselAdminVO> carouselVOList);

    void batchDelete(List<Integer> ids);

    void update(CarouselAdminVO carousel);

    Page<Carousel> page(PageRange pageRange);


}
