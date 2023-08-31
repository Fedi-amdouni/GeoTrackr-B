package com.example.backgeotracker.Controllers;

import com.example.backgeotracker.Entities.TraceStatus;
import com.example.backgeotracker.Repositories.traceRepository;
import com.example.backgeotracker.Entities.Trace;
import com.example.backgeotracker.Entities.User;
import com.example.backgeotracker.Services.TraceService;
import com.example.backgeotracker.Services.TraceValidationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backgeotracker.Repositories.traceRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/trace")
@AllArgsConstructor
@CrossOrigin("*")
public class TraceController {

    private final TraceService traceService;
    private final TraceValidationService traceValidationService;
    @Autowired
    private traceRepository traceRepository;


    @GetMapping("/all")
    public List<Trace> getAllTraces() {
        List<Trace> traces = traceService.getAllTraces();
        return traces;
    }
    @GetMapping("/{status}")
    public List<Trace> getTracesByStatus(@PathVariable("status") TraceStatus status) {
        List<Trace> traces = traceService.getTracesByStatus(status);
        return traces;
    }
    @PostMapping("/addbyadmin")
    public Trace addTrace(@RequestBody Trace trace) {
        Trace newTrace = traceService.addTraceByAdmin(trace);
        return newTrace;
    }
    @PostMapping("/add")
    public Trace newTrace(@RequestBody Trace trace) {
        Trace newTrace = traceService.addTrace(trace);
        return newTrace;
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTrace(@PathVariable("id") Long id) {
        traceService.deleteTrace(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Trace> updateTrace(@PathVariable Long id, @RequestBody Trace updatedTrace) {
        Optional<Trace> traceOptional = traceRepository.findById(id);
        if (traceOptional.isPresent()) {
            Trace trace = traceOptional.get();
            Trace updated = traceService.updateTrace(trace, updatedTrace);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{traceId}/valider")
    public Trace validerTrace(@PathVariable long traceId) {
        Trace trace = traceValidationService.validerTrace(traceId);
        return trace;
    }

    @PatchMapping("/{traceId}/rejeter")
    public Trace rejeterTrace(@PathVariable long traceId) {
        Trace trace = traceValidationService.rejeterTrace(traceId);
        return trace;
    }


    @GetMapping("/count-by-status")
    public Map<TraceStatus, Long> getTraceCountsByStatus() {
        return traceService.getTraceCountsByStatus();
    }

}
