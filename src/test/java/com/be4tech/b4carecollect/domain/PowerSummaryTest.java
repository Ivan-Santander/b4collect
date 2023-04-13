package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PowerSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PowerSummary.class);
        PowerSummary powerSummary1 = new PowerSummary();
        powerSummary1.setId(UUID.randomUUID());
        PowerSummary powerSummary2 = new PowerSummary();
        powerSummary2.setId(powerSummary1.getId());
        assertThat(powerSummary1).isEqualTo(powerSummary2);
        powerSummary2.setId(UUID.randomUUID());
        assertThat(powerSummary1).isNotEqualTo(powerSummary2);
        powerSummary1.setId(null);
        assertThat(powerSummary1).isNotEqualTo(powerSummary2);
    }
}
