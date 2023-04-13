package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class FuntionalIndexTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuntionalIndex.class);
        FuntionalIndex funtionalIndex1 = new FuntionalIndex();
        funtionalIndex1.setId(UUID.randomUUID());
        FuntionalIndex funtionalIndex2 = new FuntionalIndex();
        funtionalIndex2.setId(funtionalIndex1.getId());
        assertThat(funtionalIndex1).isEqualTo(funtionalIndex2);
        funtionalIndex2.setId(UUID.randomUUID());
        assertThat(funtionalIndex1).isNotEqualTo(funtionalIndex2);
        funtionalIndex1.setId(null);
        assertThat(funtionalIndex1).isNotEqualTo(funtionalIndex2);
    }
}
