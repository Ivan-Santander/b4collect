package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HeightSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeightSummary.class);
        HeightSummary heightSummary1 = new HeightSummary();
        heightSummary1.setId(UUID.randomUUID());
        HeightSummary heightSummary2 = new HeightSummary();
        heightSummary2.setId(heightSummary1.getId());
        assertThat(heightSummary1).isEqualTo(heightSummary2);
        heightSummary2.setId(UUID.randomUUID());
        assertThat(heightSummary1).isNotEqualTo(heightSummary2);
        heightSummary1.setId(null);
        assertThat(heightSummary1).isNotEqualTo(heightSummary2);
    }
}
