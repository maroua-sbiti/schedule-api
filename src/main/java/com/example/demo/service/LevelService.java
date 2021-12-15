package com.example.demo.service;
import com.example.demo.dto.ScheduleDataDto;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class LevelService {

    @Autowired
    private ScheduleDataRepository scheduleDataRepository;

    public List<ScheduleDataDto>getScheduleData() throws  Exception{
        return scheduleDataRepository.getScheduleData();
    }

    public Map<ScheduleDataDto,List<ScheduleDataDto>> scheduleDataGraph() throws Exception{
        return scheduleDataRepository.scheduleDataGraph();
    }

    public List<ScheduleDataDto> topologicalSort(Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph) throws Exception {
        return scheduleDataRepository.topologicalSort(scheduleDataGraph);
    }

    public List<ScheduleDataDto> calculatePredictedStartAndEnd() throws Exception {
        return scheduleDataRepository.calculatePredictedStartAndEnd();
    }
}
