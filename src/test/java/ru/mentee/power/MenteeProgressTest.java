package ru.mentee.power;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenteeProgressTest {

    @Test
    void shouldFormatSummary_whenProgressCreated() {
        MenteeProgress progress = new MenteeProgress("Ирина", 1, 8);

        assertThat(progress.summary()).isEqualTo("Sprint 1 → Ирина: planned 8 h");
    }

    @Test
    void shouldDetectReadiness_whenHoursAboveThreshold() {
        assertThat(new MenteeProgress("Ирина", 1, 4).readyForSprint()).isTrue();
    }

    @Test
    void shouldDetectLackOfReadiness_whenHoursBelowThreshold() {
        assertThat(new MenteeProgress("Ирина", 1, 2).readyForSprint()).isFalse();
    }
}