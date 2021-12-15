package com.example.demo.controller;
import com.example.demo.dto.PrerequisitesDto;
import com.example.demo.dto.RequiredOperationDTO;
import com.example.demo.entity.Operation;
import com.example.demo.service.OperationService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationController {

    @Autowired
    private OperationService operationService;

//Controller pour afficher tous les prérequis de type P
    @GetMapping("/prerequisites/")
    public ResponseEntity<List<PrerequisitesDto>> getAllTypePRequiredOperations() throws Exception {
        return new ResponseEntity(operationService.getAllTypePRequiredOperations(),HttpStatus.OK);
    }

//Controller pour ajouter/modifier la valeur du jalons d'un prérequis de type P par son nom
    @PutMapping("/addPrerequisitesMilestones/{prerequisiteName}")
    public void addPrerequisiteMilestone (@PathVariable String prerequisiteName, @RequestBody PrerequisitesDto prerequisiteDto){
        operationService.addEndingDateToTypePOperations(prerequisiteName,prerequisiteDto);
    }

    @GetMapping("/requiredOperations/{operationName}")
    public ResponseEntity<List<RequiredOperationDTO>> getRequiredOperations(@PathVariable String operationName) throws Exception {
        return new ResponseEntity(operationService.getRequiredOperationsByParentOperation(operationName),HttpStatus.OK);
    }

}
