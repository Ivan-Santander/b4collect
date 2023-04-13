package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CyclingWheelRevolutionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CyclingWheelRevolution.class);
        CyclingWheelRevolution cyclingWheelRevolution1 = new CyclingWheelRevolution();
        cyclingWheelRevolution1.setId(UUID.randomUUID());
        CyclingWheelRevolution cyclingWheelRevolution2 = new CyclingWheelRevolution();
        cyclingWheelRevolution2.setId(cyclingWheelRevolution1.getId());
        assertThat(cyclingWheelRevolution1).isEqualTo(cyclingWheelRevolution2);
        cyclingWheelRevolution2.setId(UUID.randomUUID());
        assertThat(cyclingWheelRevolution1).isNotEqualTo(cyclingWheelRevolution2);
        cyclingWheelRevolution1.setId(null);
        assertThat(cyclingWheelRevolution1).isNotEqualTo(cyclingWheelRevolution2);
    }
}
