package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class StepCountDeltaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StepCountDelta.class);
        StepCountDelta stepCountDelta1 = new StepCountDelta();
        stepCountDelta1.setId(UUID.randomUUID());
        StepCountDelta stepCountDelta2 = new StepCountDelta();
        stepCountDelta2.setId(stepCountDelta1.getId());
        assertThat(stepCountDelta1).isEqualTo(stepCountDelta2);
        stepCountDelta2.setId(UUID.randomUUID());
        assertThat(stepCountDelta1).isNotEqualTo(stepCountDelta2);
        stepCountDelta1.setId(null);
        assertThat(stepCountDelta1).isNotEqualTo(stepCountDelta2);
    }
}
