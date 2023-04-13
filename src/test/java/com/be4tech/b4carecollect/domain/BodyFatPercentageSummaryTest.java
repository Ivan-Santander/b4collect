package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BodyFatPercentageSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyFatPercentageSummary.class);
        BodyFatPercentageSummary bodyFatPercentageSummary1 = new BodyFatPercentageSummary();
        bodyFatPercentageSummary1.setId(UUID.randomUUID());
        BodyFatPercentageSummary bodyFatPercentageSummary2 = new BodyFatPercentageSummary();
        bodyFatPercentageSummary2.setId(bodyFatPercentageSummary1.getId());
        assertThat(bodyFatPercentageSummary1).isEqualTo(bodyFatPercentageSummary2);
        bodyFatPercentageSummary2.setId(UUID.randomUUID());
        assertThat(bodyFatPercentageSummary1).isNotEqualTo(bodyFatPercentageSummary2);
        bodyFatPercentageSummary1.setId(null);
        assertThat(bodyFatPercentageSummary1).isNotEqualTo(bodyFatPercentageSummary2);
    }
}
