package com.coreteam.languageservice.service;

import com.coreteam.languageservice.dto.BaseDTO;
import com.coreteam.languageservice.responce.ResponseData;

public interface GenericService<CD extends BaseDTO, UD, GD extends ResponseData<?>, K> extends BaseService {
    GD create(CD dto);
    GD update(K id, UD dto);
    GD get(K id);
    GD getAll();
    GD delete(K id);
}
