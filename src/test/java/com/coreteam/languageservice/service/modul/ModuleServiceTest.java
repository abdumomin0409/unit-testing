package com.coreteam.languageservice.service.modul;

import com.coreteam.languageservice.dto.modul.ModulCreateDTO;
import com.coreteam.languageservice.dto.modul.ModulUpdateDTO;
import com.coreteam.languageservice.model.modul.ModulDB;
import com.coreteam.languageservice.repository.modul.ModulRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {
    ModulService modulService;

    @Mock
    ModulRepository modulRepository;

    @Mock
    StatusRepository statusRepository;

    @BeforeEach
    void setUp() {
        modulService = new ModulService(modulRepository, statusRepository);
    }

    @Test
    void successfullyCreate() {
        ModulCreateDTO modulCreateDTO = new ModulCreateDTO("module_1");
        when(modulRepository.create(modulCreateDTO)).thenReturn(1);
        ResponseData<?> actual = modulService.create(modulCreateDTO);
        assertEquals(1, actual.getData());
        Mockito.verify(modulRepository, Mockito.times(1)).create(modulCreateDTO);
    }

    @Test
    void successfullyUpdate() {
        ModulUpdateDTO modulUpdateDTO = new ModulUpdateDTO(1, "module_name");
        when(modulRepository.update(modulUpdateDTO)).thenReturn(true);
        ResponseData<?> actual = modulService.update(modulUpdateDTO.getModul_id(), modulUpdateDTO);
        assertEquals(true, actual.getData());
        verify(modulRepository, times(1)).update(modulUpdateDTO);
    }

    @Test
    void successfullyGet() {
        ModulDB modulDB = ModulDB.builder().id(1).name("module").build();
        when(modulRepository.get(1)).thenReturn(modulDB);
        ResponseData<?> actual = modulService.get(1);
        ModulDB data = (ModulDB) actual.getData();
        assertEquals(1, data.getId());
        assertEquals("module", data.getName());
        verify(modulRepository, times(1)).get(1);
    }

    @Test
    void successfullyGetAll() {
        when(modulRepository.getAll()).thenReturn(List.of(ModulDB.builder().name("module1").status(1).build()));
        ResponseData<?> actual = modulService.getAll();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ModulDB modulDB = (ModulDB) list;
            assertEquals("module1", modulDB.getName());
        }
        verify(modulRepository, times(1)).getAll();
    }

    @Test
    void successfullyGetAllActive() {
        when(modulRepository.getAllActive()).thenReturn(List.of(ModulDB.builder().status(1).id(1).build()));
        ResponseData<?> actual = modulService.getAllActive();
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ModulDB modulDB = (ModulDB) list;
            assertEquals(1, modulDB.getId());
            assertEquals(1, modulDB.getStatus());
        }
        verify(modulRepository, times(1)).getAllActive();
    }

    @Test
    void successfullyDelete() {
        when(modulRepository.delete(1)).thenReturn(true);
        ResponseData<?> actual = modulService.delete(1);
        assertEquals(true, actual.getData());
        verify(modulRepository, times(1)).delete(1);
    }

    @Test
    void successfullyGetAllByStatusID() {
        when(modulRepository.getAllByStatus(1)).thenReturn(List.of(ModulDB.builder().status(1).id(1).build()));
        ResponseData<?> actual = modulService.getAllByStatusID(1);
        List lists = (List) actual.getData();
        for (Object list : lists) {
            ModulDB modulDB = (ModulDB) list;
            assertEquals(1, modulDB.getStatus());
            assertEquals(1, modulDB.getId());
        }
        verify(modulRepository, times(1)).getAllByStatus(1);
    }
}