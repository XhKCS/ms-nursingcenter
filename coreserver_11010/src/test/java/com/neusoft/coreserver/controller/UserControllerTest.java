package com.neusoft.coreserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.coreserver.entity.Customer;
import com.neusoft.coreserver.entity.PageResponseBean;
import com.neusoft.coreserver.entity.UserView;
import com.neusoft.coreserver.mapper.UserMapper;
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
class UserControllerTest {
    @Autowired
    private UserMapper userMapper;

    private MockMvc mockMvc;
    private MockHttpSession session;

    private UserController controller = new UserController();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setUserMapper(userMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("UserController test begin---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("UserController test end---");
    }

    @Test
    void page() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：userId为1（护工类型）
        Map<String, Object> request1 = new HashMap<>();
        request1.put("userType", 1);
        request1.put("name", "");
        request1.put("current", 1);
        request1.put("size", 5);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/user/page")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        PageResponseBean<List<UserView>> response1 = objectMapper.readValue(jsonResponse1, PageResponseBean.class);
        assertEquals(200, response1.getStatus());

        // 异常的测试用例：userType为-1
        Map<String, Object> request2 = new HashMap<>();
        request2.put("userType", -1);
        request2.put("name", "");
        request2.put("current", 1);
        request2.put("size", 5);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/user/page")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        PageResponseBean<Customer> response2 = objectMapper.readValue(jsonResponse2, PageResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(0, response2.getTotal());
    }
}