package com.coreteam.languageservice.controller.language;

import com.coreteam.languageservice.dto.language.LanguageCreateDTO;
import com.coreteam.languageservice.dto.language.LanguageUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.language.LanguageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = {LanguageController.class})
@Slf4j
class LanguageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LanguageService languageService;

    @Test
    void successfullyCreate() throws Exception {
        LanguageCreateDTO languageCreateDTO = LanguageCreateDTO.builder().name("korean language").short_name("korean").build();
        when(languageService.create(languageCreateDTO)).thenReturn(new ResponseData<>(true, HttpStatus.CREATED));
        mockMvc.perform(post(LANGUAGE_URL + BASIC_CREATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(languageCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).create(languageCreateDTO);
    }

    @Test
    void failingCreate() throws Exception {
        LanguageCreateDTO languageCreateDTO = LanguageCreateDTO.builder().short_name("korean").build();
        when(languageService.create(languageCreateDTO)).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(post(LANGUAGE_URL + BASIC_CREATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(languageCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
        verify(languageService, times(0)).create(languageCreateDTO);
    }

    @Test
    void successfullyUpdate() throws Exception {
        LanguageUpdateDTO languageUpdateDTO = LanguageUpdateDTO.builder().language_id(1).name("korean language").short_name("korean").build();
        when(languageService.update(languageUpdateDTO.getLanguage_id(), languageUpdateDTO)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(put(LANGUAGE_URL + BASIC_UPDATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(languageUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).update(languageUpdateDTO.getLanguage_id(), languageUpdateDTO);
    }

    @Test
    void failingUpdate() throws Exception {
        LanguageUpdateDTO languageUpdateDTO = LanguageUpdateDTO.builder().language_id(1).name("korean language").build();
        when(languageService.update(languageUpdateDTO.getLanguage_id(), languageUpdateDTO)).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(put(LANGUAGE_URL + BASIC_UPDATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(languageUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
        verify(languageService, times(0)).update(languageUpdateDTO.getLanguage_id(), languageUpdateDTO);
    }

    @Test
    void successfullyDelete() throws Exception {
        when(languageService.delete(12)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(MockMvcRequestBuilders.delete(LANGUAGE_URL + BASIC_DELETE_BY_ID_URL, 12)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).delete(12);
    }

    @Test
    void failingDelete() throws Exception {
        when(languageService.delete(12)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(MockMvcRequestBuilders.delete(LANGUAGE_URL + BASIC_DELETE_BY_ID_URL, 122)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        verify(languageService, times(1)).delete(122);
    }

    @Test
    void successfullyGetByID() throws Exception {
        when(languageService.get(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(LANGUAGE_URL + BASIC_GET_BY_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).get(1);
    }

    @Test
    void successfullyGetAll() throws Exception {
        when(languageService.getAll()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(LANGUAGE_URL + BASIC_GET_ALL_URL)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).getAll();
    }

    @Test
    void getAllActive() throws Exception {
        when(languageService.getAllActive()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(LANGUAGE_URL + BASIC_GET_ALL_ACTIVE_URL)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).getAllActive();
    }

    @Test
    void getAllByStatusID() throws Exception {
        when(languageService.getAllByStatusID(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(LANGUAGE_URL + BASIC_GET_ALL_BY_STATUS_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(languageService, times(1)).getAllByStatusID(1);
    }
}