package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AlarmRiskIndexSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlarmRiskIndexSummary.class);
        AlarmRiskIndexSummary alarmRiskIndexSummary1 = new AlarmRiskIndexSummary();
        alarmRiskIndexSummary1.setId(UUID.randomUUID());
        AlarmRiskIndexSummary alarmRiskIndexSummary2 = new AlarmRiskIndexSummary();
        alarmRiskIndexSummary2.setId(alarmRiskIndexSummary1.getId());
        assertThat(alarmRiskIndexSummary1).isEqualTo(alarmRiskIndexSummary2);
        alarmRiskIndexSummary2.setId(UUID.randomUUID());
        assertThat(alarmRiskIndexSummary1).isNotEqualTo(alarmRiskIndexSummary2);
        alarmRiskIndexSummary1.setId(null);
        assertThat(alarmRiskIndexSummary1).isNotEqualTo(alarmRiskIndexSummary2);
    }
}
