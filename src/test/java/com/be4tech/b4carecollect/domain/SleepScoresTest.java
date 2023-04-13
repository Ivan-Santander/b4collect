package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SleepScoresTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SleepScores.class);
        SleepScores sleepScores1 = new SleepScores();
        sleepScores1.setId(UUID.randomUUID());
        SleepScores sleepScores2 = new SleepScores();
        sleepScores2.setId(sleepScores1.getId());
        assertThat(sleepScores1).isEqualTo(sleepScores2);
        sleepScores2.setId(UUID.randomUUID());
        assertThat(sleepScores1).isNotEqualTo(sleepScores2);
        sleepScores1.setId(null);
        assertThat(sleepScores1).isNotEqualTo(sleepScores2);
    }
}
