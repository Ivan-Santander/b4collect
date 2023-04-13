package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BloodGlucoseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodGlucose.class);
        BloodGlucose bloodGlucose1 = new BloodGlucose();
        bloodGlucose1.setId(UUID.randomUUID());
        BloodGlucose bloodGlucose2 = new BloodGlucose();
        bloodGlucose2.setId(bloodGlucose1.getId());
        assertThat(bloodGlucose1).isEqualTo(bloodGlucose2);
        bloodGlucose2.setId(UUID.randomUUID());
        assertThat(bloodGlucose1).isNotEqualTo(bloodGlucose2);
        bloodGlucose1.setId(null);
        assertThat(bloodGlucose1).isNotEqualTo(bloodGlucose2);
    }
}
