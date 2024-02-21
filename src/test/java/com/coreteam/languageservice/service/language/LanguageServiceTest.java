package com.coreteam.languageservice.service.language;

import com.coreteam.languageservice.dto.language.LanguageCreateDTO;
import com.coreteam.languageservice.dto.language.LanguageUpdateDTO;
import com.coreteam.languageservice.model.language.LanguageDB;
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
class LanguageServiceTest {
    LanguageService languageService;

    @Mock
    LanguageRepository languageRepository;

    @Mock
    StatusRepository statusRepository;

    @BeforeEach
    void setUp() {
        languageService = new LanguageService(languageRepository, statusRepository);
    }

    @Test
    void successfullyCreate() {
        LanguageCreateDTO languageCreateDTO = LanguageCreateDTO.builder().short_name("uz").name("ingliz").build();
        Mockito.when(languageRepository.create(languageCreateDTO)).thenReturn(1);
        ResponseData<?> actual = languageService.create(languageCreateDTO);
        assertEquals(1, actual.getData());
        Mockito.verify(languageRepository, Mockito.times(1)).create(languageCreateDTO);
    }

    @Test
    void successfullyUpdate() {
        LanguageUpdateDTO languageUpdateDTO = LanguageUpdateDTO.builder().language_id(1).short_name("uz").name("ingliz").build();
        Mockito.when(languageRepository.update(languageUpdateDTO)).thenReturn(true);
        ResponseData<?> actual = languageService.update(languageUpdateDTO.getLanguage_id(), languageUpdateDTO);
        assertEquals(true, actual.getData());
        Mockito.verify(languageRepository, Mockito.times(1)).update(languageUpdateDTO);
    }

    @Test
    void successfullyGet() {
        Mockito.when(languageRepository.get(1)).thenReturn(LanguageDB.builder().id(1).build());
        ResponseData<?> actual = languageService.get(1);
        LanguageDB data = (LanguageDB) actual.getData();
        assertEquals(1, data.getId());
        Mockito.verify(languageRepository, Mockito.times(1)).get(1);
    }

    @Test
    void successfullyGetAll() {
        Mockito.when(languageRepository.getAll()).thenReturn(List.of(LanguageDB.builder().status(1).name("uzb").build()));
        ResponseData<?> actual = languageService.getAll();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            LanguageDB languageDB = (LanguageDB) list;
            assertEquals("uzb", languageDB.getName());
            assertEquals(1, languageDB.getStatus());
        }
        Mockito.verify(languageRepository, Mockito.times(1)).getAll();
    }

    @Test
    void successfullyGetAllActive() {
        Mockito.when(languageRepository.getAllActive()).thenReturn(List.of(LanguageDB.builder().name("asd").status(1).build()));
        ResponseData<?> actual = languageService.getAllActive();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            LanguageDB languageDB = (LanguageDB) list;
            assertEquals("asd", languageDB.getName());
            assertEquals(1, languageDB.getStatus());
        }
        Mockito.verify(languageRepository, Mockito.times(1)).getAllActive();
    }

    @Test
    void successfullyDelete() {
        Mockito.when(languageRepository.delete(1)).thenReturn(false);
        ResponseData<?> actual = languageService.delete(1);
        assertEquals(false, actual.getData());
        Mockito.verify(languageRepository, Mockito.times(1)).delete(1);
    }

    @Test
    void successfullyGetAllByStatusID() {
        Mockito.when(languageRepository.getAllByStatus(1)).thenReturn(List.of(LanguageDB.builder().id(123).status(4).build()));
        ResponseData<?> actual = languageService.getAllByStatusID(1);
        List lists = (List) actual.getData();
        for (Object list : lists) {
            LanguageDB languageDB = (LanguageDB) list;
            assertEquals(123, languageDB.getId());
            assertEquals(4, languageDB.getStatus());
        }
        Mockito.verify(languageRepository, Mockito.times(1)).getAllByStatus(1);
    }
}