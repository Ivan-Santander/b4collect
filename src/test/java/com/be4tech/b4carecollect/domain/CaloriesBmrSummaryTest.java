package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CaloriesBmrSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaloriesBmrSummary.class);
        CaloriesBmrSummary caloriesBmrSummary1 = new CaloriesBmrSummary();
        caloriesBmrSummary1.setId(UUID.randomUUID());
        CaloriesBmrSummary caloriesBmrSummary2 = new CaloriesBmrSummary();
        caloriesBmrSummary2.setId(caloriesBmrSummary1.getId());
        assertThat(caloriesBmrSummary1).isEqualTo(caloriesBmrSummary2);
        caloriesBmrSummary2.setId(UUID.randomUUID());
        assertThat(caloriesBmrSummary1).isNotEqualTo(caloriesBmrSummary2);
        caloriesBmrSummary1.setId(null);
        assertThat(caloriesBmrSummary1).isNotEqualTo(caloriesBmrSummary2);
    }
}
