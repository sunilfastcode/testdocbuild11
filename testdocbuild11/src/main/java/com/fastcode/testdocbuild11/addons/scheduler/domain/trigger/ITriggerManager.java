package com.fastcode.testdocbuild11.addons.scheduler.domain.trigger;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITriggerManager {
    Page<TriggerEntity> findAll(Predicate predicate, Pageable pageable);
}
