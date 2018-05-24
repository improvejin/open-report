package com.pplove.bip.scheduler.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    @Caching(put={@CachePut(value = "reportsStatus", key = "#result.getId()")})
    public ScheduleInfo updateScheduleStatus(String report, int date, ScheduleStatus status, String jobId) {
        ScheduleId id = new ScheduleId(report, date);
        ScheduleInfo info = new ScheduleInfo(id, status, jobId);
        return  repository.save(info);
    }

    @Cacheable("reportsStatus")
    public ScheduleInfo getScheduleInfo(ScheduleId id) {
        return repository.findOne(id);
    }

    public ScheduleStatus getScheduleStatus(String report, int date) {
        ScheduleId id  = new ScheduleId(report, date);
        ScheduleInfo info  = getScheduleInfo(id);
        return info.getStatus();
    }

    public List<ScheduleInfo> getAllScheduleInfo() {
        List<ScheduleInfo> infos = new ArrayList<>();
        Iterable<ScheduleInfo> it = repository.findAll();
        it.forEach(info -> infos.add(info));
        return infos;
    }
}
