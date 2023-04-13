package com.be4tech.b4carecollect.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.be4tech.b4carecollect.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.be4tech.b4carecollect.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.be4tech.b4carecollect.domain.User.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.Authority.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.User.class.getName() + ".authorities");
            createCache(cm, com.be4tech.b4carecollect.domain.CaloriesBMR.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.CaloriesExpended.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.CiclingPedalingCadence.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.HeartMinutes.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.ActiveMinutes.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.PoweSample.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.StepCountCadence.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.StepCountDelta.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.ActivityExercise.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.CyclingWheelRevolution.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.DistanceDelta.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.LocationSample.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.Speed.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.Nutrition.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BloodGlucose.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BloodPressure.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BodyFatPercentage.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BodyTemperature.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.HeartRateBpm.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.Height.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.OxygenSaturation.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.SleepSegment.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.Weight.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.ActivitySummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.CaloriesBmrSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.PowerSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BodyFatPercentageSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.HeartRateSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.HeightSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.WeightSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.SpeedSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.NutritionSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BloodGlucoseSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.BloodPressureSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.TemperatureSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.OxygenSaturationSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.FuntionalIndex.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.UserDemographics.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.UserMedicalInfo.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.UserBodyInfo.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.MentalHealth.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.AlarmRiskIndexSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.SleepScores.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.FuntionalIndexSummary.class.getName());
            createCache(cm, com.be4tech.b4carecollect.domain.MentalHealthSummary.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
