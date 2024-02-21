package com.coreteam.languageservice.service.foreign_lang;

import com.coreteam.languageservice.dto.foreign_lang.ForeignLangCreateDTO;
import com.coreteam.languageservice.dto.foreign_lang.ForeignLangUpdateDTO;
import com.coreteam.languageservice.model.foreign_lang.ForeignLangDB;
import com.coreteam.languageservice.repository.foreign_lang.ForeignLangRepository;
import com.coreteam.languageservice.repository.language.LanguageRepository;
import com.coreteam.languageservice.repository.status.StatusRepository;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.AbstractService;
import com.coreteam.languageservice.service.GenericService;
import com.coreteam.languageservice.service.status.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForeignLangService extends AbstractService<ForeignLangRepository>
        implements GenericService<ForeignLangCreateDTO, ForeignLangUpdateDTO, ResponseData<?>, Integer> {
    private final StatusRepository statusRepository;
    private final LanguageRepository languageRepository;


    public ForeignLangService(ForeignLangRepository repository, StatusRepository statusRepository, LanguageRepository languageRepository) {
        super(repository);
        this.statusRepository = statusRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public ResponseData<?> create(ForeignLangCreateDTO dto) {
        if (repository.existsForeignLangByTConst(dto.getT_const()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496000).build();
        if (!repository.existsForeignLangByModuleId(dto.getModul_id()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496001).build();
        if (!languageRepository.existsLanguageByName(dto.getLang()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496003).build();
        return ResponseData.builder().success(true).data(repository.create(dto)).code(1496000).
                message("Successfully created foreign language").httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseData<?> update(Integer id, ForeignLangUpdateDTO dto) {
        if (!repository.existsForeignLangById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496004).build();
        if (!repository.existsForeignLangByModuleId(dto.getModul_id()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496005).build();
        if (!languageRepository.existsLanguageByName(dto.getLang()))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496006).build();
        repository.update(dto);
        return ResponseData.builder().success(true).code(1496001).
                message("Successfully updated foreign language").httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseData<?> get(Integer id) {
        if (!repository.existsForeignLangById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496007).build();
        return ResponseData.builder().success(true).data(repository.get(id)).code(1496002).
                message("Successfully get foreign language").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> getAll() {
        List<ForeignLangDB> all = repository.getAll();
        return ResponseData.builder().success(true).data(all).code(1496003).
                message("Successfully get all foreign language").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getAllActive() {
        return ResponseData.builder().success(true).data(repository.getAllActive()).code(1496004).
              message("Successfully get all foreign language").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ResponseData<?> delete(Integer id) {
        if (!repository.existsForeignLangById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496008).build();
        repository.delete(id);
        return ResponseData.builder().success(true).code(1496005).
              message("Successfully deleted foreign language").httpStatus(HttpStatus.ACCEPTED).build();
    }

    public ResponseData<?> getAllByStatusID(Integer statusID) {
        if (!statusRepository.existsStatusById(statusID))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496009).build();
        return ResponseData.builder().success(true).data(repository.getAllByStatus(statusID)).code(1496006).
                message("Successfully get all foreign language").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getAllByModulID(Integer modulId) {
        if (!repository.existsForeignLangByModuleId(modulId))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496010).build();
        return ResponseData.builder().success(true).data(repository.getAllByModul(modulId)).code(1496007).
                message("Successfully get all foreign language").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getByTConst(String tConst) {
        if (!repository.existsForeignLangByTConst(tConst))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1496011).build();
        return ResponseData.builder().success(true).data(repository.getByTConst(tConst)).code(1496008).
                message("Successfully get foreign language").httpStatus(HttpStatus.OK).build();
    }

}
