package com.neusoft.nursingserver.service;

import com.neusoft.nursingserver.entity.NursingProgram;

public interface NursingProgramService {
    int updateProgram(NursingProgram updatedProgram);

    int deleteProgramById(int id);

    int getPurchaseByProgramIdAndDate(int programId, String startDate, String endDate);
}
