package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FuntionalIndexSummaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuntionalIndexSummary.class);
        FuntionalIndexSummary funtionalIndexSummary1 = new FuntionalIndexSummary();
        funtionalIndexSummary1.setId(UUID.randomUUID());
        FuntionalIndexSummary funtionalIndexSummary2 = new FuntionalIndexSummary();
        funtionalIndexSummary2.setId(funtionalIndexSummary1.getId());
        assertThat(funtionalIndexSummary1).isEqualTo(funtionalIndexSummary2);
        funtionalIndexSummary2.setId(UUID.randomUUID());
        assertThat(funtionalIndexSummary1).isNotEqualTo(funtionalIndexSummary2);
        funtionalIndexSummary1.setId(null);
        assertThat(funtionalIndexSummary1).isNotEqualTo(funtionalIndexSummary2);
    }
}
