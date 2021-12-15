package com.example.demo.repositories;

import com.example.demo.dto.PrerequisitesDto;
import com.example.demo.dto.RequiredOperationDTO;
import com.example.demo.dto.ScheduleDataDto;
import com.example.demo.repositories.operation.OperationRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.*;


@Repository
public class ScheduleDataRepositoryImpl implements ScheduleDataRepository {

    @Autowired
    private OperationRepositoryCustom operationRepositoryCustom;

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<ScheduleDataDto> getScheduleData() throws Exception {
        List<ScheduleDataDto> scheduleData = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT t_level.name_building,t_level.level_name,t_group.super_family,t_operation.parent,");
        query.append("t_operation.operation,ROUND(SUM(t_article.unit_time*t_level_article.quantity*t_group_operation.rate),2)AS ");
        query.append("total_duration,t_level.starting_availability,t_level.end_availability FROM t_level,t_level_article,");
        query.append("t_article,t_group,t_group_operation,t_operation WHERE t_level.id_level=t_level_article.id_level ");
        query.append("AND t_level_article.id_article=t_article.id_article AND t_article.id_group=t_group.id_group AND ");
        query.append("t_group.id_group=t_group_operation.id_group AND t_group_operation.id_operation=t_operation.id_operation ");
        query.append("Group BY t_level.name_building, t_level.level_name,t_group.super_family,t_operation.operation");
//        query.append("SELECT t_level.name_building,t_level.level_name,t_group.super_family,t_operation.parent,t_operation.operation,ROUND(SUM(t_article.unit_time*t_level_article.quantity*t_group_operation.rate),2)AS total_duration,t_level.starting_availability,t_level.end_availability FROM t_level,t_level_article,t_article,t_group,t_group_operation,t_operation WHERE t_level.id_level=t_level_article.id_level AND t_level_article.id_article=t_article.id_article AND t_article.id_group=t_group.id_group AND t_group.id_group=t_group_operation.id_group AND t_group_operation.id_operation=t_operation.id_operation and name_building=\"LPA\" and level_name=\"N0\"\n" +
//                "Group BY t_level.name_building, t_level.level_name,t_group.super_family,t_operation.operation");
        Query q = entityManager.createNativeQuery(query.toString());

        List<Object[]> results = q.getResultList();

        for (Object[] res : results) {
            ScheduleDataDto item = new ScheduleDataDto();
            item.setBuildingName("" + res[0]);
            item.setLevelName("" + res[1]);
            item.setSuperFamilyName("" + res[2]);
            item.setParent("" + res[3]);
            item.setOperationName("" + res[4]);
            item.setTaskDuration(Double.valueOf("" + res[5]));
            item.setLevelStartAvailability(res[6] != null ? LocalDate.parse("" + res[6]) : null);
            item.setLevelEndAvailability(res[7] != null ? LocalDate.parse("" + res[7]) : null);
            item.setRequiredOperations(operationRepositoryCustom.getRequiredOperationsByParentOperation(item.getOperationName()));
            scheduleData.add(item);
        }
        addPrerequisitesToScheduleData(scheduleData);
        return scheduleData;
    }


    private List<ScheduleDataDto> addPrerequisitesToScheduleData(List<ScheduleDataDto> scheduleData) throws Exception {
        try {
            List<PrerequisitesDto> prerequisitesDtoList = operationRepositoryCustom.getAllTypePRequiredOperations();
            for (PrerequisitesDto p : prerequisitesDtoList) {
                ScheduleDataDto item = new ScheduleDataDto();
                item.setOperationName(p.getOperation());
                item.setParent(p.getParent());
                item.setPredictedEnd(LocalDate.parse("" + p.getPrerequisiteMilestone()));
                scheduleData.add(0, item);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return scheduleData;
    }

    public Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph() throws Exception {
        List<ScheduleDataDto> scheduleData = addPrerequisitesToScheduleData(getScheduleData());
        Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph = new HashMap<>();
        for (ScheduleDataDto s : scheduleData) {
            scheduleDataGraph.put(s, null);
        }
        for (ScheduleDataDto opr : scheduleDataGraph.keySet()) {
            List<ScheduleDataDto> successors = new ArrayList<>();
            for (ScheduleDataDto ops : scheduleData) {
                if (ops.getBuildingName() != null && ops.getLevelName() != null) {
                    if (ops.getBuildingName().equals(opr.getBuildingName()) && ops.getLevelName().equals(opr.getLevelName())) {
                        List<RequiredOperationDTO> requiredOperations = operationRepositoryCustom.getRequiredOperationsByParentOperation(ops.getOperationName());
                        if (requiredOperations != null) {
                            for (RequiredOperationDTO r : requiredOperations) {
                                if (r.getNameRequiredOperation().equals(opr.getOperationName())) {
                                    successors.add(ops);
                                }
                            }
                        }
                    }
                } else {
                    List<RequiredOperationDTO> requiredOperations = operationRepositoryCustom.getRequiredOperationsByParentOperation(ops.getOperationName());
                    if (requiredOperations != null) {
                        for (RequiredOperationDTO r : requiredOperations) {
                            if (r.getNameRequiredOperation().equals(opr.getOperationName())) {
                                successors.add(ops);
                            }
                        }
                    }
                }
            }
            if (successors.size() > 0) {
                scheduleDataGraph.put(opr, successors);
            }
        }
        return scheduleDataGraph;
    }

    public List<ScheduleDataDto> topologicalSort(Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph) throws Exception {
        scheduleDataGraph = scheduleDataGraph();
        List<ScheduleDataDto> orderedScheduleDataGraph = new ArrayList<>();
        Map<ScheduleDataDto, Boolean> visited = new HashMap<>();
        for (ScheduleDataDto ope : scheduleDataGraph.keySet()) {
            visited.put(ope, Boolean.FALSE);
        }
        for (ScheduleDataDto ope : scheduleDataGraph.keySet()) {
            if (visited.get(ope) == Boolean.FALSE) {
                blackMagic(scheduleDataGraph, ope, visited, orderedScheduleDataGraph);
            }
        }
        Collections.reverse(orderedScheduleDataGraph);
        return orderedScheduleDataGraph;
    }

    public static void blackMagic(Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph, ScheduleDataDto ope, Map<ScheduleDataDto, Boolean> visited, List<ScheduleDataDto> orderedScheduleDataGraph) {
        visited.replace(ope, Boolean.TRUE);
        List<ScheduleDataDto> opeSuccessors = scheduleDataGraph.get(ope);
        if (opeSuccessors != null) {
            for (ScheduleDataDto s : opeSuccessors) {
                if (visited.get(s) == Boolean.FALSE) {
                    blackMagic(scheduleDataGraph, s, visited, orderedScheduleDataGraph);
                }
            }
            if (!orderedScheduleDataGraph.contains(ope)) {
                orderedScheduleDataGraph.add(ope);
            }
        }
        if (!orderedScheduleDataGraph.contains(ope)) {
            orderedScheduleDataGraph.add(ope);
        }
    }

    public List<ScheduleDataDto> calculatePredictedStartAndEnd() throws Exception {
        List<ScheduleDataDto> orderedScheduleData = topologicalSort(scheduleDataGraph());
        LocalDate maxDate;
        long daysToAdd;
        for (ScheduleDataDto ope : orderedScheduleData) {
            if (ope.getBuildingName() != null && ope.getLevelName() != null) {
                List<RequiredOperationDTO> prerequisites = ope.getRequiredOperations();
                if (!prerequisites.isEmpty()) {
                    List<LocalDate> prerequisitesEndingDates = new ArrayList<>();
                    for (RequiredOperationDTO p : prerequisites) {
                        if (!"P".equals(p.getTypeRequiredOperation())) {
                            for (ScheduleDataDto i : orderedScheduleData.subList(0, orderedScheduleData.indexOf(ope))) {
                                if (i.getOperationName().equals(p.getNameRequiredOperation()) && (i.getBuildingName().equals(ope.getBuildingName()) && (i.getLevelName().equals(ope.getLevelName())))) {
                                    prerequisitesEndingDates.add(i.getPredictedEnd());
                                }
                            }
                        }
                        if (p.getMilestoneRequiredOperation() != null) {
                            prerequisitesEndingDates.add(p.getMilestoneRequiredOperation());
                        }
                    }
                    maxDate = !prerequisitesEndingDates.isEmpty() ? prerequisitesEndingDates.stream().max(LocalDate::compareTo).get() : ope.getLevelStartAvailability();
                    LocalDate opePredictedStart = maxDate.isBefore(ope.getLevelStartAvailability()) ? ope.getLevelStartAvailability() : maxDate;
                    ope.setPredictedStart(opePredictedStart);
                    daysToAdd = (ope.getTaskDuration().longValue()) / 8;
                    if (opePredictedStart.plusDays(daysToAdd).isBefore(ope.getLevelEndAvailability())) {
                        ope.setPredictedEnd(opePredictedStart.plusDays(daysToAdd));
                    } else {
                        ope.setPredictedEnd(ope.getLevelEndAvailability());
                    }
                } else {
                    ope.setPredictedStart(ope.getLevelStartAvailability());
                    daysToAdd = (ope.getTaskDuration().longValue()) / 8;
                    if (ope.getPredictedStart().plusDays(daysToAdd).isBefore(ope.getLevelEndAvailability())) {
                        ope.setPredictedEnd(ope.getPredictedStart().plusDays(daysToAdd));
                    } else {
                        ope.setPredictedEnd(ope.getLevelEndAvailability());
                    }
                }
            }
        }
        return orderedScheduleData;
    }
}



