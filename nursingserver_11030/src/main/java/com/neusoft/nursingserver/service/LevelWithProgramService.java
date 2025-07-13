package com.neusoft.nursingserver.service;

import com.neusoft.nursingserver.entity.NursingProgram;
import com.neusoft.nursingserver.entity.PageResponseBean;

import java.util.List;

public interface LevelWithProgramService {
    PageResponseBean<List<NursingProgram>> pageProgramsByLevelId(int levelId, String programName, int current, int size);
}
