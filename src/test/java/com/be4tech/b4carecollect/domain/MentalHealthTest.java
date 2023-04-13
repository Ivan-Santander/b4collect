package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MentalHealthTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MentalHealth.class);
        MentalHealth mentalHealth1 = new MentalHealth();
        mentalHealth1.setId(UUID.randomUUID());
        MentalHealth mentalHealth2 = new MentalHealth();
        mentalHealth2.setId(mentalHealth1.getId());
        assertThat(mentalHealth1).isEqualTo(mentalHealth2);
        mentalHealth2.setId(UUID.randomUUID());
        assertThat(mentalHealth1).isNotEqualTo(mentalHealth2);
        mentalHealth1.setId(null);
        assertThat(mentalHealth1).isNotEqualTo(mentalHealth2);
    }
}
