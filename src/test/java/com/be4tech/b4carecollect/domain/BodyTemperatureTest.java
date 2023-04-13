package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BodyTemperatureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BodyTemperature.class);
        BodyTemperature bodyTemperature1 = new BodyTemperature();
        bodyTemperature1.setId(UUID.randomUUID());
        BodyTemperature bodyTemperature2 = new BodyTemperature();
        bodyTemperature2.setId(bodyTemperature1.getId());
        assertThat(bodyTemperature1).isEqualTo(bodyTemperature2);
        bodyTemperature2.setId(UUID.randomUUID());
        assertThat(bodyTemperature1).isNotEqualTo(bodyTemperature2);
        bodyTemperature1.setId(null);
        assertThat(bodyTemperature1).isNotEqualTo(bodyTemperature2);
    }
}
