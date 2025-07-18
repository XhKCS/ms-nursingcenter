package com.neusoft.nursingserver.service;

import com.neusoft.nursingserver.entity.CustomerNursingService;
import com.neusoft.nursingserver.entity.NursingRecord;
import com.neusoft.nursingserver.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
@Service
public class NursingRecordServiceImpl implements NursingRecordService{

    @Autowired
    private NursingRecordMapper nursingRecordMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private NursingProgramMapper nursingProgramMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CustomerNursingServiceMapper customerNursingServiceMapper;

    @Transactional
    @Override
    public Integer add(Map<String, Object> request) {
        String customerName = (String) request.get("customerName");
        String programName = (String) request.get("programName");
        String programCode = (String) request.get("programCode");
        String nursingTime = (String) request.get("nursingTime");
        int executionCount = (int) request.get("executionCount");
        String description = (String) request.get("description");
        String nurseName = (String) request.get("nurseName");
        int customerId = (int) request.get("customerId");

        int programId = nursingProgramMapper.getByName(programName).getId();
        String nursePhone = userMapper.getByName(nurseName).getPhoneNumber();

        NursingRecord nursingRecord = new NursingRecord(0,customerId,customerName,programId,programCode,programName,description,nurseName,nursePhone,nursingTime,executionCount,false);
        Integer result = nursingRecordMapper.insert(nursingRecord);
//        System.out.println(customerId);
//        System.out.println(programCode);
        CustomerNursingService customerNursingService = customerNursingServiceMapper.getByCustomerIdAndProgramCode(customerId,programCode);
//        System.out.println(customerNursingService);
        customerNursingService.setUsedCount(customerNursingService.getUsedCount()+executionCount);

        Integer result2 = customerNursingServiceMapper.updateById(customerNursingService);
        if(result<=0){
            throw new RuntimeException("尝试添加护理记录时出错");
        }

        if(result2<=0){
            throw new RuntimeException("尝试更新用户护理项目已使用数量时出错");
        }

        return result;
    }
}
