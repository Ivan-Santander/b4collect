package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ActivitySummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivitySummary.class);
        ActivitySummary activitySummary1 = new ActivitySummary();
        activitySummary1.setId(UUID.randomUUID());
        ActivitySummary activitySummary2 = new ActivitySummary();
        activitySummary2.setId(activitySummary1.getId());
        assertThat(activitySummary1).isEqualTo(activitySummary2);
        activitySummary2.setId(UUID.randomUUID());
        assertThat(activitySummary1).isNotEqualTo(activitySummary2);
        activitySummary1.setId(null);
        assertThat(activitySummary1).isNotEqualTo(activitySummary2);
    }
}
