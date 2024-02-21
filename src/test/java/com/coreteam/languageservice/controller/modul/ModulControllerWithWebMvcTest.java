package com.coreteam.languageservice.controller.modul;

import com.coreteam.languageservice.dto.modul.ModulCreateDTO;
import com.coreteam.languageservice.dto.modul.ModulUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.modul.ModulService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.coreteam.languageservice.controller.BaseURL.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = ModulController.class)
@Slf4j
class ModulControllerWithWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ModulService modulService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void successfullyCreate() throws Exception {
        ModulCreateDTO modulCreateDTO = new ModulCreateDTO("modul");
        when(modulService.create(modulCreateDTO)).thenReturn(new ResponseData<>(true, HttpStatus.CREATED));
        mockMvc.perform(
                        post(MODUL_URL + BASIC_CREATE_URL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(modulCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
        verify(modulService, atLeast(1)).create(modulCreateDTO);
    }

    @Test
    void failingCreate() throws Exception {
        ModulCreateDTO modulCreateDTO = new ModulCreateDTO();
        when(modulService.create(modulCreateDTO)).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(
                        post(MODUL_URL + BASIC_CREATE_URL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(modulCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
        verify(modulService, times(0)).create(any(ModulCreateDTO.class));
    }

    @Test
    void successfullyUpdate() throws Exception {
        ModulUpdateDTO modulUpdateDTO = ModulUpdateDTO.builder().modul_id(1).name("modul").build();
        when(modulService.update(1, modulUpdateDTO)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(put(MODUL_URL + BASIC_UPDATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(modulUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
        verify(modulService, times(1)).update(1, modulUpdateDTO);
    }

    @Test
    void failingUpdate() throws Exception {
        ModulUpdateDTO modulUpdateDTO = ModulUpdateDTO.builder().modul_id(1).build();
        when(modulService.update(1, modulUpdateDTO)).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(put(MODUL_URL + BASIC_UPDATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(modulUpdateDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        verify(modulService, times(0)).update(1, modulUpdateDTO);
    }

    @Test
    void successfullyGetByID() throws Exception {
        when(modulService.get(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_BY_ID_URL, 1).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.success").value(true));
        verify(modulService, times(1)).get(1);
    }

    @Test
    void failingGetByID() throws Exception {
        when(modulService.get(10)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_BY_ID_URL, 12e3).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(400));
        verify(modulService, times(0)).get(100);
    }

    @Test
    void successfullyDelete() throws Exception {
        when(modulService.delete(11)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(delete(MODUL_URL + BASIC_DELETE_BY_ID_URL, 11).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(jsonPath("$.success").value(true));
        verify(modulService, times(1)).delete(11);
    }

    @Test
    void failingDelete() throws Exception {
        when(modulService.delete(12)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(delete(MODUL_URL + BASIC_DELETE_BY_ID_URL, 123).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(202));
        verify(modulService, times(1)).delete(123);
    }

    @Test
    void successfullyGetAll() throws Exception {
        when(modulService.getAll()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_URL).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(modulService, times(1)).getAll();
    }

    @Test
    void failingGetAll() throws Exception {
        when(modulService.getAll()).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_URL).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.success").value(false));
        verify(modulService, times(1)).getAll();
    }


    @Test
    void successfullyGetAllActive() throws Exception {
        when(modulService.getAllActive()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_ACTIVE_URL).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(modulService, times(1)).getAllActive();
    }

    @Test
    void failingGetAllActive() throws Exception {
        when(modulService.getAllActive()).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_ACTIVE_URL).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.success").value(false));
        verify(modulService, times(1)).getAllActive();
    }

    @Test
    void successfullyGetAllByStatusId() throws Exception {
        when(modulService.getAllByStatusID(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_BY_STATUS_ID_URL, 1).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.success").value(true));
        verify(modulService, times(1)).getAllByStatusID(1);
    }

    @Test
    void failingGetAllByStatusId() throws Exception {
        when(modulService.getAllByStatusID(1)).thenReturn(new ResponseData<>(false, HttpStatus.BAD_REQUEST));
        mockMvc.perform(get(MODUL_URL + BASIC_GET_ALL_BY_STATUS_ID_URL , 6).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(modulService, times(1)).getAllByStatusID(6);
    }

}