package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MentalHealthSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MentalHealthSummary.class);
        MentalHealthSummary mentalHealthSummary1 = new MentalHealthSummary();
        mentalHealthSummary1.setId(UUID.randomUUID());
        MentalHealthSummary mentalHealthSummary2 = new MentalHealthSummary();
        mentalHealthSummary2.setId(mentalHealthSummary1.getId());
        assertThat(mentalHealthSummary1).isEqualTo(mentalHealthSummary2);
        mentalHealthSummary2.setId(UUID.randomUUID());
        assertThat(mentalHealthSummary1).isNotEqualTo(mentalHealthSummary2);
        mentalHealthSummary1.setId(null);
        assertThat(mentalHealthSummary1).isNotEqualTo(mentalHealthSummary2);
    }
}
