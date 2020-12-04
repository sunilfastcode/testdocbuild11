package com.fastcode.testdocbuild11.addons.scheduler.application.trigger;

import com.fastcode.testdocbuild11.addons.scheduler.application.trigger.dto.*;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import java.util.List;
import org.quartz.SchedulerException;
import org.springframework.data.domain.Pageable;

public interface ITriggerAppService {
    boolean updateTrigger(UpdateTriggerInput obj) throws SchedulerException;

    boolean cancelTrigger(String triggerName, String triggerGroup) throws SchedulerException;

    boolean pauseTrigger(String triggerName, String triggerGroup) throws SchedulerException;

    boolean resumeTrigger(String triggerName, String triggerGroup) throws SchedulerException;

    boolean createTrigger(CreateTriggerInput obj) throws SchedulerException;

    GetTriggerOutput returnTrigger(String triggerName, String triggerGroup) throws SchedulerException;

    List<String> listAllTriggerGroups() throws SchedulerException;

    List<GetTriggerOutput> listAllTriggers(SearchCriteria search, Pageable pageable) throws Exception;

    List<GetJobOutput> executionHistoryByTrigger(String triggerName, String triggerGroup);
}
