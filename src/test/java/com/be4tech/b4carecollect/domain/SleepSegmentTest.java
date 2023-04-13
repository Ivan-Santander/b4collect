package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SleepSegmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SleepSegment.class);
        SleepSegment sleepSegment1 = new SleepSegment();
        sleepSegment1.setId(UUID.randomUUID());
        SleepSegment sleepSegment2 = new SleepSegment();
        sleepSegment2.setId(sleepSegment1.getId());
        assertThat(sleepSegment1).isEqualTo(sleepSegment2);
        sleepSegment2.setId(UUID.randomUUID());
        assertThat(sleepSegment1).isNotEqualTo(sleepSegment2);
        sleepSegment1.setId(null);
        assertThat(sleepSegment1).isNotEqualTo(sleepSegment2);
    }
}
