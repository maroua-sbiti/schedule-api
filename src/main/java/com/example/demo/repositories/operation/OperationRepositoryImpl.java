package com.example.demo.repositories.operation;


import com.example.demo.dto.PrerequisitesDto;
import com.example.demo.dto.RequiredOperationDTO;
import com.example.demo.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OperationRepositoryImpl implements OperationRepositoryCustom {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OperationRepository operationRepository;


    @Override
    public List<PrerequisitesDto> getAllTypePRequiredOperations() throws Exception {
        List<PrerequisitesDto> prerequisites = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT operation,parent,prerequisite_milestone FROM `t_operation` WHERE operation_type='P'");
        Query q = entityManager.createNativeQuery(query.toString());

        List<Object[]> results = q.getResultList();
        for (Object[] res : results) {
            PrerequisitesDto item = new PrerequisitesDto();
            item.setOperation("" + res[0]);
            item.setParent("" + res[1]);
            item.setPrerequisiteMilestone(res[2] != null ? LocalDate.parse("" + res[2]) : null);
            prerequisites.add(item);
        }
        return prerequisites;
    }

    @Override
    public List<RequiredOperationDTO> getRequiredOperationsByParentOperation(String operationName) {
        List<RequiredOperationDTO> requiredOperations = new ArrayList<>();

        StringBuilder query = new StringBuilder();
        query.append("SELECT op.id_operation,op.operation,top.id_operation_prerequisite,ope.operation_type,ope.operation as prerequis,ope.parent,ope.prerequisite_milestone FROM t_operation op INNER JOIN t_operation_prerequisite top on op.id_operation = top.id_operation INNER JOIN t_operation ope on top.id_operation_prerequisite = ope.id_operation WHERE op.operation =:operationName");
        Query q = entityManager.createNativeQuery(query.toString());
        q.setParameter("operationName", operationName);

        List<Object[]> results = q.getResultList();
        for (Object[] res : results) {
            RequiredOperationDTO item = new RequiredOperationDTO();
            item.setIdRequiredOperation(Long.valueOf("" + res[2]));
            item.setTypeRequiredOperation("" + res[3]);
            item.setNameRequiredOperation("" + res[4]);
            item.setParentRequiredOperation("" + res[5]);
            item.setMilestoneRequiredOperation(res[6] != null ? LocalDate.parse("" + res[6]) : null);
            requiredOperations.add(item);
        }
        return requiredOperations;
    }

    @Override
    public void addEndingDateToTypePOperations(String prerequisiteName, PrerequisitesDto prerequisiteDto) {
        List<Operation> operations;
        operations = operationRepository.findAll();
        for (Operation ope : operations) {
            if (ope.getOperation().equals(prerequisiteName)) {
                ope.setPrerequisiteMilestone(prerequisiteDto.getPrerequisiteMilestone());
                operationRepository.save(ope);
            }
        }
    }

}
