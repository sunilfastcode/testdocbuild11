package com.fastcode.testdocbuild11.addons.scheduler.domain.trigger;

import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("triggerManager")
public class TriggerManager implements ITriggerManager {

    @Autowired
    @Qualifier("triggerRepository")
    protected ITriggerRepository _triggerRepository;

    @Transactional
    public Page<TriggerEntity> findAll(Predicate predicate, Pageable pageable) {
        return _triggerRepository.findAll(predicate, pageable);
    }
}
