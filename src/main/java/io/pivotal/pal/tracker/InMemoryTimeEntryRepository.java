package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
  private AtomicLong id = new AtomicLong(0L);
  private Map<Long, TimeEntry> timeEntryMap = new HashMap<>();

  @Override
  public TimeEntry create(TimeEntry timeEntry) {
    long newId = id.incrementAndGet();
    timeEntry.setId(newId);
    timeEntryMap.put(newId, timeEntry);
    return timeEntry;
  }

  @Override
  public TimeEntry find(long id) {
    return timeEntryMap.get(id);
  }

  @Override
  public List<TimeEntry> list() {
    return new ArrayList<>(timeEntryMap.values());
  }

  @Override
  public TimeEntry update(long id, TimeEntry timeEntry) {
    if (find(id) == null) {
      return null;
    }
    TimeEntry updatedEntry =
        new TimeEntry(
            id,
            timeEntry.getProjectId(),
            timeEntry.getUserId(),
            timeEntry.getDate(),
            timeEntry.getHours());
    timeEntryMap.replace(id, updatedEntry);
    return updatedEntry;
  }

  @Override
  public void delete(long id) {
    timeEntryMap.remove(id);
  }
}
