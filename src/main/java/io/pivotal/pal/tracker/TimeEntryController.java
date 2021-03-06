package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
  private final TimeEntryRepository timeEntryRepository;
  private final Counter actionCounter;
  private final DistributionSummary timeEntrySummary;

  public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
    this.timeEntryRepository = timeEntryRepository;
    this.actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    this.timeEntrySummary = meterRegistry.summary("timeEntry.summary");
  }

  @PostMapping
  public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
    actionCounter.increment();
    TimeEntry t = timeEntryRepository.create(timeEntryToCreate);
    timeEntrySummary.record(timeEntryRepository.list().size());
    return new ResponseEntity<>(t, HttpStatus.CREATED);
  }

  @GetMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
    actionCounter.increment();
    TimeEntry t = timeEntryRepository.find(timeEntryId);
    timeEntrySummary.record(timeEntryRepository.list().size());
    if (t != null) {
      return new ResponseEntity<>(t, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<TimeEntry>> list() {
    actionCounter.increment();
    List<TimeEntry> ts = timeEntryRepository.list();
    timeEntrySummary.record(ts.size());
    return new ResponseEntity<>(ts, HttpStatus.OK);
  }

  @PutMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
    actionCounter.increment();
    TimeEntry t = timeEntryRepository.update(timeEntryId, timeEntry);
    timeEntrySummary.record(timeEntryRepository.list().size());
    if (t != null) {
      return new ResponseEntity<>(t, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> delete(@PathVariable Long timeEntryId) {
    actionCounter.increment();
    timeEntryRepository.delete(timeEntryId);
    timeEntrySummary.record(timeEntryRepository.list().size());

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
