package com.novels.novel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.novels.common.enums.SystemConstantEnum;
import com.novels.novel.convert.CarouselConvert;
import com.novels.novel.domain.vo.CarouselAdminVO;
import com.novels.novel.domain.vo.CarouselVO;
import com.novels.novel.domain.vo.PageRange;
import com.wx.common.exception.BizException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.novels.novel.domain.model.Carousel;
import com.novels.novel.dao.CarouselMapper;
import com.novels.novel.service.CarouselService;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * TODO 后续对增删改,增加权限管理
 * @author 王兴
 * @date 2023/5/28 21:29
 */
@Service
@SuppressWarnings("all")
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService{

    private final CarouselConvert carouselConvert;

    public CarouselServiceImpl(CarouselConvert carouselConvert) {
        this.carouselConvert = carouselConvert;
    }

    @Override
    public List<CarouselVO> currentExpire() {
        LambdaQueryWrapper<Carousel> query = Wrappers.lambdaQuery();
        // 大于当前时间,感觉后续优化可以存放缓存中
        query.le(Carousel::getStartTime,LocalDateTime.now()).ge(Carousel::getEndTime, LocalDateTime.now()).orderByDesc(Carousel::getSort);
        List<Carousel> list = list(query);
        return carouselConvert.toList(list);
    }

    @Override
    public CarouselVO carouselVO(Integer id) {
        if (Objects.isNull(id)) {
            id = 1;
        }
        Carousel byId = getById(id);
        if (Objects.isNull(byId)) {
            return new CarouselVO();
        }
        return carouselConvert.to(byId);
    }

    @Override
    public void batchInsert(List<CarouselAdminVO> carouselVOList) {
        List<Carousel> carousels = carouselConvert.fromListCarouseAdmin(carouselVOList);
        boolean b = this.saveBatch(carousels);
        if (!b) {
            throw new BizException(SystemConstantEnum.INSERT_EXCEPTION);
        }
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BizException(SystemConstantEnum.DELETE_EXCEPTION);
        }
        if (!this.removeBatchByIds(ids,1000)) {
            throw new BizException(SystemConstantEnum.DELETE_EXCEPTION);
        };
    }

    @Override
    public void update(CarouselAdminVO carouselAdminVO) {
        Carousel carousel = carouselConvert.fromCarouselAdminVO(carouselAdminVO);
        if (!updateById(carousel)) {
            throw new BizException(SystemConstantEnum.UPDATE_EXCEPTION);
        }
    }

    @Override
    public Page<Carousel> page(PageRange pageRange) {
//        LambdaQueryWrapper<Carousel> lambdaQueryWrapper = new QueryWrapper<Carousel>().lambda();
//        lambdaQueryWrapper.gt(Carousel::getCarouselId,5);
        Page<Carousel> page = this.page(new Page<>(pageRange.getPage(), pageRange.getCount()));
        return page;
    }

}
