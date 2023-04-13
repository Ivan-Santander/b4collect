package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BloodGlucoseSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodGlucoseSummary.class);
        BloodGlucoseSummary bloodGlucoseSummary1 = new BloodGlucoseSummary();
        bloodGlucoseSummary1.setId(UUID.randomUUID());
        BloodGlucoseSummary bloodGlucoseSummary2 = new BloodGlucoseSummary();
        bloodGlucoseSummary2.setId(bloodGlucoseSummary1.getId());
        assertThat(bloodGlucoseSummary1).isEqualTo(bloodGlucoseSummary2);
        bloodGlucoseSummary2.setId(UUID.randomUUID());
        assertThat(bloodGlucoseSummary1).isNotEqualTo(bloodGlucoseSummary2);
        bloodGlucoseSummary1.setId(null);
        assertThat(bloodGlucoseSummary1).isNotEqualTo(bloodGlucoseSummary2);
    }
}
