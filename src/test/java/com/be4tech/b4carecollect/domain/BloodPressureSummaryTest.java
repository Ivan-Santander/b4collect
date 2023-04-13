package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BloodPressureSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodPressureSummary.class);
        BloodPressureSummary bloodPressureSummary1 = new BloodPressureSummary();
        bloodPressureSummary1.setId(UUID.randomUUID());
        BloodPressureSummary bloodPressureSummary2 = new BloodPressureSummary();
        bloodPressureSummary2.setId(bloodPressureSummary1.getId());
        assertThat(bloodPressureSummary1).isEqualTo(bloodPressureSummary2);
        bloodPressureSummary2.setId(UUID.randomUUID());
        assertThat(bloodPressureSummary1).isNotEqualTo(bloodPressureSummary2);
        bloodPressureSummary1.setId(null);
        assertThat(bloodPressureSummary1).isNotEqualTo(bloodPressureSummary2);
    }
}
