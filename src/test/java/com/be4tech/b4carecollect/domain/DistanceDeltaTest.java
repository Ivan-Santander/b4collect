package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DistanceDeltaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistanceDelta.class);
        DistanceDelta distanceDelta1 = new DistanceDelta();
        distanceDelta1.setId(UUID.randomUUID());
        DistanceDelta distanceDelta2 = new DistanceDelta();
        distanceDelta2.setId(distanceDelta1.getId());
        assertThat(distanceDelta1).isEqualTo(distanceDelta2);
        distanceDelta2.setId(UUID.randomUUID());
        assertThat(distanceDelta1).isNotEqualTo(distanceDelta2);
        distanceDelta1.setId(null);
        assertThat(distanceDelta1).isNotEqualTo(distanceDelta2);
    }
}
