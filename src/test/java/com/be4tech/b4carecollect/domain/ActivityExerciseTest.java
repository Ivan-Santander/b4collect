package com.be4tech.b4carecollect.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.be4tech.b4carecollect.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ActivityExerciseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityExercise.class);
        ActivityExercise activityExercise1 = new ActivityExercise();
        activityExercise1.setId(UUID.randomUUID());
        ActivityExercise activityExercise2 = new ActivityExercise();
        activityExercise2.setId(activityExercise1.getId());
        assertThat(activityExercise1).isEqualTo(activityExercise2);
        activityExercise2.setId(UUID.randomUUID());
        assertThat(activityExercise1).isNotEqualTo(activityExercise2);
        activityExercise1.setId(null);
        assertThat(activityExercise1).isNotEqualTo(activityExercise2);
    }
}
