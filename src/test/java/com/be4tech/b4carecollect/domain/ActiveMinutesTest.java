package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ActiveMinutesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActiveMinutes.class);
        ActiveMinutes activeMinutes1 = new ActiveMinutes();
        activeMinutes1.setId(UUID.randomUUID());
        ActiveMinutes activeMinutes2 = new ActiveMinutes();
        activeMinutes2.setId(activeMinutes1.getId());
        assertThat(activeMinutes1).isEqualTo(activeMinutes2);
        activeMinutes2.setId(UUID.randomUUID());
        assertThat(activeMinutes1).isNotEqualTo(activeMinutes2);
        activeMinutes1.setId(null);
        assertThat(activeMinutes1).isNotEqualTo(activeMinutes2);
    }
}
