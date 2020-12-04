package com.fastcode.testdocbuild11.addons.scheduler.application.job;

import com.fastcode.testdocbuild11.addons.scheduler.CGenClassLoader;
import com.fastcode.testdocbuild11.addons.scheduler.application.job.dto.*;
import com.fastcode.testdocbuild11.addons.scheduler.constants.QuartzConstants;
import com.fastcode.testdocbuild11.addons.scheduler.domain.job.IJobManager;
import com.fastcode.testdocbuild11.addons.scheduler.domain.job.JobEntity;
import com.fastcode.testdocbuild11.addons.scheduler.domain.job.QJobEntity;
import com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory.IJobHistoryManager;
import com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory.JobHistoryEntity;
import com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory.QJobHistoryEntity;
import com.fastcode.testdocbuild11.commons.logging.LoggingHelper;
import com.fastcode.testdocbuild11.commons.search.SearchCriteria;
import com.fastcode.testdocbuild11.commons.search.SearchFields;
import com.google.gson.Gson;
import com.querydsl.core.BooleanBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class JobAppService implements IJobAppService {

    static final int case1 = 1;
    static final int case2 = 2;
    static final int case3 = 3;

    @Autowired
    protected Environment env;

    @Autowired
    protected LoggingHelper logHelper;

    @Autowired
    protected SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    @Qualifier("jobHistoryManager")
    protected IJobHistoryManager jobHistoryManager;

    @Autowired
    @Qualifier("jobManager")
    protected IJobManager jobDetailsManager;

    protected Scheduler scheduler;

    protected QuartzConstants quartzConstant;

    public Scheduler getScheduler() {
        scheduler = schedulerFactoryBean.getScheduler();
        //scheduler.start();
        return scheduler;
    }

    //List all jobs
    public List<JobListOutput> listAllJobs(SearchCriteria search, Pageable pageable) throws Exception {
        List<JobListOutput> list = new ArrayList<JobListOutput>();
        //Get Jobs
        Page<JobEntity> foundJobs = jobDetailsManager.findAll(searchJobDetails(search), pageable);
        List<JobEntity> jobList = foundJobs.getContent();

        for (int i = 0; i < jobList.size(); i++) {
            JobListOutput obj = new JobListOutput();

            obj.setJobName(jobList.get(i).getJobName());
            obj.setJobGroup(jobList.get(i).getJobGroup());
            obj.setJobClass(jobList.get(i).getJobClassName());
            obj.setJobDescription(jobList.get(i).getDescription());

            if (isJobRunning(jobList.get(i).getJobName(), jobList.get(i).getJobGroup())) obj.setJobStatus(
                quartzConstant.JOB_STATUS_RUNNING
            ); else obj.setJobStatus(getJobState(jobList.get(i).getJobName(), jobList.get(i).getJobGroup()));

            list.add(obj);
        }
        return list;
    }

    //List Triggers Against Job Key
    public List<FindByTriggerOutput> returnTriggersForAJob(JobKey jobKey) throws SchedulerException {
        List<FindByTriggerOutput> triggerDetails = new ArrayList<FindByTriggerOutput>();

        if (getScheduler().checkExists(jobKey)) {
            List<Trigger> triggers = (List<Trigger>) getScheduler().getTriggersOfJob(jobKey);
            for (int i = 0; i < triggers.size(); i++) {
                triggerDetails.add(returnTriggerDetails(triggers.get(i)));
            }
        } else {
            logHelper
                .getLogger()
                .error(
                    "There does not exist a job with a jobName=" +
                    jobKey.getName() +
                    " and jobGroup=" +
                    jobKey.getGroup()
                );
            return null;
        }

        return triggerDetails;
    }

    public BooleanBuilder searchJobDetails(SearchCriteria search) throws Exception {
        QJobEntity job = QJobEntity.jobEntity;
        if (search != null) {
            if (search.getType() == case1) {
                return searchAllPropertiesForJobDetails(job, search.getValue(), search.getOperator());
            } else if (search.getType() == case2) {
                List<String> keysList = new ArrayList<String>();
                for (SearchFields f : search.getFields()) {
                    keysList.add(f.getFieldName());
                }
                checkPropertiesForJobDetails(keysList);
                return searchSpecificPropertyForJobDetails(job, keysList, search.getValue(), search.getOperator());
            } else if (search.getType() == case3) {
                Map<String, SearchFields> map = new HashMap<>();
                for (SearchFields fieldDetails : search.getFields()) {
                    map.put(fieldDetails.getFieldName(), fieldDetails);
                }
                List<String> keysList = new ArrayList<String>(map.keySet());
                checkPropertiesForJobDetails(keysList);
                return searchKeyValuePairForJobDetails(job, map);
            }
        }
        return null;
    }

    public BooleanBuilder searchAllPropertiesForJobDetails(QJobEntity jobDetails, String value, String operator) {
        BooleanBuilder builder = new BooleanBuilder();

        if (operator.equals("contains")) {
            builder.or(jobDetails.jobName.likeIgnoreCase("%" + value + "%"));
            builder.or(jobDetails.jobGroup.likeIgnoreCase("%" + value + "%"));
            builder.or(jobDetails.jobClassName.likeIgnoreCase("%" + value + "%"));
        } else if (operator.equals("equals")) {
            builder.or(jobDetails.jobName.eq(value));
            builder.or(jobDetails.jobGroup.eq(value));
            builder.or(jobDetails.jobClassName.eq(value));
        }

        return builder;
    }

    public void checkPropertiesForJobDetails(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    (list.get(i).replace("%20", "").trim().equals("jobName")) ||
                    (list.get(i).replace("%20", "").trim().equals("jobGroup")) ||
                    (list.get(i).replace("%20", "").trim().equals("jobClassName"))
                )
            ) {
                // Throw an exception
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    public BooleanBuilder searchSpecificPropertyForJobDetails(
        QJobEntity jobDetails,
        List<String> list,
        String value,
        String operator
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).replace("%20", "").trim().equals("jobName")) {
                if (operator.equals("contains")) {
                    builder.or(jobDetails.jobName.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobDetails.jobName.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("jobGroup")) {
                if (operator.equals("contains")) {
                    builder.or(jobDetails.jobGroup.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobDetails.jobGroup.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("jobClassName")) {
                if (operator.equals("contains")) {
                    builder.or(jobDetails.jobClassName.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobDetails.jobClassName.eq(value));
                }
            }
        }
        return builder;
    }

    public BooleanBuilder searchKeyValuePairForJobDetails(QJobEntity jobDetails, Map<String, SearchFields> map) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("jobName")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(jobDetails.jobName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%"));
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobDetails.jobName.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobDetails.jobName.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("jobGroup")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(jobDetails.jobGroup.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%"));
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobDetails.jobGroup.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobDetails.jobGroup.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("jobClassName")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(
                        jobDetails.jobClassName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                    );
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobDetails.jobClassName.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobDetails.jobClassName.ne(details.getValue().getSearchValue()));
                }
            }
        }
        return builder;
    }

    public List<GetExecutingJob> currentlyExecutingJobs() throws SchedulerException {
        List<GetExecutingJob> jobsList = new ArrayList<GetExecutingJob>();
        List<JobExecutionContext> executingJobs = getScheduler().getCurrentlyExecutingJobs();

        for (int i = 0; i < executingJobs.size(); i++) {
            GetExecutingJob job = new GetExecutingJob();
            FindByJobOutput jobDetail = returnJobDetails(executingJobs.get(i).getJobDetail().getKey());
            job.setJobName(jobDetail.getJobName());
            job.setJobGroup(jobDetail.getJobGroup());
            job.setJobDescription(jobDetail.getJobDescription());
            job.setJobClass(jobDetail.getJobClass());
            job.setJobMapData(jobDetail.getJobMapData());
            job.setTriggerName(executingJobs.get(i).getTrigger().getKey().getName());
            job.setTriggerGroup(executingJobs.get(i).getTrigger().getKey().getGroup());
            job.setFiredTime(executingJobs.get(i).getFireTime());
            job.setNextExecutionTime(executingJobs.get(i).getNextFireTime());

            if (!jobsList.contains(job)) {
                jobsList.add(job);
            }
        }

        return jobsList;
    }

    //List All Job Classes
    public List<String> listAllJobClasses() {
        String path = System.getProperty("user.dir");
        System.out.println(" path " + System.getProperty("user.dir"));
        String packageName = env.getProperty("fastCode.jobs.default");
        System.out.println(" package  " + packageName);
        CGenClassLoader loader = new CGenClassLoader(path.replace('\\', '/') + "/target/classes");
        ArrayList<Class<?>> jobClasses = null;
        try {
            jobClasses = loader.findClasses(packageName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<String> classNameList = new ArrayList<String>();

        for (Class<?> classObj : jobClasses) {
            classNameList.add(classObj.getName());
        }

        return classNameList;
    }

    //List All Job Groups
    public List<String> listAllJobGroups() throws SchedulerException {
        List<String> groupsList = getScheduler().getJobGroupNames();
        return groupsList;
    }

    // Pause a job
    public boolean pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);

        if (getScheduler().checkExists(jobKey)) {
            getScheduler().pauseJob(jobKey);
            return true;
        } else {
            logHelper
                .getLogger()
                .error("There does not exist a job with a jobName=" + jobName + " and jobGroup=" + jobGroup);
            return false;
        }
    }

    //Resume a job
    public boolean resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (getScheduler().checkExists(jobKey)) {
            getScheduler().resumeJob(jobKey);
            return true;
        } else {
            logHelper
                .getLogger()
                .error("There does not exist a job with a jobName=" + jobName + " and jobGroup=" + jobGroup);
            return false;
        }
    }

    //Delete a job
    public boolean deleteJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (getScheduler().checkExists(jobKey)) {
            getScheduler().deleteJob(jobKey);
            return true;
        } else {
            logHelper
                .getLogger()
                .error("There does not exist a job with a jobName=" + jobName + " and jobGroup=" + jobGroup);
            return false;
        }
    }

    //Return a job
    public FindByJobOutput returnJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);

        if (getScheduler().checkExists(jobKey)) {
            FindByJobOutput jobDetails = returnJobDetails(jobKey);
            List<Trigger> triggers = (List<Trigger>) getScheduler().getTriggersOfJob(jobKey);
            List<FindByTriggerOutput> triggerDetails = new ArrayList<FindByTriggerOutput>();
            for (int i = 0; i < triggers.size(); i++) {
                System.out.println("sizee ");
                triggerDetails.add(returnTriggerDetails(triggers.get(i)));
            }
            jobDetails.setTriggerDetails(triggerDetails);
            return jobDetails;
        } else {
            logHelper
                .getLogger()
                .error("There does not exist a job with a jobName=" + jobName + " and jobGroup=" + jobGroup);
            return null;
        }
    }

    protected FindByTriggerOutput returnTriggerDetails(Trigger trigger) throws SchedulerException {
        TriggerKey triggKey = trigger.getKey();
        String trigName = triggKey.getName();
        String trigGroup = triggKey.getGroup();
        String trigType = quartzConstant.SIMPLE_TRIGGER;
        if (trigger.getClass().toString().equals(quartzConstant.CRON_TRIGGER_CLASS)) trigType =
            quartzConstant.CRON_TRIGGER;

        Date nextFireTime = trigger.getNextFireTime();
        Date endTime = trigger.getEndTime();
        Date fireTime = trigger.getStartTime();
        Date lastFireTime = trigger.getPreviousFireTime();

        String triggerState = getScheduler().getTriggerState(triggKey).toString();

        String triggerDescription = getScheduler().getTrigger(triggKey).getDescription();

        String[] triggerKeyValues = getScheduler().getTrigger(triggKey).getJobDataMap().getKeys();
        Map<String, String> triggerMap = new HashMap<String, String>();
        for (int j = 0; j < triggerKeyValues.length; j++) {
            String str = getScheduler().getTrigger(triggKey).getJobDataMap().getString(triggerKeyValues[j]);
            triggerMap.put(triggerKeyValues[j], str);
        }

        FindByTriggerOutput triggDetails = new FindByTriggerOutput(
            trigName,
            trigGroup,
            triggerState,
            trigType,
            triggerDescription,
            triggerMap,
            fireTime,
            endTime,
            lastFireTime,
            nextFireTime
        );

        return triggDetails;
    }

    //Update a job
    public boolean updateJob(UpdateJobInput obj) throws SchedulerException {
        JobKey jobKey = new JobKey(obj.getJobName(), obj.getJobGroup());
        if (getScheduler().checkExists(jobKey)) {
            JobDetail jobDetails = getScheduler()
                .getJobDetail(jobKey)
                .getJobBuilder()
                .storeDurably(obj.getIsDurable())
                .withDescription(obj.getJobDescription())
                .build();

            getScheduler().addJob(setJobMapData(jobDetails, obj.getJobMapData()), true, true);
            return true;
        } else {
            logHelper
                .getLogger()
                .error(
                    "There does not exist a job with a jobName=" +
                    obj.getJobName() +
                    " and jobGroup=" +
                    obj.getJobGroup()
                );
            return false;
        }
    }

    // Create a job
    public boolean createJob(CreateJobInput obj) throws SchedulerException, ClassNotFoundException {
        if (!(getScheduler().checkExists(new JobKey(obj.getJobName(), obj.getJobGroup())))) {
            Class<? extends Job> className = (Class<? extends Job>) Class.forName(obj.getJobClass());
            JobDetail jobDetails = JobBuilder
                .newJob(className)
                .storeDurably(obj.getIsDurable())
                .withDescription(obj.getJobDescription())
                .withIdentity(obj.getJobName(), obj.getJobGroup())
                .build();
            getScheduler().addJob(setJobMapData(jobDetails, obj.getJobMapData()), true, true);
            return true;
        } else {
            logHelper
                .getLogger()
                .error(
                    "There already exists a job with a jobName=" + obj.getJobName(),
                    " and jobGroup=" + obj.getJobGroup()
                );
            return false;
        }
    }

    protected JobDetail setJobMapData(JobDetail jobDetails, Map<String, String> mapData) {
        if (mapData != null) {
            Set set = mapData.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                jobDetails.getJobDataMap().put(mentry.getKey().toString(), mentry.getValue().toString());
            }
        }

        return jobDetails;
    }

    protected Long dateStringToMillis(String str) {
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date;
            try {
                date = formatter.parse(str);
                long mills = date.getTime();
                return mills;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    protected Date stringToDate(String str) {
        if (str != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date;
            try {
                date = formatter.parse(str);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    protected Date millisToDate(long durationInMillis) throws ParseException {
        if (durationInMillis > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            //SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
            //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String formattedDate = formatter.format(new Date(durationInMillis));
            Date date = formatter.parse(formattedDate);
            return date;
        } else return null;
    }

    //Execution History By Job
    public List<GetJobOutput> executionHistoryByJob(String jobName, String jobGroup) {
        List<JobHistoryEntity> jobList = jobHistoryManager.findByJob(jobName, jobGroup);
        return returnJobOutput(jobList);
    }

    //Execution History
    public List<GetJobOutput> executionHistory(SearchCriteria search, Pageable pageable) throws Exception {
        Page<JobHistoryEntity> foundJobs = jobHistoryManager.findAll(searchExecutionHistory(search), pageable);
        List<JobHistoryEntity> jobList = foundJobs.getContent();
        return returnJobOutput(jobList);
    }

    protected BooleanBuilder searchExecutionHistory(SearchCriteria search) throws Exception {
        QJobHistoryEntity jobHistory = QJobHistoryEntity.jobHistoryEntity;
        if (search != null) {
            if (search.getType() == case1) {
                return searchAllPropertiesForJobHistory(jobHistory, search.getValue(), search.getOperator());
            } else if (search.getType() == case2) {
                List<String> keysList = new ArrayList<String>();
                for (SearchFields f : search.getFields()) {
                    keysList.add(f.getFieldName());
                }
                checkPropertiesForJobHistory(keysList);
                return searchSpecificPropertyForJobHistory(
                    jobHistory,
                    keysList,
                    search.getValue(),
                    search.getOperator()
                );
            } else if (search.getType() == case3) {
                Map<String, SearchFields> map = new HashMap<>();
                for (SearchFields fieldDetails : search.getFields()) {
                    map.put(fieldDetails.getFieldName(), fieldDetails);
                }
                List<String> keysList = new ArrayList<String>(map.keySet());
                checkPropertiesForJobHistory(keysList);
                return searchKeyValuePairForJobHistory(jobHistory, map);
            }
        }
        return null;
    }

    protected BooleanBuilder searchAllPropertiesForJobHistory(
        QJobHistoryEntity jobHistory,
        String value,
        String operator
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (operator.equals("contains")) {
            builder.or(jobHistory.jobName.likeIgnoreCase("%" + value + "%"));
            builder.or(jobHistory.jobGroup.likeIgnoreCase("%" + value + "%"));
            builder.or(jobHistory.triggerGroup.likeIgnoreCase("%" + value + "%"));
            builder.or(jobHistory.triggerName.likeIgnoreCase("%" + value + "%"));
        } else if (operator.equals("equals")) {
            builder.or(jobHistory.jobName.eq(value));
            builder.or(jobHistory.jobGroup.eq(value));
            builder.or(jobHistory.triggerGroup.eq(value));
            builder.or(jobHistory.triggerName.eq(value));

            Date date = stringToDate(value);
            if (date != null) {
                builder.or(jobHistory.firedTime.eq(date));
                builder.or(jobHistory.finishedTime.eq(date));
            }
        }

        return builder;
    }

    protected void checkPropertiesForJobHistory(List<String> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            if (
                !(
                    (list.get(i).replace("%20", "").trim().equals("jobName")) ||
                    (list.get(i).replace("%20", "").trim().equals("jobGroup")) ||
                    (list.get(i).replace("%20", "").trim().equals("triggerName")) ||
                    (list.get(i).replace("%20", "").trim().equals("triggerGroup")) ||
                    (list.get(i).replace("%20", "").trim().equals("firedTime")) ||
                    (list.get(i).replace("%20", "").trim().equals("finishedTime"))
                )
            ) {
                // Throw an exception
                throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!");
            }
        }
    }

    protected BooleanBuilder searchSpecificPropertyForJobHistory(
        QJobHistoryEntity jobHistory,
        List<String> list,
        String value,
        String operator
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).replace("%20", "").trim().equals("jobName")) {
                if (operator.equals("contains")) {
                    builder.or(jobHistory.jobName.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobHistory.jobName.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("jobGroup")) {
                if (operator.equals("contains")) {
                    builder.or(jobHistory.jobGroup.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobHistory.jobGroup.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("triggerName")) {
                if (operator.equals("contains")) {
                    builder.or(jobHistory.triggerName.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobHistory.triggerName.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("triggerGroup")) {
                if (operator.equals("contains")) {
                    builder.or(jobHistory.triggerGroup.likeIgnoreCase("%" + value + "%"));
                } else if (operator.equals("equals")) {
                    builder.or(jobHistory.triggerGroup.eq(value));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("firedTime")) {
                Date date = stringToDate(value);
                if (operator.equals("equals") && date != null) {
                    builder.or(jobHistory.firedTime.eq(date));
                }
            }
            if (list.get(i).replace("%20", "").trim().equals("finishedTime")) {
                Date date = stringToDate(value);
                if (operator.equals("equals") && date != null) {
                    builder.or(jobHistory.finishedTime.eq(date));
                }
            }
        }
        return builder;
    }

    protected BooleanBuilder searchKeyValuePairForJobHistory(
        QJobHistoryEntity jobHistory,
        Map<String, SearchFields> map
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if (details.getKey().replace("%20", "").trim().equals("jobName")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(jobHistory.jobName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%"));
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobHistory.jobName.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobHistory.jobName.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("jobGroup")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(jobHistory.jobGroup.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%"));
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobHistory.jobGroup.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobHistory.jobGroup.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("triggerName")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(jobHistory.triggerName.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%"));
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobHistory.triggerName.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobHistory.triggerName.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("triggerGroup")) {
                if (details.getValue().getOperator().equals("contains")) {
                    builder.and(
                        jobHistory.triggerGroup.likeIgnoreCase("%" + details.getValue().getSearchValue() + "%")
                    );
                } else if (details.getValue().getOperator().equals("equals")) {
                    builder.and(jobHistory.triggerGroup.eq(details.getValue().getSearchValue()));
                } else if (details.getValue().getOperator().equals("notEqual")) {
                    builder.and(jobHistory.triggerGroup.ne(details.getValue().getSearchValue()));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("firedTime")) {
                Date date = stringToDate(details.getValue().getSearchValue());
                if (details.getValue().getOperator().equals("equals") && date != null) builder.and(
                    jobHistory.firedTime.eq(date)
                ); else if (details.getValue().getOperator().equals("notEqual") && date != null) builder.and(
                    jobHistory.firedTime.ne(date)
                ); else if (details.getValue().getOperator().equals("range")) {
                    Date startDate = stringToDate(details.getValue().getStartingValue());
                    Date endDate = stringToDate(details.getValue().getEndingValue());
                    if (startDate != null && endDate != null) builder.and(
                        jobHistory.firedTime.between(startDate, endDate)
                    ); else if (endDate != null) builder.and(jobHistory.firedTime.loe(endDate)); else if (
                        startDate != null
                    ) builder.and(jobHistory.firedTime.goe(startDate));
                }
            }
            if (details.getKey().replace("%20", "").trim().equals("finishedTime")) {
                Date date = stringToDate(details.getValue().getSearchValue());
                if (details.getValue().getOperator().equals("equals") && date != null) builder.and(
                    jobHistory.finishedTime.eq(date)
                ); else if (details.getValue().getOperator().equals("notEqual") && date != null) builder.and(
                    jobHistory.finishedTime.ne(date)
                ); else if (details.getValue().getOperator().equals("range")) {
                    Date startDate = stringToDate(details.getValue().getStartingValue());
                    Date endDate = stringToDate(details.getValue().getEndingValue());
                    if (startDate != null && endDate != null) builder.and(
                        jobHistory.finishedTime.between(startDate, endDate)
                    ); else if (endDate != null) builder.and(jobHistory.finishedTime.loe(endDate)); else if (
                        startDate != null
                    ) builder.and(jobHistory.finishedTime.goe(startDate));
                }
            }
        }
        return builder;
    }

    protected FindByJobOutput returnJobDetails(JobKey jobKey) throws SchedulerException {
        Class<? extends Job> jobClassObject = getScheduler().getJobDetail(jobKey).getJobClass();
        String jobClass = jobClassObject.toString();
        jobClass = jobClass.substring(jobClass.indexOf(' ') + 1);
        String jobStatus = null;
        String jobDescription = getScheduler().getJobDetail(jobKey).getDescription();
        boolean isDurable = getScheduler().getJobDetail(jobKey).isDurable();

        if (isJobRunning(jobKey.getName(), jobKey.getGroup())) jobStatus =
            quartzConstant.JOB_STATUS_RUNNING; else jobStatus = getJobState(jobKey.getName(), jobKey.getGroup());

        String[] jobKeyValues = getScheduler().getJobDetail(jobKey).getJobDataMap().getKeys();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < jobKeyValues.length; i++) {
            String st = getScheduler().getJobDetail(jobKey).getJobDataMap().getString(jobKeyValues[i]);
            map.put(jobKeyValues[i], st);
        }

        FindByJobOutput jobDetails = new FindByJobOutput(
            jobKey.getName(),
            jobKey.getGroup(),
            jobClass,
            jobDescription,
            map,
            null,
            isDurable,
            jobStatus
        );
        return jobDetails;
    }

    protected boolean isJobRunning(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobkey = new JobKey(jobName, jobGroup);

        List<JobExecutionContext> executingJobs = getScheduler().getCurrentlyExecutingJobs();
        for (int i = 0; i < executingJobs.size(); i++) {
            JobKey executingJobKey = executingJobs.get(i).getJobDetail().getKey();
            if (executingJobKey.getName().equals(jobName) && executingJobKey.getGroup().equals(jobGroup)) {
                return true;
            }
        }

        return false;
    }

    protected String getJobState(String jobName, String jobGroup) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroup);
            JobDetail jobDetail = getScheduler().getJobDetail(jobKey);
            int pausedTriggersLength = 0;

            List<? extends Trigger> triggers = getScheduler().getTriggersOfJob(jobDetail.getKey());
            if (triggers != null && triggers.size() > 0) {
                for (Trigger trigger : triggers) {
                    TriggerState triggerState = getScheduler().getTriggerState(trigger.getKey());

                    if (TriggerState.PAUSED.equals(triggerState)) {
                        pausedTriggersLength++;
                    }
                }
            }

            if (triggers.size() > 0 && pausedTriggersLength == triggers.size()) {
                return "PAUSED";
            }
        } catch (SchedulerException e) {
            logHelper.getLogger().error("SchedulerException while fetching all jobs. error message :" + e.getMessage());
            e.printStackTrace();
        }
        return "ACTIVE";
    }

    protected List<GetJobOutput> returnJobOutput(List<JobHistoryEntity> jobList) {
        List<GetJobOutput> list = new ArrayList<GetJobOutput>();

        for (int i = 0; i < jobList.size(); i++) {
            GetJobOutput obj = new GetJobOutput();
            obj.setId(jobList.get(i).getId());
            obj.setJobName(jobList.get(i).getJobName());
            obj.setJobGroup(jobList.get(i).getJobGroup());
            obj.setJobClass(jobList.get(i).getJobClass());
            obj.setTriggerName(jobList.get(i).getTriggerName());
            obj.setTriggerGroup(jobList.get(i).getTriggerGroup());
            obj.setJobStatus(jobList.get(i).getJobStatus());
            obj.setJobDescription(jobList.get(i).getJobDescription());
            obj.setFiredTime(jobList.get(i).getFiredTime());
            obj.setFinishedTime(jobList.get(i).getFinishedTime());
            obj.setDuration(jobList.get(i).getDuration());

            String mapData = jobList.get(i).getJobMapData();
            Map<String, String> result = new Gson().fromJson(mapData, HashMap.class);
            obj.setJobMapData(result);

            list.add(obj);
        }

        return list;
    }
}
