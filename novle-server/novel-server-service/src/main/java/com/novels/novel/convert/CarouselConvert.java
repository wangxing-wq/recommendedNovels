package com.novels.novel.convert;

import com.novels.common.convert.BaseMapper;
import com.novels.novel.domain.model.Carousel;
import com.novels.novel.domain.vo.CarouselAdminVO;
import com.novels.novel.domain.vo.CarouselVO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author 王兴
 * @date 2023/6/9 0:12
 */
@Mapper(componentModel = "spring")
public interface CarouselConvert extends BaseMapper<Carousel, CarouselVO> {


    @Mapping(target = "createBy",constant = "1")
    @Mapping(target = "createByName",constant = "wx")
    @Mapping(target = "updateTime",expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "createTime",expression = "java(java.time.LocalDateTime.now())")
    Carousel fromCarouselAdminVO(CarouselAdminVO carouselAdminVO);


    @InheritConfiguration(name = "fromCarouselAdminVO")
    List<Carousel> fromListCarouseAdmin(List<CarouselAdminVO> carouselAdminVOList);

}
