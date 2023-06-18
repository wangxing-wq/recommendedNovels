package com.novels.novel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novels.common.annotation.LogHelper;
import com.novels.common.bean.Result;
import com.novels.novel.constant.ApiVersionConstant;
import com.novels.novel.domain.model.Carousel;
import com.novels.novel.domain.vo.CarouselAdminVO;
import com.novels.novel.domain.vo.CarouselVO;
import com.novels.novel.domain.vo.PageRange;
import com.novels.novel.service.CarouselService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制
 * @author 王兴
 * @date 2023/6/3 21:21
 */
@LogHelper
@RestController
@RequestMapping(ApiVersionConstant.ONE + "carousel")
public class CarouselController {

    /***
     * author
     */
    private final CarouselService carouselService;

    public CarouselController(CarouselService carouselService) {
        this.carouselService = carouselService;
    }

    /**
     * 获取当前内有效的轮播图
     * @return {@link Result}<{@link CarouselVO}>
     */
    @PostMapping("/show")
    public Result<CarouselVO> show(){
        List<CarouselVO> carouselVOList =  carouselService.currentExpire();
        return Result.success(carouselVOList);
    }

    /**
     * 查看轮播图
     * @param id 主键
     * @return 轮播信息
     */
    @PostMapping("show/{id}")
    public Result<CarouselVO> showId(@PathVariable Integer id) {
        CarouselVO carouselVO = carouselService.carouselVO(id);
        return Result.success(carouselVO);
    }

    /**
     * 批量添加轮播图
     * @param carouselVOList 需要批量插入的轮播图信息
     * @return 批量添加
     */
    @PostMapping("batchImport")
    public Result<Void> batchAdd(@RequestBody List<CarouselAdminVO> carouselAdminVOList) {
        carouselService.batchInsert(carouselAdminVOList);
        return Result.success();
    }


    /**
     * 更新
     * @param carouselAdminVO 要修改的轮播信息
     * @return 响应结果
     */
    @PostMapping("update")
    public Result<Void> update(@RequestBody CarouselAdminVO carouselAdminVO) {
        carouselService.update(carouselAdminVO);
        return Result.success();
    }

    /**
     * 批量删除轮播图
     * @param ids id集合
     * @return 删除结果,会返回删除成功的集合
     */
    @PostMapping("/delete")
    public Result<List<Integer>> batchDelete(@RequestBody List<Integer> ids) {
        carouselService.batchDelete(ids);
        return Result.success();
    }

    /**
     * 分页信息
     * @param pageRange 需要分页的内容
     * @return 返回
     */
    @PostMapping("search")
    public Result<Page<Carousel>> page(@RequestBody PageRange pageRange) {
        return Result.success(carouselService.page(pageRange));
    }

}
