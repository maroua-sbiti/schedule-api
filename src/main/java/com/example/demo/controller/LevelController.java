package com.example.demo.controller;
import com.example.demo.dto.ScheduleDataDto;
import com.example.demo.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping("/schedule/allData")
    public ResponseEntity<List<ScheduleDataDto>> getScheduleData() throws Exception{
            return new ResponseEntity(levelService.getScheduleData(),HttpStatus.OK);
    }
    @GetMapping("/schedule/graph")
    public ResponseEntity<Map<ScheduleDataDto,List<ScheduleDataDto>>> scheduleDataGraph()throws Exception{
        return new ResponseEntity(levelService.scheduleDataGraph(),HttpStatus.OK);
    }

    @GetMapping("/schedule/sortedData")
    public ResponseEntity<List<ScheduleDataDto>> topologicalSort(Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph) throws Exception{
        return new ResponseEntity(levelService.topologicalSort(scheduleDataGraph),HttpStatus.OK);
    }

    @GetMapping("/schedule/finalData")
    public ResponseEntity<List<ScheduleDataDto>> calculatePredictedStartAndEnd() throws Exception{
        return new ResponseEntity(levelService.calculatePredictedStartAndEnd(),HttpStatus.OK);
    }

}
