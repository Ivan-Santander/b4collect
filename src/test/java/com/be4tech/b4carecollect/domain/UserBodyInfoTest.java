package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserBodyInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBodyInfo.class);
        UserBodyInfo userBodyInfo1 = new UserBodyInfo();
        userBodyInfo1.setId(UUID.randomUUID());
        UserBodyInfo userBodyInfo2 = new UserBodyInfo();
        userBodyInfo2.setId(userBodyInfo1.getId());
        assertThat(userBodyInfo1).isEqualTo(userBodyInfo2);
        userBodyInfo2.setId(UUID.randomUUID());
        assertThat(userBodyInfo1).isNotEqualTo(userBodyInfo2);
        userBodyInfo1.setId(null);
        assertThat(userBodyInfo1).isNotEqualTo(userBodyInfo2);
    }
}
