package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class OxygenSaturationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OxygenSaturation.class);
        OxygenSaturation oxygenSaturation1 = new OxygenSaturation();
        oxygenSaturation1.setId(UUID.randomUUID());
        OxygenSaturation oxygenSaturation2 = new OxygenSaturation();
        oxygenSaturation2.setId(oxygenSaturation1.getId());
        assertThat(oxygenSaturation1).isEqualTo(oxygenSaturation2);
        oxygenSaturation2.setId(UUID.randomUUID());
        assertThat(oxygenSaturation1).isNotEqualTo(oxygenSaturation2);
        oxygenSaturation1.setId(null);
        assertThat(oxygenSaturation1).isNotEqualTo(oxygenSaturation2);
    }
}
