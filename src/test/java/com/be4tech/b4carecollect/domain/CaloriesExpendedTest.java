package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CaloriesExpendedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CaloriesExpended.class);
        CaloriesExpended caloriesExpended1 = new CaloriesExpended();
        caloriesExpended1.setId(UUID.randomUUID());
        CaloriesExpended caloriesExpended2 = new CaloriesExpended();
        caloriesExpended2.setId(caloriesExpended1.getId());
        assertThat(caloriesExpended1).isEqualTo(caloriesExpended2);
        caloriesExpended2.setId(UUID.randomUUID());
        assertThat(caloriesExpended1).isNotEqualTo(caloriesExpended2);
        caloriesExpended1.setId(null);
        assertThat(caloriesExpended1).isNotEqualTo(caloriesExpended2);
    }
}
