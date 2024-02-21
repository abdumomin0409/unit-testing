package com.coreteam.languageservice.service.foreign_lang;

import com.coreteam.languageservice.dto.foreign_lang.ForeignLangCreateDTO;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangUpdateDTO;
import com.coreteam.languageservice.model.foreign_lang.ForeignLangDB;
import com.coreteam.languageservice.repository.foreign_lang.ForeignLangRepository;
import com.coreteam.languageservice.repository.language.LanguageRepository;
import com.coreteam.languageservice.repository.status.StatusRepository;
import com.coreteam.languageservice.responce.ResponseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ForeignLangServiceTest {
    ForeignLangService foreignLangService;
    @Mock
    ForeignLangRepository foreignLangRepository;
    @Mock
    StatusRepository statusRepository;
    @Mock
    LanguageRepository languageRepository;

    @BeforeEach
    void setUp() {
        foreignLangService = new ForeignLangService(foreignLangRepository, statusRepository, languageRepository);
    }

    @Test
    void successfullyCreate() {
        ForeignLangCreateDTO createDTO = ForeignLangCreateDTO.builder().created_user_id(1).description("description").build();
        Mockito.when(foreignLangRepository.create(createDTO)).thenReturn(1);
        ResponseData<?> actual = foreignLangService.create(createDTO);
        assertEquals(1, actual.getData());
        Mockito.verify(foreignLangRepository, Mockito.times(1)).create(createDTO);
    }

    @Test
    void successfullyUpdate() {
        ForeignLangUpdateDTO updateDTO = ForeignLangUpdateDTO.builder().foreign_language_id(1).created_user_id(1).description("description").build();
        Mockito.when(foreignLangRepository.update(updateDTO)).thenReturn(true);
        ResponseData<?> actual = foreignLangService.update(updateDTO.getForeign_language_id(), updateDTO);
        assertEquals(true, actual.getData());
        Mockito.verify(foreignLangRepository, Mockito.times(1)).update(updateDTO);
    }

    @Test
    void successfullyGet() {
        Mockito.when(foreignLangRepository.get(1)).thenReturn(ForeignLangDB.builder().lang("uz").modulID(1).build());
        ResponseData<?> actual = foreignLangService.get(1);
        ForeignLangDB data = (ForeignLangDB) actual.getData();
        assertEquals(1, data.getModulID());
        assertEquals("uz", data.getLang());
        Mockito.verify(foreignLangRepository, Mockito.times(1)).get(1);
    }

    @Test
    void successfullyGetAll() {
        Mockito.when(foreignLangRepository.getAll()).thenReturn(List.of(ForeignLangDB.builder().id(1).createdUserId(1).build()));
        ResponseData<?> actual = foreignLangService.getAll();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ForeignLangDB modulDB = (ForeignLangDB) list;
            assertEquals(1, modulDB.getId());
            assertEquals(1, modulDB.getCreatedUserId());
        }
        Mockito.verify(foreignLangRepository, Mockito.times(1)).getAll();
    }

    @Test
    void successfullyGetAllActive() {
        Mockito.when(foreignLangRepository.getAllActive()).thenReturn(List.of(ForeignLangDB.builder().id(1).createdUserId(1).build()));
        ResponseData<?> actual = foreignLangService.getAllActive();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ForeignLangDB modulDB = (ForeignLangDB) list;
            assertEquals(1, modulDB.getId());
            assertEquals(1, modulDB.getCreatedUserId());
        }
        Mockito.verify(foreignLangRepository, Mockito.times(1)).getAllActive();
    }

    @Test
    void successfullyDelete() {
        Mockito.when(foreignLangRepository.delete(1)).thenReturn(false);
        ResponseData<?> actual = foreignLangService.delete(1);
        assertEquals(false, actual.getData());
        Mockito.verify(foreignLangRepository, Mockito.times(1)).delete(1);
    }

    @Test
    void successfullyGetAllByStatusID() {
        Mockito.when(foreignLangRepository.getAllByStatus(1)).thenReturn(List.of(ForeignLangDB.builder().id(1).createdUserId(1).build()));
        ResponseData<?> actual = foreignLangService.getAllByStatusID(1);
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ForeignLangDB modulDB = (ForeignLangDB) list;
            assertEquals(1, modulDB.getId());
            assertEquals(1, modulDB.getCreatedUserId());
        }
        Mockito.verify(foreignLangRepository, Mockito.times(1)).getAllByStatus(1);
    }

    @Test
    void successfullyGetAllByModulID() {
        Mockito.when(foreignLangRepository.getAllByModul(1)).thenReturn(List.of(ForeignLangDB.builder().id(1).createdUserId(1).build()));
        ResponseData<?> actual = foreignLangService.getAllByModulID(1);
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ForeignLangDB modulDB = (ForeignLangDB) list;
            assertEquals(1, modulDB.getId());
            assertEquals(1, modulDB.getCreatedUserId());
        }
        Mockito.verify(foreignLangRepository, Mockito.times(1)).getAllByModul(1);
    }

    @Test
    void successfullyGetByTConst() {
        Mockito.when(foreignLangRepository.getByTConst("uz")).thenReturn(ForeignLangDB.builder().lang("uz").tConst("uz").modulID(1).build());
        ResponseData<?> actual = foreignLangService.getByTConst("uz");
        ForeignLangDB data = (ForeignLangDB) actual.getData();
        assertEquals(1, data.getModulID());
        assertEquals("uz", data.getLang());
        assertEquals("uz", data.getTConst());
        Mockito.verify(foreignLangRepository, Mockito.times(1)).getByTConst("uz");
    }
}