package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SpeedSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpeedSummary.class);
        SpeedSummary speedSummary1 = new SpeedSummary();
        speedSummary1.setId(UUID.randomUUID());
        SpeedSummary speedSummary2 = new SpeedSummary();
        speedSummary2.setId(speedSummary1.getId());
        assertThat(speedSummary1).isEqualTo(speedSummary2);
        speedSummary2.setId(UUID.randomUUID());
        assertThat(speedSummary1).isNotEqualTo(speedSummary2);
        speedSummary1.setId(null);
        assertThat(speedSummary1).isNotEqualTo(speedSummary2);
    }
}
