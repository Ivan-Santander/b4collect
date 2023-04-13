package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserDemographicsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDemographics.class);
        UserDemographics userDemographics1 = new UserDemographics();
        userDemographics1.setId(UUID.randomUUID());
        UserDemographics userDemographics2 = new UserDemographics();
        userDemographics2.setId(userDemographics1.getId());
        assertThat(userDemographics1).isEqualTo(userDemographics2);
        userDemographics2.setId(UUID.randomUUID());
        assertThat(userDemographics1).isNotEqualTo(userDemographics2);
        userDemographics1.setId(null);
        assertThat(userDemographics1).isNotEqualTo(userDemographics2);
    }
}
