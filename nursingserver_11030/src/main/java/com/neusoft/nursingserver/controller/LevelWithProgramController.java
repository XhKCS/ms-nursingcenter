package com.neusoft.nursingserver.controller;

import com.neusoft.nursingserver.entity.*;
import com.neusoft.nursingserver.mapper.LevelWithProgramMapper;
import com.neusoft.nursingserver.service.LevelWithProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin("*")
@RestController
@RequestMapping("/levelWithProgram")
public class LevelWithProgramController {
    @Autowired
    private LevelWithProgramMapper levelWithProgramMapper;

    @Autowired
    private LevelWithProgramService levelWithProgramService;

    @PostMapping("/pageProgramsByLevelId")
    public PageResponseBean<List<NursingProgram>> pageProgramsByLevelId(@RequestBody Map<String, Object> request){
        int levelId = (int) request.get("levelId");
        String programName = (String) request.get("programName");
        int current = (int) request.get("current");
        int size = (int) request.get("size");
        return levelWithProgramService.pageProgramsByLevelId(levelId, programName, current, size);
    }

    @PostMapping("/add")
    public ResponseBean<String> add(@RequestBody Map<String, Object> request) {
        int levelId = (int) request.get("levelId");
        int programId = (int) request.get("programId");
        LevelWithProgram levelWithProgram = new LevelWithProgram(0, levelId, programId);
        int result = levelWithProgramMapper.insert(levelWithProgram);

        ResponseBean<String> rb = null;
        if (result > 0) {
            rb = new ResponseBean<>("添加成功");
        } else {
            rb = new ResponseBean<>(500, "添加失败");
        }
        return rb;
    }

//    @PostMapping("/add")
//    public ResponseBean<Integer> add(@RequestBody LevelWithProgram levelWithProgram) {
//        Integer result = levelWithProgramMapper.insert(levelWithProgram);
//        ResponseBean<Integer> rb = null;
//        if(result > 0) {
//            rb = new ResponseBean<>(result);
//        }else {
//            rb = new ResponseBean<>(500,"Fail to add");
//        }
//        return rb;
//    }

    @PostMapping("/delete")
    public ResponseBean<Integer> delete(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        int result = levelWithProgramMapper.deleteById(id);
        ResponseBean<Integer> rb =null;
        if(result > 0) {
            rb = new ResponseBean<>(result);
        }else {
            rb = new ResponseBean<>(500,"Fail to delete");
        }

        return rb;
    }

    @PostMapping("/deleteByLevelAndProgram")
    public ResponseBean<Integer> deleteByLevelAndProgram(@RequestBody Map<String, Object> request) {
        int levelId = (int) request.get("levelId");
        int programId = (int) request.get("programId");
        LevelWithProgram levelWithProgram = levelWithProgramMapper.getByLevelAndProgram(levelId, programId);
        int result = levelWithProgramMapper.deleteById(levelWithProgram.getId());
        ResponseBean<Integer> rb =null;
        if(result > 0) {
            rb = new ResponseBean<>(result);
        }else {
            rb = new ResponseBean<>(500,"Fail to delete");
        }

        return rb;
    }
}
