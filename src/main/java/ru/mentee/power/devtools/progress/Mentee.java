package ru.mentee.power.devtools.progress;

public record Mentee(
    String name,
    String city,
    String goal,
    int completedLessons,
    int totalLessons
) {
  public Mentee {
    if (completedLessons < 0 || totalLessons <= 0 || completedLessons > totalLessons) {
      throw new IllegalArgumentException("Некорректные значения прогресса");
    }
  }
}