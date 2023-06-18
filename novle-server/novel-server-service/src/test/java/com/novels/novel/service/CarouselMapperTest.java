package com.novels.novel.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.novels.novel.NovelServerApp;
import com.novels.novel.dao.CarouselMapper;
import com.novels.novel.domain.model.Carousel;
import com.novels.novel.domain.vo.CarouselVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 王兴
 * @date 2023/5/30 22:57
 */
@Slf4j
@SpringBootTest
@Import(NovelServerApp.class)
public class CarouselMapperTest {

    @Autowired
    CarouselMapper carouselMapper;

    @Autowired
    CarouselService carouselService;

    @Test
    public void currentExpire(){
        List<CarouselVO> carouselVOList = carouselService.currentExpire();
        for (CarouselVO carouselVO : carouselVOList) {
            log.info("{}",carouselVO);
        }
    }


    @Test
    public void insert() {
        Carousel carousel = new Carousel();

//        carousel.setCarouselId(); 不设置自动生成
        carousel.setImgUrl("www.baidu.com/images/1.png");
        carousel.setLink("www.baidu.com");
        carousel.setSort(1);
        carousel.setCreateBy("1");
        carousel.setCreateByName("王兴");
        carousel.setCreateTime(LocalDateTime.now());
        carousel.setUpdateTime(LocalDateTime.now());
        int insert = carouselMapper.insert(carousel);
        assertEquals(insert, 0, "插入失败");
    }

    @Test
    public void delete() {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        Carousel carousel = new Carousel();
        // 根据QueryWrapper 删除
        carousel.setCarouselId(1);
        queryWrapper.setEntity(carousel);
        int delete = carouselMapper.delete(queryWrapper);
        log.info("根据条件删除:{}", delete);
        // 根据主键删除
        carouselMapper.deleteById(2);
        int i = carouselMapper.deleteBatchIds(Lists.newArrayList(1, 2, 3, 4));
        log.info("根据主键批量删除:{}", i);

    }

    @Test
    public void update() {
        QueryWrapper<Carousel> query = new QueryWrapper<Carousel>();
        Carousel carousel = new Carousel();
        carousel.setCarouselId(5);
        carousel.setCreateByName("王兴");

        int update = carouselMapper.update(carousel, query);
        log.info("wrapper updated:{}", update);
        carousel.setCreateByName("test");
        int i = carouselMapper.updateById(carousel);
        log.info("wrapper updated:{}", i);

    }

    @Test
    public void select() {
        // 根据主键查询
        Carousel carousel = carouselMapper.selectById(5);
        log.info("主键查询:{}", carousel);
        List<Carousel> carousels = carouselMapper.selectBatchIds(Lists.newArrayList(3, 4, 9, 10));
        log.info("批量查询:{}", carousels);
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.le(Carousel.COL_CAROUSEL_ID, 20);
        Long aLong = carouselMapper.selectCount(queryWrapper);
        log.info("查询数量:{}", aLong);
        List<Carousel> carousels1 = carouselMapper.selectList(queryWrapper);
        queryWrapper.getSqlSelect();
        log.info("条件查询:{}", carousels1);
        // 分页查询
    }

    @Test
    public void functionSelect() {
        QueryWrapper<Carousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,%Y%m-%d)=0", "2023-01-6");


        // () and () or (xxx 1 = 1 and 2 = 2)

        // () and name = ? 语句

        // 限制返回
        queryWrapper.last(true, "limit 3");
        // condition 是否启用这个查询条件,condition
        queryWrapper.like(true, Carousel.COL_CREATE_BY_NAME, "王");
    }

    @Test
    public void lambda() {
        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new QueryWrapper<Carousel>().lambda();
        lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Carousel::getCarouselId, 5);
        List<Carousel> carousels = carouselMapper.selectList(lambdaQueryWrapper);
        log.info("{}",carousels);
    }

    @Test
    public void page(){
        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new QueryWrapper<Carousel>().lambda();
        lambdaQueryWrapper.gt(Carousel::getCarouselId,5);
        IPage<Carousel> iPage1 = carouselMapper.selectPage(new Page<>(3,3), lambdaQueryWrapper);
        log.info("{}", iPage1.getPages());
        log.info("{}", iPage1.getRecords());
        log.info("{}", iPage1.getCurrent());
        log.info("{}", iPage1.getSize());
        log.info("{}", iPage1.getTotal());
    }
}
