package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class NutritionSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NutritionSummary.class);
        NutritionSummary nutritionSummary1 = new NutritionSummary();
        nutritionSummary1.setId(UUID.randomUUID());
        NutritionSummary nutritionSummary2 = new NutritionSummary();
        nutritionSummary2.setId(nutritionSummary1.getId());
        assertThat(nutritionSummary1).isEqualTo(nutritionSummary2);
        nutritionSummary2.setId(UUID.randomUUID());
        assertThat(nutritionSummary1).isNotEqualTo(nutritionSummary2);
        nutritionSummary1.setId(null);
        assertThat(nutritionSummary1).isNotEqualTo(nutritionSummary2);
    }
}
