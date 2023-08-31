package com.example.backgeotracker.Repositories;

import com.example.backgeotracker.Entities.Trace;
import com.example.backgeotracker.Entities.TraceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface traceRepository extends JpaRepository<Trace, Long> {
    void deleteTraceById(Long id);
     Optional<Trace> findById(Long id);
    List<Trace> findByStatus(TraceStatus status);
    @Query(value = "SELECT t.status, COUNT(*) FROM trace t GROUP BY t.status", nativeQuery = true)
    List<Object[]> countTracesByStatus();

}
	