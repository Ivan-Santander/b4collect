package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HeartRateSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeartRateSummary.class);
        HeartRateSummary heartRateSummary1 = new HeartRateSummary();
        heartRateSummary1.setId(UUID.randomUUID());
        HeartRateSummary heartRateSummary2 = new HeartRateSummary();
        heartRateSummary2.setId(heartRateSummary1.getId());
        assertThat(heartRateSummary1).isEqualTo(heartRateSummary2);
        heartRateSummary2.setId(UUID.randomUUID());
        assertThat(heartRateSummary1).isNotEqualTo(heartRateSummary2);
        heartRateSummary1.setId(null);
        assertThat(heartRateSummary1).isNotEqualTo(heartRateSummary2);
    }
}
