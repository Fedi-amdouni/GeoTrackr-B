package com.example.backgeotracker.Services;


import com.example.backgeotracker.Entities.Trace;
import com.example.backgeotracker.Entities.TraceStatus;
import com.example.backgeotracker.Repositories.traceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class TraceService {
    @Autowired
    private com.example.backgeotracker.Repositories.traceRepository traceRepository;

    public TraceService(traceRepository traceRepository){
        this.traceRepository=traceRepository;
    }

    public Trace addTraceByAdmin(Trace trace) {
        trace.setStatus(TraceStatus.VALIDE);
        return traceRepository.save(trace);
    }
    public Trace addTrace(Trace trace) {
        return traceRepository.save(trace);
    }


    public Trace updateTrace(Trace trace, Trace updatedTrace) {


        // Update the trace fields based on the provided values in the updatedTrace object
        if (updatedTrace.getLabel() != null) {
            trace.setLabel(updatedTrace.getLabel());
        }

        if (updatedTrace.getLongitudes() != null) {
            trace.setLongitudes(updatedTrace.getLongitudes());
        }

        if (updatedTrace.getLatitudes() != null) {
            trace.setLatitudes(updatedTrace.getLatitudes());
        }

        return traceRepository.save(trace);
    }

    public List<Trace> getAllTraces() {
        return traceRepository.findAll();
    }
    public void deleteTrace(Long id){
        traceRepository.deleteTraceById(id);
    }
    public List<Trace> getTracesByStatus(TraceStatus status){
        return traceRepository.findByStatus(status);
    }


    public Map<TraceStatus, Long> getTraceCountsByStatus() {
        List<Object[]> countsByStatus = traceRepository.countTracesByStatus();
        Map<TraceStatus, Long> traceCountsByStatus = new HashMap<>();

        for (Object[] result : countsByStatus) {
            String statusString = (String) result[0]; // Get the status as a string
            TraceStatus status = TraceStatus.valueOf(statusString); // Convert string to TraceStatus
            Long count = (Long) result[1];
            traceCountsByStatus.put(status, count);
        }

        return traceCountsByStatus;
    }


}
