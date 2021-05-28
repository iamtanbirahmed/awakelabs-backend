package com.awakelabs.writeservice;

import com.awakelabs.writeservice.controller.VitalSignWriteController;
import com.awakelabs.writeservice.dto.VitalSignDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class VitalSignWriteControllerIntegrationTest {

    @Value("classpath:data.json")
    private Resource resourceFile;

    @Autowired
    VitalSignWriteController vitalSignWriteController;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

//    @Before()
//    public void setup()
//    {
//        //Init MockMvc Object and build
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    public void writeVitalSignTest() throws Exception {

        String uri = "https://localhost:7003/v0.1/write/";

        ObjectMapper objectMapper = new ObjectMapper();
        File file = resourceFile.getFile();
        VitalSignDTO[] vitalSignDTOS = objectMapper.readValue(file, VitalSignDTO[].class);
        System.out.println(file.getAbsolutePath());
        System.out.println(vitalSignDTOS[0]);


        String body = objectMapper.writeValueAsString(vitalSignDTOS[0]);

        MvcResult mvcResult = (MvcResult) this.mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(body)
                .secure(true)
                .contentType(MediaType.APPLICATION_JSON));
//        String inputJson = super.mapToJson(vitalSignDTOS.get(0));

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);


    }
}
