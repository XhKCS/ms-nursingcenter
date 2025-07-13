package com.neusoft.mealserver.service;

import com.neusoft.mealserver.entity.Food;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FoodService {
    int updateFood(Food food);

    int deleteFoodById(int foodId);

    @Transactional
    int deleteFoodByIds(List<Integer> ids);

    int getPurchaseByIdAndTime(int foodId,String startTime,String endTime);
}
