package com.neusoft.nursingserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.nursingserver.entity.NursingLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NursingLevelMapper extends BaseMapper<NursingLevel> {
    @Select("select * from nursing_level where name=#{name}")
    NursingLevel getByName(String name);

    @Select("select * from nursing_level where status=#{status}")
    List<NursingLevel> listByStatus(int status);
}
