package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StepCountCadenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StepCountCadence.class);
        StepCountCadence stepCountCadence1 = new StepCountCadence();
        stepCountCadence1.setId(UUID.randomUUID());
        StepCountCadence stepCountCadence2 = new StepCountCadence();
        stepCountCadence2.setId(stepCountCadence1.getId());
        assertThat(stepCountCadence1).isEqualTo(stepCountCadence2);
        stepCountCadence2.setId(UUID.randomUUID());
        assertThat(stepCountCadence1).isNotEqualTo(stepCountCadence2);
        stepCountCadence1.setId(null);
        assertThat(stepCountCadence1).isNotEqualTo(stepCountCadence2);
    }
}
