package ru.mentee.power;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MenteeProgressTest {

  @Test
  void shouldFormatSummaryWhenProgressCreated() {
    MenteeProgress progress = new MenteeProgress("Ирина", 1, 8);

    assertThat(progress.summary()).isEqualTo("Sprint 1 → Ирина: planned 8 h");
  }

  @Test
  void shouldDetectReadinessWhenHoursAboveThreshold() {
    assertThat(new MenteeProgress("Ирина", 1, 4).readyForSprint()).isTrue();
  }

  @Test
  void shouldDetectLackOfReadinessWhenHoursBelowThreshold() {
    assertThat(new MenteeProgress("Ирина", 1, 2).readyForSprint()).isFalse();
  }
}