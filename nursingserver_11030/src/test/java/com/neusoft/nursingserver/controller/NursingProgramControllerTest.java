package com.neusoft.nursingserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.nursingserver.entity.NursingLevel;
import com.neusoft.nursingserver.entity.NursingProgram;
import com.neusoft.nursingserver.entity.PageResponseBean;
import com.neusoft.nursingserver.entity.ResponseBean;
import com.neusoft.nursingserver.mapper.NursingProgramMapper;
import com.neusoft.nursingserver.service.NursingProgramServiceImpl;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class NursingProgramControllerTest {
    @Autowired
    private NursingProgramMapper nursingProgramMapper;

    @Autowired
    private NursingProgramServiceImpl nursingProgramService;

    private MockMvc mockMvc;
    private MockHttpSession session;

    private NursingProgramController controller = new NursingProgramController();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setNursingProgramMapper(nursingProgramMapper);
        controller.setNursingProgramService(nursingProgramService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("NursingProgramController test begin---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("NursingProgramController test end---");
    }

    @Test
    void page() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：status为1（启用）
        Map<String, Object> request1 = new HashMap<>();
        request1.put("status", 1);
        request1.put("name", "");
        request1.put("current", 1);
        request1.put("size", 5);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/nursingProgram/page")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        PageResponseBean<NursingProgram> response1 = objectMapper.readValue(jsonResponse1, PageResponseBean.class);
        assertEquals(200, response1.getStatus());
        assertTrue(response1.getTotal() > 0);

        // 异常的测试用例：status为-1
        Map<String, Object> request2 = new HashMap<>();
        request2.put("status", -1);
        request2.put("name", "");
        request2.put("current", 1);
        request2.put("size", 5);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/nursingProgram/page")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        PageResponseBean<NursingProgram> response2 = objectMapper.readValue(jsonResponse2, PageResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(0, response2.getTotal());
    }

    @Test
    void update() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：将id为1的护理项目的价格修改为30
        NursingProgram nursingProgram1 = nursingProgramMapper.selectById(1);
        nursingProgram1.setPrice(30.0);
        String jsonRequest1 = objectMapper.writeValueAsString(nursingProgram1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/nursingProgram/update")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        ResponseBean<String> response1 = objectMapper.readValue(jsonResponse1, ResponseBean.class);
        assertEquals(200, response1.getStatus());

        // 异常的测试用例：尝试将id为2的护理项目的编号修改为与id为项目的编号相同
        NursingProgram nursingProgram2 = nursingProgramMapper.selectById(2);
        String oldCode = nursingProgram2.getProgramCode();
        nursingProgram2.setProgramCode(nursingProgram1.getProgramCode());
        String jsonRequest2 = objectMapper.writeValueAsString(nursingProgram2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/nursingProgram/update")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        ResponseBean<String> response2 = objectMapper.readValue(jsonResponse2, ResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(oldCode, nursingProgramMapper.selectById(2).getProgramCode());
    }
}