package com.example.demo.repositories.operation;

import com.example.demo.dto.PrerequisitesDto;
import com.example.demo.dto.RequiredOperationDTO;

import java.util.List;

public interface OperationRepositoryCustom {

    List<PrerequisitesDto> getAllTypePRequiredOperations() throws Exception;

    List<RequiredOperationDTO> getRequiredOperationsByParentOperation(String operationName);

    void addEndingDateToTypePOperations(String prerequisiteName, PrerequisitesDto prerequisiteDto);
}
