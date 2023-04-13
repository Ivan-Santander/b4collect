package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserMedicalInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMedicalInfo.class);
        UserMedicalInfo userMedicalInfo1 = new UserMedicalInfo();
        userMedicalInfo1.setId(UUID.randomUUID());
        UserMedicalInfo userMedicalInfo2 = new UserMedicalInfo();
        userMedicalInfo2.setId(userMedicalInfo1.getId());
        assertThat(userMedicalInfo1).isEqualTo(userMedicalInfo2);
        userMedicalInfo2.setId(UUID.randomUUID());
        assertThat(userMedicalInfo1).isNotEqualTo(userMedicalInfo2);
        userMedicalInfo1.setId(null);
        assertThat(userMedicalInfo1).isNotEqualTo(userMedicalInfo2);
    }
}
