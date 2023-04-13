package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SpeedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speed.class);
        Speed speed1 = new Speed();
        speed1.setId(UUID.randomUUID());
        Speed speed2 = new Speed();
        speed2.setId(speed1.getId());
        assertThat(speed1).isEqualTo(speed2);
        speed2.setId(UUID.randomUUID());
        assertThat(speed1).isNotEqualTo(speed2);
        speed1.setId(null);
        assertThat(speed1).isNotEqualTo(speed2);
    }
}
