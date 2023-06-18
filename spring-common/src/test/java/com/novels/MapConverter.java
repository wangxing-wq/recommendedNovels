package com.novels;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapConverter {
    MapConverter INSTANCE = Mappers.getMapper(MapConverter.class);

    String toVO(Integer age);

    public static void main(String[] args) {
        String age = INSTANCE.toVO(11);
        System.out.println(age);
    }
}
