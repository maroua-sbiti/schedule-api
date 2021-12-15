package com.example.demo.service;

import com.example.demo.dto.PrerequisitesDto;
import com.example.demo.dto.RequiredOperationDTO;
import com.example.demo.entity.Operation;
import com.example.demo.repositories.operation.OperationRepository;
import com.example.demo.repositories.operation.OperationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OperationService {

    @Autowired
    private OperationRepositoryCustom operationRepositoryCustom;


    public List<PrerequisitesDto> getAllTypePRequiredOperations() throws Exception {
        return operationRepositoryCustom.getAllTypePRequiredOperations();
    }

    public void addEndingDateToTypePOperations(String prerequisiteName, PrerequisitesDto prerequisiteDto) {
        operationRepositoryCustom.addEndingDateToTypePOperations(prerequisiteName, prerequisiteDto);
    }

    public List<RequiredOperationDTO> getRequiredOperationsByParentOperation(String operationName) throws Exception {
        return operationRepositoryCustom.getRequiredOperationsByParentOperation(operationName);
    }

}
