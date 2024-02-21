package com.coreteam.languageservice.service.status;

import com.coreteam.languageservice.model.status.StatusDB;
import com.coreteam.languageservice.repository.status.StatusRepository;
import com.coreteam.languageservice.responce.ResponseData;
import com.coreteam.languageservice.service.AbstractService;
import com.coreteam.languageservice.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService extends AbstractService<StatusRepository> implements BaseService {

    public StatusService(StatusRepository repository) {
        super(repository);
    }

    public ResponseData<?> get(Integer id) {
        if (!repository.existsStatusById(id))
            return ResponseData.builder().success(false).httpStatus(HttpStatus.BAD_REQUEST).message("Invalid input").code(-1497001).build();
        return ResponseData.builder().success(true).data(repository.get(id)).code(1497001)
                .message("Successfully get status").httpStatus(HttpStatus.OK).build();
    }

    public ResponseData<?> getAll() {
        return ResponseData.builder().success(true).data(repository.getAll()).code(1497002)
                .message("Successfully get all status").httpStatus(HttpStatus.OK).build();
    }

}
