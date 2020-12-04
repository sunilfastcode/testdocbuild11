package com.fastcode.testdocbuild11.addons.scheduler.domain.job;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IJobManager {
    Page<JobEntity> findAll(Predicate predicate, Pageable pageable);
}
