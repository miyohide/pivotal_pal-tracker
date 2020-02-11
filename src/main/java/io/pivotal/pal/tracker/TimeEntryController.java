package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
  private final TimeEntryRepository timeEntryRepository;

  public TimeEntryController(TimeEntryRepository timeEntryRepository) {
    this.timeEntryRepository = timeEntryRepository;
  }

  @PostMapping
  public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
    TimeEntry t = timeEntryRepository.create(timeEntryToCreate);
    return new ResponseEntity<>(t, HttpStatus.CREATED);
  }

  @GetMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
    TimeEntry t = timeEntryRepository.find(timeEntryId);
    if (t != null) {
      return new ResponseEntity<>(t, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping
  public ResponseEntity<List<TimeEntry>> list() {
    List<TimeEntry> ts = timeEntryRepository.list();
    return new ResponseEntity<>(ts, HttpStatus.OK);
  }

  @PutMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntry) {
    TimeEntry t = timeEntryRepository.update(timeEntryId, timeEntry);
    if (t != null) {
      return new ResponseEntity<>(t, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("{timeEntryId}")
  public ResponseEntity<TimeEntry> delete(@PathVariable Long timeEntryId) {
    timeEntryRepository.delete(timeEntryId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
