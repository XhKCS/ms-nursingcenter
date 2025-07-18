package com.neusoft.nursingserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neusoft.nursingserver.entity.NursingProgram;
import com.neusoft.nursingserver.entity.PageResponseBean;
import com.neusoft.nursingserver.entity.ResponseBean;
import com.neusoft.nursingserver.mapper.NursingProgramMapper;
import com.neusoft.nursingserver.service.NursingProgramService;
import com.neusoft.nursingserver.service.NursingProgramServiceImpl;
import com.neusoft.nursingserver.util.NursingProgramStructOutputUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin("*")
@RestController
@RequestMapping("/nursingProgram")
public class NursingProgramController {
    @Autowired
    private NursingProgramMapper nursingProgramMapper;

    @Autowired
    private NursingProgramService nursingProgramService;

    @Resource
    private NursingProgramStructOutputUtil nursingProgramStructOutputUtil;

    public void setNursingProgramMapper(NursingProgramMapper nursingProgramMapper) {
        this.nursingProgramMapper = nursingProgramMapper;
    }

    public void setNursingProgramService(NursingProgramServiceImpl nursingProgramService) {
        this.nursingProgramService = nursingProgramService;
    }

    @PostMapping("/getPurchaseByProgramIdAndDate")
    public ResponseBean<Integer> getPurchaseByIdAndTime(@RequestBody Map<String, Object> request) {
        int programId = (int) request.get("programId");
        String startDate = (String) request.get("startDate");
        String endDate = (String) request.get("endDate");

        int purchaseCount = nursingProgramService.getPurchaseByProgramIdAndDate(programId,startDate,endDate);

        ResponseBean<Integer> rb = null;

        if(purchaseCount >= 0) {
            rb = new ResponseBean<>(purchaseCount);
        }else {
            rb = new ResponseBean<>(500,"Fail to get");
        }
        return rb;
    }

    @PostMapping("/pageAll")
    public PageResponseBean<List<NursingProgram>> pageAll(@RequestBody Map<String, Object> request) {
        int current = (int) request.get("current"); //当前页面
        int size = (int) request.get("size"); //一页的行数

        IPage<NursingProgram> page = new Page<>(current, size);
        QueryWrapper<NursingProgram> qw = new QueryWrapper<>();
        qw.eq("is_deleted", 0);
        IPage<NursingProgram> result = nursingProgramMapper.selectPage(page, qw);
        List<NursingProgram> list = result.getRecords();
        long total = result.getTotal();
        PageResponseBean<List<NursingProgram>> prb = null;

        if (total > 0) {
            prb = new PageResponseBean<>(list);
            prb.setTotal(total);
        } else {
            prb = new PageResponseBean<>(500, "No data");
        }
        return prb;
    }

    @PostMapping("/listAll")
    public ResponseBean<List<NursingProgram>> listAll() {
        QueryWrapper<NursingProgram> qw = new QueryWrapper<>();
        qw.eq("is_deleted", 0);
        List<NursingProgram> programList = nursingProgramMapper.selectList(qw);

        ResponseBean<List<NursingProgram>> rb = null;
        if (programList.size() > 0) {
            rb = new ResponseBean<>(programList);
        } else {
            rb = new ResponseBean<>(500, "No data");
        }
        return rb;
    }

    // 多条件组合分页查询
    // 组合条件：项目名称、状态
    @PostMapping("/page")
    public PageResponseBean<List<NursingProgram>> page(@RequestBody Map<String, Object> request) {
        int status = (int) request.get("status");
        String name = (String) request.get("name");
        int current = (int) request.get("current"); //当前页面
        int size = (int) request.get("size"); //一页的行数

        IPage<NursingProgram> page = new Page<>(current, size);
        QueryWrapper<NursingProgram> qw = new QueryWrapper<>();
        qw.eq("is_deleted", 0);
        qw.eq("status", status);
        qw.like("name", name);
        IPage<NursingProgram> result = nursingProgramMapper.selectPage(page, qw);
        List<NursingProgram> list = result.getRecords();
        long total = result.getTotal();
        PageResponseBean<List<NursingProgram>> prb = null;

        if (total > 0) {
            prb = new PageResponseBean<>(list);
            prb.setTotal(total);
        } else {
            prb = new PageResponseBean<>(500, "No data");
        }
        return prb;
    }

    @PostMapping("/listByStatus")
    public ResponseBean<List<NursingProgram>> listByStatus(@RequestBody Map<String, Object> request) {
        int status = (int) request.get("status");
        List<NursingProgram> programList = nursingProgramMapper.listByStatus(status);

        ResponseBean<List<NursingProgram>> rb = null;
        if (programList.size() > 0) {
            rb = new ResponseBean<>(programList);
        } else {
            rb = new ResponseBean<>(500, "No data");
        }
        return rb;
    }

    @PostMapping("/getById")
    public ResponseBean<NursingProgram> getById(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        NursingProgram nursingProgram = nursingProgramMapper.selectById(id);

        ResponseBean<NursingProgram> rb = null;
        if (nursingProgram != null) {
            rb = new ResponseBean<>(nursingProgram);
        } else {
            rb = new ResponseBean<>(500, "不存在该id的护理项目");
        }
        return rb;
    }

    @PostMapping("/getByProgramCode")
    public ResponseBean<NursingProgram> getByProgramCode(@RequestBody Map<String, Object> request) {
        String programCode = (String) request.get("programCode");
        NursingProgram nursingProgram = nursingProgramMapper.getByProgramCode(programCode);

        ResponseBean<NursingProgram> rb = null;
        if (nursingProgram != null) {
            rb = new ResponseBean<>(nursingProgram);
        } else {
            rb = new ResponseBean<>(500, "不存在该编号的护理项目");
        }
        return rb;
    }

    @PostMapping("/getByName")
    public ResponseBean<NursingProgram> getByName(@RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        NursingProgram nursingProgram = nursingProgramMapper.getByName(name);

        ResponseBean<NursingProgram> rb = null;
        if (nursingProgram != null) {
            rb = new ResponseBean<>(nursingProgram);
        } else {
            rb = new ResponseBean<>(500, "不存在该名称的护理项目");
        }
        return rb;
    }

    @PostMapping("/add")
    public ResponseBean<String> add(@RequestBody NursingProgram nursingProgram) {
        NursingProgram check = nursingProgramMapper.getByProgramCode(nursingProgram.getProgramCode());
        if (check != null) {
            return new ResponseBean<>(500, "护理项目编号不能重复！");
        }
        check = nursingProgramMapper.getByName(nursingProgram.getName());
        if (check != null) {
            return new ResponseBean<>(500, "护理项目名称不能重复！");
        }
        nursingProgram.setIsDeleted(false);
        int result = nursingProgramMapper.insert(nursingProgram);
        ResponseBean<String> rb = null;

        if (result > 0) {
            rb = new ResponseBean<>("添加成功");
        } else {
            rb = new ResponseBean<>(500, "添加失败");
        }
        return rb;
    }

    // 事务
    @PostMapping("/update")
    public ResponseBean<String> update(@RequestBody NursingProgram updatedProgram) {
        NursingProgram check = nursingProgramMapper.getByProgramCode(updatedProgram.getProgramCode());
        if (check != null && check.getId() != updatedProgram.getId()) {
            return new ResponseBean<>(500, "护理项目编号不能重复！");
        }
        check = nursingProgramMapper.getByName(updatedProgram.getName());
        if (check != null && check.getId() != updatedProgram.getId()) {
            return new ResponseBean<>(500, "护理项目名称不能重复！");
        }
        updatedProgram.setIsDeleted(false); //修改不能够让项目被删除
        ResponseBean<String> rb = null;
        try {
            int result = nursingProgramService.updateProgram(updatedProgram);
            if (result > 0) {
                rb = new ResponseBean<>("修改成功");
            } else {
                rb = new ResponseBean<>(500, "修改失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rb = new ResponseBean<>(500, e.getMessage());
        }
        return rb;
    }

    // 事务
    @PostMapping("/delete")
    public ResponseBean<String> delete(@RequestBody Map<String, Object> request) {
        int id = (int) request.get("id");
        ResponseBean<String> rb = null;
        try {
            int result = nursingProgramService.deleteProgramById(id);
            if (result > 0) {
                rb = new ResponseBean<>("删除成功");
            } else {
                rb = new ResponseBean<>(500, "删除失败");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rb = new ResponseBean<>(500, e.getMessage());
        }
        return rb;
    }

    @PostMapping("/deleteBatch")
    public ResponseBean<String> deleteBatch(@RequestBody List<NursingProgram> programList) {
        ResponseBean<String> rb = null;
        try {
            for (NursingProgram program : programList) {
                int result = nursingProgramService.deleteProgramById(program.getId());
                if (result <= 0) {
                    return new ResponseBean<>(500, "删除过程中失败");
                }
            }
            rb = new ResponseBean<>("删除成功，共删除"+programList.size()+"条数据");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rb = new ResponseBean<>(500, e.getMessage());
        }
        return rb;
    }

    @PostMapping("/aiObj")
    public ResponseBean<NursingProgram> aiCreateObj(@RequestBody Map<String, Object> request){
        String query = (String)request.get("query");
        ResponseBean<NursingProgram> rb =null;
        try {
            NursingProgram nursingProgram = nursingProgramStructOutputUtil.chatObj(query);
            if(nursingProgram != null){
                System.out.println(nursingProgram);
                rb = new ResponseBean<>(nursingProgram);
            }else {
                rb = new ResponseBean<>(500,"No data");
            }
        } catch (Exception e) {
            System.out.println("Exception happened: ");
            e.printStackTrace();
            rb = new ResponseBean<>(500, e.getMessage());
        }
        return rb;
    }
}
