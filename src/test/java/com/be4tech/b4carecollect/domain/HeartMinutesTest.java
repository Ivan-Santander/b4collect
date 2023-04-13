package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class HeartMinutesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HeartMinutes.class);
        HeartMinutes heartMinutes1 = new HeartMinutes();
        heartMinutes1.setId(UUID.randomUUID());
        HeartMinutes heartMinutes2 = new HeartMinutes();
        heartMinutes2.setId(heartMinutes1.getId());
        assertThat(heartMinutes1).isEqualTo(heartMinutes2);
        heartMinutes2.setId(UUID.randomUUID());
        assertThat(heartMinutes1).isNotEqualTo(heartMinutes2);
        heartMinutes1.setId(null);
        assertThat(heartMinutes1).isNotEqualTo(heartMinutes2);
    }
}
