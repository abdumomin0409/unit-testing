package com.coreteam.languageservice.controller.status;

import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.status.StatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.coreteam.languageservice.controller.BaseURL.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {StatusController.class})
class StatusControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StatusService statusService;

    @Test
    void successfullyGetByID() throws Exception {
        when(statusService.get(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(MockMvcRequestBuilders.get(STATUS_URL + BASIC_GET_BY_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(statusService, times(1)).get(1);
    }

    @Test
    void successfullyGetAll() throws Exception {
        when(statusService.getAll()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(MockMvcRequestBuilders.get(STATUS_URL + BASIC_GET_ALL_URL)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(statusService, times(1)).getAll();
    }
}