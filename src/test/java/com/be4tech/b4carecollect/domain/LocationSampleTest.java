package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class LocationSampleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationSample.class);
        LocationSample locationSample1 = new LocationSample();
        locationSample1.setId(UUID.randomUUID());
        LocationSample locationSample2 = new LocationSample();
        locationSample2.setId(locationSample1.getId());
        assertThat(locationSample1).isEqualTo(locationSample2);
        locationSample2.setId(UUID.randomUUID());
        assertThat(locationSample1).isNotEqualTo(locationSample2);
        locationSample1.setId(null);
        assertThat(locationSample1).isNotEqualTo(locationSample2);
    }
}
