package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CaloriesBMRTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaloriesBMR.class);
        CaloriesBMR caloriesBMR1 = new CaloriesBMR();
        caloriesBMR1.setId(UUID.randomUUID());
        CaloriesBMR caloriesBMR2 = new CaloriesBMR();
        caloriesBMR2.setId(caloriesBMR1.getId());
        assertThat(caloriesBMR1).isEqualTo(caloriesBMR2);
        caloriesBMR2.setId(UUID.randomUUID());
        assertThat(caloriesBMR1).isNotEqualTo(caloriesBMR2);
        caloriesBMR1.setId(null);
        assertThat(caloriesBMR1).isNotEqualTo(caloriesBMR2);
    }
}
