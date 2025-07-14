package com.neusoft.nursingserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.nursingserver.entity.NursingLevel;
import com.neusoft.nursingserver.entity.PageResponseBean;
import com.neusoft.nursingserver.entity.ResponseBean;
import com.neusoft.nursingserver.feignclient.ToolFeignClient;
import com.neusoft.nursingserver.mapper.NursingLevelMapper;
import com.neusoft.nursingserver.service.NursingLevelService;
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
class NursingLevelControllerTest {
    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    @Autowired
    private NursingLevelService nursingLevelService;

    @Autowired
    private ToolFeignClient toolFeignClient;

    private MockMvc mockMvc;
    private MockHttpSession session;

    private NursingLevelController controller = new NursingLevelController();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setNursingLevelMapper(nursingLevelMapper);
        controller.setNursingLevelService(nursingLevelService);
        controller.setToolFeignClient(toolFeignClient);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("NursingLevelController test begin---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("NursingLevelController test end---");
    }

    @Test
    void pageByStatus() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：status为1（启用）
        Map<String, Object> request1 = new HashMap<>();
        request1.put("status", 1);
        request1.put("current", 1);
        request1.put("size", 5);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/nursingLevel/pageByStatus")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        PageResponseBean<NursingLevel> response1 = objectMapper.readValue(jsonResponse1, PageResponseBean.class);
        assertEquals(200, response1.getStatus());
        assertTrue(response1.getTotal() > 0);

        // 异常的测试用例：status为-1
        Map<String, Object> request2 = new HashMap<>();
        request2.put("status", -1);
        request2.put("current", 1);
        request2.put("size", 5);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/nursingLevel/pageByStatus")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        PageResponseBean<NursingLevel> response2 = objectMapper.readValue(jsonResponse2, PageResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(0, response2.getTotal());
    }

    @Test
    void add() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：name="八级", status=1
        Map<String, Object> request1 = new HashMap<>();
        request1.put("name", "八级");
        request1.put("status", 1);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/nursingLevel/add")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        ResponseBean<String> response1 = objectMapper.readValue(jsonResponse1, ResponseBean.class);
        assertEquals(200, response1.getStatus());

        // 异常的测试用例：尝试再次添加重名的护理级别：name="八级", status=0
        Map<String, Object> request2 = new HashMap<>();
        request2.put("name", "八级");
        request2.put("status", 0);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/nursingLevel/add")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        ResponseBean<String> response2 = objectMapper.readValue(jsonResponse2, ResponseBean.class);
        assertEquals(500, response2.getStatus());
    }
}