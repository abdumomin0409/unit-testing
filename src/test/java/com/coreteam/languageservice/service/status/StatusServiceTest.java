package com.coreteam.languageservice.service.status;

import com.coreteam.languageservice.model.status.StatusDB;
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
class StatusServiceTest {
    StatusService statusService;
    @Mock
    StatusRepository statusRepository;

    @BeforeEach
    void setUp() {
        statusService = new StatusService(statusRepository);
    }

    @Test
    void successfullyGet() {
        Mockito.when(statusRepository.get(1)).thenReturn(StatusDB.builder().name("name").id(1).build());
        ResponseData<?> actual = statusService.get(1);
        StatusDB data = (StatusDB) actual.getData();
        assertEquals(1, data.getId());
        assertEquals("name", data.getName());
        Mockito.verify(statusRepository, Mockito.times(1)).get(1);
    }

    @Test
    void successfullyGetAll() {
        Mockito.when(statusRepository.getAll()).thenReturn(List.of(StatusDB.builder().name("name").id(1).build()));
        ResponseData<?> actual = statusService.getAll();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            StatusDB modulDB = (StatusDB) list;
            assertEquals("name", modulDB.getName());
            assertEquals(1, modulDB.getId());
        }
        Mockito.verify(statusRepository, Mockito.times(1)).getAll();
    }
}