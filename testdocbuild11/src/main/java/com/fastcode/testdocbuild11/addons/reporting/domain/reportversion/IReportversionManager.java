package com.fastcode.testdocbuild11.addons.reporting.domain.reportversion;

import com.fastcode.testdocbuild11.domain.core.authorization.user.UserEntity;
import com.querydsl.core.types.Predicate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReportversionManager {
    ReportversionEntity create(ReportversionEntity reportversion);

    void delete(ReportversionEntity reportversion);

    ReportversionEntity update(ReportversionEntity reportversion);

    ReportversionEntity findById(ReportversionId reportversionId);

    Page<ReportversionEntity> findAll(Predicate predicate, Pageable pageable);

    List<ReportversionEntity> findByUserId(Long userId);

    UserEntity getUser(ReportversionId reportversionId);
}
