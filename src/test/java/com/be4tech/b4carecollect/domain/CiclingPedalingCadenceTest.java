package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CiclingPedalingCadenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiclingPedalingCadence.class);
        CiclingPedalingCadence ciclingPedalingCadence1 = new CiclingPedalingCadence();
        ciclingPedalingCadence1.setId(UUID.randomUUID());
        CiclingPedalingCadence ciclingPedalingCadence2 = new CiclingPedalingCadence();
        ciclingPedalingCadence2.setId(ciclingPedalingCadence1.getId());
        assertThat(ciclingPedalingCadence1).isEqualTo(ciclingPedalingCadence2);
        ciclingPedalingCadence2.setId(UUID.randomUUID());
        assertThat(ciclingPedalingCadence1).isNotEqualTo(ciclingPedalingCadence2);
        ciclingPedalingCadence1.setId(null);
        assertThat(ciclingPedalingCadence1).isNotEqualTo(ciclingPedalingCadence2);
    }
}
