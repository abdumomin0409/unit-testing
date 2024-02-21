package com.coreteam.languageservice.service.modul;

import com.coreteam.languageservice.dto.modul.ModulCreateDTO;
import com.coreteam.languageservice.dto.modul.ModulUpdateDTO;
import com.coreteam.languageservice.model.modul.ModulDB;
import com.coreteam.languageservice.repository.modul.ModulRepository;
import com.coreteam.languageservice.repository.status.StatusRepository;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.AbstractService;
import com.coreteam.languageservice.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ModulService extends AbstractService<ModulRepository>
        implements GenericService<ModulCreateDTO, ModulUpdateDTO, ResponseData<?>, Integer> {
    private final StatusRepository statusRepository;
    public ModulService(ModulRepository repository, StatusRepository statusRepository) {
        super(repository);
        this.statusRepository = statusRepository;
    }

    @Override
    public ResponseData<?> create(ModulCreateDTO dto) {
        if (repository.existsModuleByName(dto.getName()))
            return ResponseData.builder().success(false).code(-1499000)
                    .message("Invalid input").httpStatus(HttpStatus.BAD_REQUEST).build();
        Integer data = repository.create(dto);
        return ResponseData.builder().success(true).data(data).code(1499000).
                message("Successfully create modul").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseData<?> update(Integer id, ModulUpdateDTO dto) {
        if (!repository.existsModuleById(id))
            return ResponseData.builder().success(false).code(-1499001)
                    .message("Invalid input").httpStatus(HttpStatus.BAD_REQUEST).build();
        repository.update(dto);
        return ResponseData.builder().success(true).code(1499001).
                message("Successfully update modul").httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseData<?> get(Integer id) {
        if (!repository.existsModuleById(id))
            return ResponseData.builder().success(false).code(-1499002)
                    .message("Invalid input").httpStatus(HttpStatus.BAD_REQUEST).build();
        ModulDB data = repository.get(id);
        return ResponseData.builder().success(true).data(data).code(1499002).
                message("Successfully get module").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> getAll() {
        return ResponseData.builder().success(true).data(repository.getAll()).code(1499003).
                message("Successfully get all module").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getAllActive() {
        return ResponseData.builder().success(true).data(repository.getAllActive()).code(1499004).
                message("Successfully get all module").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> delete(Integer id) {
        if (!repository.existsModuleById(id))
            return ResponseData.builder().success(false).code(-1499003)
                    .message("Invalid input").httpStatus(HttpStatus.BAD_REQUEST).build();
        repository.delete(id);
        return ResponseData.builder().success(true).code(1499005).
                message("Successfully delete module").httpStatus(HttpStatus.ACCEPTED).build();
    }

    public ResponseData<?> getAllByStatusID(Integer statusID) {
        if (!statusRepository.existsStatusById(statusID))
            return ResponseData.builder().success(false).code(-1499004)
                    .message("Invalid input").httpStatus(HttpStatus.BAD_REQUEST).build();
        return ResponseData.builder().success(true).data(repository.getAllByStatus(statusID)).code(1499006).
                message("Successfully get all module").httpStatus(HttpStatus.OK).build();
    }
}
