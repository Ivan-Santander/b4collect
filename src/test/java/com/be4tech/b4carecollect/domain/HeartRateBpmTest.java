package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HeartRateBpmTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeartRateBpm.class);
        HeartRateBpm heartRateBpm1 = new HeartRateBpm();
        heartRateBpm1.setId(UUID.randomUUID());
        HeartRateBpm heartRateBpm2 = new HeartRateBpm();
        heartRateBpm2.setId(heartRateBpm1.getId());
        assertThat(heartRateBpm1).isEqualTo(heartRateBpm2);
        heartRateBpm2.setId(UUID.randomUUID());
        assertThat(heartRateBpm1).isNotEqualTo(heartRateBpm2);
        heartRateBpm1.setId(null);
        assertThat(heartRateBpm1).isNotEqualTo(heartRateBpm2);
    }
}
