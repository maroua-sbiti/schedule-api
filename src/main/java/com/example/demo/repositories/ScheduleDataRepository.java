package com.example.demo.repositories;
import com.example.demo.dto.ScheduleDataDto;
import java.util.List;
import java.util.Map;

public interface ScheduleDataRepository {

    /**
     * Méthode pour extraire la liste des opérations par bâtiment par niveau et super famille avec leurs durées respectif
     * calculées et cumulées par rapport aux quantités et temps de mise en oeuvre des articles correspondants
     * ainsi qu'aux ratios des opérations concernées
     */
    List<ScheduleDataDto> getScheduleData() throws Exception;

    Map<ScheduleDataDto,List<ScheduleDataDto>> scheduleDataGraph() throws Exception;

    List<ScheduleDataDto> topologicalSort(Map<ScheduleDataDto, List<ScheduleDataDto>> scheduleDataGraph) throws Exception;

    List<ScheduleDataDto> calculatePredictedStartAndEnd() throws Exception;

}
