package com.coreteam.languageservice.controller;

public interface BaseURL {
    String BASE_URL = "/api/v2";
    String STATUS_URL = BASE_URL + "/status";
    String LANGUAGE_URL = BASE_URL + "/language";
    String MODUL_URL = BASE_URL + "/modul";
    String FOREIGN_LANG_URL = BASE_URL + "/foreign-lang";
    String BASIC_CREATE_URL = "/create";
    String BASIC_UPDATE_URL = "/update";
    String BASIC_DELETE_BY_ID_URL = "/delete/{deletedID}";
    String BASIC_GET_BY_ID_URL = "/get-id/{getID}";
    String BASIC_GET_BY_T_CONST_URL = "/get-const/{tConst}";
    String BASIC_GET_ALL_URL = "/get-all";
    String BASIC_GET_ALL_ACTIVE_URL = "/get-all-active";
    String BASIC_GET_ALL_BY_STATUS_ID_URL = "/get-all-status/{statusID}";
    String BASIC_GET_ALL_BY_MODULE_URL = "/get-all-modul/{modulID}";
}
