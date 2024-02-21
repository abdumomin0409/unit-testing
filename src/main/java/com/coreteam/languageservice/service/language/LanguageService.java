package com.coreteam.languageservice.service.language;

import com.coreteam.languageservice.dto.language.LanguageCreateDTO;
import com.coreteam.languageservice.dto.language.LanguageUpdateDTO;
import com.coreteam.languageservice.repository.language.LanguageRepository;
import com.coreteam.languageservice.repository.status.StatusRepository;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.AbstractService;
import com.coreteam.languageservice.service.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LanguageService extends AbstractService<LanguageRepository>
        implements GenericService<LanguageCreateDTO, LanguageUpdateDTO, ResponseData<?>, Integer> {
    private final StatusRepository statusRepository;

    public LanguageService(LanguageRepository repository, StatusRepository statusRepository) {
        super(repository);
        this.statusRepository = statusRepository;
    }

    @Override
    public ResponseData<?> create(LanguageCreateDTO dto) {
        if (repository.existsLanguageByName(dto.getName()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498001).build();
        if (repository.existsLanguageByShortName(dto.getShort_name()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498002).build();
        return ResponseData.builder().success(true).data(repository.create(dto)).code(1498001).
                message("Successfully create language").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseData<?> update(Integer id, LanguageUpdateDTO dto) {
        if (!repository.existsLanguageById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498003).build();
        repository.update(dto);
        return ResponseData.builder().success(true).code(1498002).
                message("Successfully update language").httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseData<?> get(Integer id) {
        if (!repository.existsLanguageById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498004).build();
        return ResponseData.builder().success(true).data(repository.get(id)).code(1498003).
                message("Successfully get language").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> getAll() {
        return ResponseData.builder().success(true).data(repository.getAll()).code(1498003).
                message("Successfully get all language").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getAllActive() {
        return ResponseData.builder().success(true).data(repository.getAllActive()).code(1498003).
                message("Successfully get all language").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> delete(Integer id) {
        if (!repository.existsLanguageById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498005).build();
        repository.delete(id);
        return ResponseData.builder().success(true).code(1498004).
                message("Successfully delete language").httpStatus(HttpStatus.ACCEPTED).build();
    }

    public ResponseData<?> getAllByStatusID(Integer statusID) {
        if (!statusRepository.existsStatusById(statusID))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1498006).build();
        return ResponseData.builder().success(true).data(repository.getAllByStatus(statusID)).code(1498003).
                message("Successfully get all language").httpStatus(HttpStatus.OK).build();
    }
}
