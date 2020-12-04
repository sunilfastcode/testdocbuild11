package com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory;

import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("jobHistoryManager")
public class JobHistoryManager implements IJobHistoryManager {

    @Autowired
    @Qualifier("jobHistoryRepository")
    protected IJobHistoryRepository _jobRepository;

    @Transactional
    public JobHistoryEntity create(JobHistoryEntity job) {
        return _jobRepository.save(job);
    }

    @Transactional
    public List<JobHistoryEntity> findByJob(String jobName, String jobGroup) {
        return _jobRepository.findByJob(jobName, jobGroup);
    }

    @Transactional
    public List<JobHistoryEntity> findByTrigger(String triggerName, String triggerGroup) {
        return _jobRepository.findByTrigger(triggerName, triggerGroup);
    }

    @Transactional
    public Page<JobHistoryEntity> findAll(Predicate predicate, Pageable pageable) {
        return _jobRepository.findAll(predicate, pageable);
    }
}
