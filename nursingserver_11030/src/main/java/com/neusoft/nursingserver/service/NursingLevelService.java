package com.neusoft.nursingserver.service;

import com.neusoft.nursingserver.entity.NursingProgram;

import java.util.List;

public interface NursingLevelService {
    List<NursingProgram> listProgramsByLevelId(int levelId);


}
