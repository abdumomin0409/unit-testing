package com.coreteam.languageservice.service;

import com.coreteam.languageservice.repository.BaseRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractService<R extends BaseRepository> {
    protected final R repository;
}
