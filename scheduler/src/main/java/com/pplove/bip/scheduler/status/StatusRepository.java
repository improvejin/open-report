package com.pplove.bip.scheduler.status;


import org.springframework.data.repository.CrudRepository;


/**
 * Created by jiatingjin on 2018/1/19.
 */
public interface StatusRepository extends CrudRepository<ScheduleInfo, ScheduleId> {

    ScheduleInfo save(ScheduleInfo info);

    ScheduleInfo findOne(ScheduleId id);

    Iterable<ScheduleInfo> findAll();
}
