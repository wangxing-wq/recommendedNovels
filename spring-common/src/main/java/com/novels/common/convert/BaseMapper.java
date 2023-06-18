package com.novels.common.convert;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * 通用转换Mapper接口
 * @author 22343
 */
@MapperConfig
public interface BaseMapper<SOURCE, TARGET> {

    /**
     * 映射同名属性
     */
    TARGET to(SOURCE source);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "to")
    SOURCE from(TARGET target);

    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "to")
    List<TARGET> toList(List<SOURCE> sourceList);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "from")
    List<SOURCE> fromList(List<TARGET> targetList);

    /**
     * 映射同名属性，集合流形式
     */
    List<TARGET> toStream(Stream<SOURCE> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<SOURCE> fromStream(Stream<TARGET> stream);
}
