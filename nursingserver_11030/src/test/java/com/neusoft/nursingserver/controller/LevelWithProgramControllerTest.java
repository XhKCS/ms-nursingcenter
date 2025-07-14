package com.neusoft.nursingserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neusoft.nursingserver.entity.LevelWithProgram;
import com.neusoft.nursingserver.entity.NursingProgram;
import com.neusoft.nursingserver.entity.PageResponseBean;
import com.neusoft.nursingserver.mapper.LevelWithProgramMapper;
import com.neusoft.nursingserver.service.LevelWithProgramService;
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
class LevelWithProgramControllerTest {
    @Autowired
    private LevelWithProgramMapper levelWithProgramMapper;

    @Autowired
    private LevelWithProgramService levelWithProgramService;

    private MockMvc mockMvc;
    private MockHttpSession session;

    private LevelWithProgramController controller = new LevelWithProgramController();

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        controller.setLevelWithProgramMapper(levelWithProgramMapper);
        controller.setLevelWithProgramService(levelWithProgramService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        System.out.println("LevelWithProgramController test begin---");
    }

    @AfterEach
    void tearDown() {
        System.out.println("LevelWithProgramController test end---");
    }

    @Test
    void pageProgramsByLevelId() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 正常的测试用例：levelId为 1
        Map<String, Object> request1 = new HashMap<>();
        request1.put("levelId", 1);
        request1.put("programName", "");
        request1.put("current", 1);
        request1.put("size", 5);
        String jsonRequest1 = objectMapper.writeValueAsString(request1);
        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .post("/levelWithProgram/pageProgramsByLevelId")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest1);
        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
        String jsonResponse1 = result1.getResponse().getContentAsString();
        System.out.println("jsonResponse1（正常测试用例返回结果）: "+jsonResponse1);
        PageResponseBean<LevelWithProgram> response1 = objectMapper.readValue(jsonResponse1, PageResponseBean.class);
        assertEquals(200, response1.getStatus());
        assertTrue(response1.getTotal() > 0);

        // 异常的测试用例：levelId为 -1
        Map<String, Object> request2 = new HashMap<>();
        request2.put("levelId", -1);
        request2.put("programName", "");
        request2.put("current", 1);
        request2.put("size", 5);
        String jsonRequest2 = objectMapper.writeValueAsString(request2);
        RequestBuilder requestBuilder2 = MockMvcRequestBuilders
                .post("/levelWithProgram/pageProgramsByLevelId")
                .contentType(MediaType.APPLICATION_JSON) //注意post不是写accept,而是写contentType
                .characterEncoding(StandardCharsets.UTF_8.name())
                .content(jsonRequest2);
        MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
        String jsonResponse2 = result2.getResponse().getContentAsString();
        System.out.println("jsonResponse2（异常测试用例返回结果）: "+jsonResponse2);
        PageResponseBean<LevelWithProgram> response2 = objectMapper.readValue(jsonResponse2, PageResponseBean.class);
        assertEquals(500, response2.getStatus());
        assertEquals(0, response2.getTotal());
    }

    @Test
    void deleteAllByProgramId() {
    }
}