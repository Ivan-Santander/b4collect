package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BodyFatPercentageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyFatPercentage.class);
        BodyFatPercentage bodyFatPercentage1 = new BodyFatPercentage();
        bodyFatPercentage1.setId(UUID.randomUUID());
        BodyFatPercentage bodyFatPercentage2 = new BodyFatPercentage();
        bodyFatPercentage2.setId(bodyFatPercentage1.getId());
        assertThat(bodyFatPercentage1).isEqualTo(bodyFatPercentage2);
        bodyFatPercentage2.setId(UUID.randomUUID());
        assertThat(bodyFatPercentage1).isNotEqualTo(bodyFatPercentage2);
        bodyFatPercentage1.setId(null);
        assertThat(bodyFatPercentage1).isNotEqualTo(bodyFatPercentage2);
    }
}
