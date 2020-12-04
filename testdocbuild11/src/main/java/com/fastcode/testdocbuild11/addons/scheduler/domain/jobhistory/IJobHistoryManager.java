package com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory;

import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJobHistoryManager {
    JobHistoryEntity create(JobHistoryEntity job);

    List<JobHistoryEntity> findByJob(String jobName, String jobGroup);

    List<JobHistoryEntity> findByTrigger(String triggerName, String triggerGroup);

    Page<JobHistoryEntity> findAll(Predicate predicate, Pageable pageable);
}
