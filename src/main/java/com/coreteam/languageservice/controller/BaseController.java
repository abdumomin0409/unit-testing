package com.coreteam.languageservice.controller;

import com.coreteam.languageservice.service.BaseService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseController<S extends BaseService>  {
    protected final S service;
}
