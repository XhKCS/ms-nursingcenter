package com.neusoft.mealserver.mapper;

import com.neusoft.mealserver.config.MyBaseMapper;
import com.neusoft.mealserver.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FoodMapper extends MyBaseMapper<Food> {
    @Select("select * from food where type=#{type}")
    List<Food> listByType(String type);

    @Select("select * from food where name=#{name}")
    Food getByName(String name);
}
