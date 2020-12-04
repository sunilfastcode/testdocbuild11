package com.fastcode.testdocbuild11.addons.scheduler;

import com.fastcode.testdocbuild11.addons.scheduler.domain.jobhistory.JobsListener;
import java.io.IOException;
import java.util.Properties;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@ConditionalOnProperty(name = "quartz.enabled")
public class QuartzSchedulerConfig {

    @Autowired
    private JobsListener jobsListener;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * create scheduler
     */

    @Bean
    public JobFactory jobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setQuartzProperties(quartzProperties());
        factory.setGlobalJobListeners(jobsListener);
        factory.setJobFactory(jobFactory());
        return factory;
    }

    /**
     * Configure quartz using properties file
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
