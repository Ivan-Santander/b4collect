package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TemperatureSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemperatureSummary.class);
        TemperatureSummary temperatureSummary1 = new TemperatureSummary();
        temperatureSummary1.setId(UUID.randomUUID());
        TemperatureSummary temperatureSummary2 = new TemperatureSummary();
        temperatureSummary2.setId(temperatureSummary1.getId());
        assertThat(temperatureSummary1).isEqualTo(temperatureSummary2);
        temperatureSummary2.setId(UUID.randomUUID());
        assertThat(temperatureSummary1).isNotEqualTo(temperatureSummary2);
        temperatureSummary1.setId(null);
        assertThat(temperatureSummary1).isNotEqualTo(temperatureSummary2);
    }
}
