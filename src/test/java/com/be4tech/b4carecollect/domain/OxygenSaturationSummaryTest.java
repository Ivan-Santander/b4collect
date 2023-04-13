package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OxygenSaturationSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OxygenSaturationSummary.class);
        OxygenSaturationSummary oxygenSaturationSummary1 = new OxygenSaturationSummary();
        oxygenSaturationSummary1.setId(UUID.randomUUID());
        OxygenSaturationSummary oxygenSaturationSummary2 = new OxygenSaturationSummary();
        oxygenSaturationSummary2.setId(oxygenSaturationSummary1.getId());
        assertThat(oxygenSaturationSummary1).isEqualTo(oxygenSaturationSummary2);
        oxygenSaturationSummary2.setId(UUID.randomUUID());
        assertThat(oxygenSaturationSummary1).isNotEqualTo(oxygenSaturationSummary2);
        oxygenSaturationSummary1.setId(null);
        assertThat(oxygenSaturationSummary1).isNotEqualTo(oxygenSaturationSummary2);
    }
}
