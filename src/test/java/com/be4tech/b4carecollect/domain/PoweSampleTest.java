package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PoweSampleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoweSample.class);
        PoweSample poweSample1 = new PoweSample();
        poweSample1.setId(UUID.randomUUID());
        PoweSample poweSample2 = new PoweSample();
        poweSample2.setId(poweSample1.getId());
        assertThat(poweSample1).isEqualTo(poweSample2);
        poweSample2.setId(UUID.randomUUID());
        assertThat(poweSample1).isNotEqualTo(poweSample2);
        poweSample1.setId(null);
        assertThat(poweSample1).isNotEqualTo(poweSample2);
    }
}
