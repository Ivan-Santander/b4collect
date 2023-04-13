package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class WeightSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WeightSummary.class);
        WeightSummary weightSummary1 = new WeightSummary();
        weightSummary1.setId(UUID.randomUUID());
        WeightSummary weightSummary2 = new WeightSummary();
        weightSummary2.setId(weightSummary1.getId());
        assertThat(weightSummary1).isEqualTo(weightSummary2);
        weightSummary2.setId(UUID.randomUUID());
        assertThat(weightSummary1).isNotEqualTo(weightSummary2);
        weightSummary1.setId(null);
        assertThat(weightSummary1).isNotEqualTo(weightSummary2);
    }
}
