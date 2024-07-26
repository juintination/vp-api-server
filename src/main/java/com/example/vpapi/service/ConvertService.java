package com.example.vpapi.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ConvertService {

    ResponseEntity<Resource> uploadAndConvertFile(Resource fileResource);

}
