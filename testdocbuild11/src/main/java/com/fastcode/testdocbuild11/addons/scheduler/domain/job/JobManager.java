package com.fastcode.testdocbuild11.addons.scheduler.domain.job;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("jobManager")
public class JobManager implements IJobManager {

    @Autowired
    @Qualifier("jobRepository")
    protected IJobRepository _jobRepository;

    @Transactional
    public Page<JobEntity> findAll(Predicate predicate, Pageable pageable) {
        return _jobRepository.findAll(predicate, pageable);
    }
}
