package com.coreteam.languageservice.controller.foreign_lang;

import com.coreteam.languageservice.dto.foreign_lang.ForeignLangCreateDTO;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangUpdateDTO;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.foreign_lang.ForeignLangService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.coreteam.languageservice.controller.BaseURL.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ForeignLangController.class})
class ForeignLangControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ForeignLangService foreignLangService;

    @Test
    void successfullyCreate() throws Exception {
        ForeignLangCreateDTO foreignLangCreateDTO = new ForeignLangCreateDTO("const", 1, "uzbek", "bu const", "const ni description", 1);
        Mockito.when(foreignLangService.create(foreignLangCreateDTO))
                .thenReturn(new ResponseData<>(true, HttpStatus.CREATED));
        mockMvc.perform(MockMvcRequestBuilders.post(FOREIGN_LANG_URL + BASIC_CREATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(foreignLangCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
        verify(foreignLangService, Mockito.times(1)).create(foreignLangCreateDTO);
    }

    @Test
    void successfullyUpdate() throws Exception {
        ForeignLangUpdateDTO dto = new ForeignLangUpdateDTO(1, 1, "t_const", "uzbek", "t_text", "t_description", 1);
        Mockito.when(foreignLangService.update(dto.getForeign_language_id(), dto))
                .thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(put(FOREIGN_LANG_URL + BASIC_UPDATE_URL)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted());
        verify(foreignLangService, times(0)).update(dto.getForeign_language_id(), dto);
    }

    @Test
    void successfullyDelete() throws Exception {
        when(foreignLangService.delete(1)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(delete(FOREIGN_LANG_URL + BASIC_DELETE_BY_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).delete(1);
    }

    @Test
    void successfullyGetByID() throws Exception {
        when(foreignLangService.get(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_BY_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).get(1);
    }

    @Test
    void successfullyGetByTConst() throws Exception {
        when(foreignLangService.getByTConst("t_const")).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_BY_T_CONST_URL, "t_const")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).getByTConst("t_const");
    }

    @Test
    void successfullyGetAll() throws Exception {
        when(foreignLangService.getAll()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_ALL_URL)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).getAll();
    }

    @Test
    void successfullyGetAllActive() throws Exception {
        when(foreignLangService.getAllActive()).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_ALL_ACTIVE_URL)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).getAllActive();
    }

    @Test
    void successfullyGetAllByStatusID() throws Exception {
        when(foreignLangService.getAllByStatusID(1)).thenReturn(new ResponseData<>(true, HttpStatus.OK));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_ALL_BY_STATUS_ID_URL, 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).getAllByStatusID(1);
    }

    @Test
    void successfullyGetAllByModulID() throws Exception {
        when(foreignLangService.getAllByModulID(1)).thenReturn(new ResponseData<>(true, HttpStatus.ACCEPTED));
        mockMvc.perform(get(FOREIGN_LANG_URL + BASIC_GET_ALL_BY_MODULE_URL, 1)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
        verify(foreignLangService, times(1)).getAllByModulID(1);
    }
}