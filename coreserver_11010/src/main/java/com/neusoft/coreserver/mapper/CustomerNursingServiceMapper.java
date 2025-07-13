package com.neusoft.coreserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.coreserver.entity.CustomerNursingService;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerNursingServiceMapper extends BaseMapper<CustomerNursingService> {
    @Select("select * from customer_nursing_service where customer_id=#{customerId}")
    List<CustomerNursingService> listByCustomerId(int customerId);

    @Select("select * from customer_nursing_service where customer_id=#{customerId} and program_code=#{programCode}")
    CustomerNursingService getByCustomerIdAndProgramCode(@Param("customerId") int customerId, @Param("programCode") String programCode);

    @Delete("delete from customer_nursing_service where customer_id=#{customerId} and level_id=#{levelId}")
    int deleteByCustomerIdAndLevelId(@Param("customerId") int customerId, @Param("levelId") int levelId);
}
