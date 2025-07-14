package com.neusoft.nursingserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.nursingserver.entity.*;
import com.neusoft.nursingserver.mapper.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerNursingServiceControllerTest {
    @Autowired
    private CustomerNursingServiceMapper customerNursingServiceMapper;
    @Autowired
    private NursingLevelMapper nursingLevelMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private LevelWithProgramMapper levelWithProgramMapper;
    @Autowired
    private NursingProgramMapper nursingProgramMapper;

    private MockMvc mockMvc;
    private MockHttpSession session;
    private CustomerNursingServiceController controller = new CustomerNursingServiceController();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setCustomerNursingServiceMapper(customerNursingServiceMapper);
        controller.setNursingLevelMapper(nursingLevelMapper);
        controller.setCustomerMapper(customerMapper);
        controller.setLevelWithProgramMapper(levelWithProgramMapper);
        controller.setNursingProgramMapper(nursingProgramMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("CustomerNursingServiceController test begin---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("CustomerNursingServiceController test end---");
    }


    @Test
    void pageAvailableProgramsByCustomer() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：customerId为 1
        Map<String, Object> request1 = new HashMap<>();
        request1.put("customerId", 1);
        request1.put("programName", "");
        request1.put("current", 1);
        request1.put("size", 5);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/customerNursingService/pageAvailableProgramsByCustomer")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        PageResponseBean<List<NursingProgram>> response1 = objectMapper.readValue(jsonResponse1, PageResponseBean.class);
        assertEquals(200, response1.getStatus());
        assertTrue(response1.getTotal() > 0);

        // 异常的测试用例：customerId为 -1
        Map<String, Object> request2 = new HashMap<>();
        request2.put("customerId", -1);
        request2.put("programName", "");
        request2.put("current", 1);
        request2.put("size", 5);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/customerNursingService/pageAvailableProgramsByCustomer")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        PageResponseBean<List<NursingProgram>> response2 = objectMapper.readValue(jsonResponse2, PageResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(0, response2.getTotal());
    }

    @Test
    void add() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：为id为1的客户添加id为2的护理项目服务
        Customer customer = customerMapper.selectById(1);
        NursingProgram nursingProgram = nursingProgramMapper.selectById(2);
        NursingLevel nursingLevel = nursingLevelMapper.getByName(customer.getNursingLevelName());
        CustomerNursingService customerNursingService1 = new CustomerNursingService(0, 1, nursingLevel.getId(), 2, nursingProgram.getProgramCode(),
                nursingProgram.getName(), nursingProgram.getPrice(), nursingProgram.getExecutionPeriod(), nursingProgram.getExecutionTimes(),
                "2025-07-12", 5, 0, "2025-10-12");
        String jsonRequest1 = objectMapper.writeValueAsString(customerNursingService1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/customerNursingService/add")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        ResponseBean<Integer> response1 = objectMapper.readValue(jsonResponse1, ResponseBean.class);
        assertEquals(200, response1.getStatus());

        // 异常的测试用例：尝试再次为id为1的客户添加id为2的护理项目服务（重复添加）
        MvcResult result2 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        ResponseBean<Integer> response2 = objectMapper.readValue(jsonResponse2, ResponseBean.class);
        assertEquals(500, response2.getStatus());
    }
}