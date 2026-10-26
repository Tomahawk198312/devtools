package ru.mentee.power.devtools.progress;

public class ProgressTracker {

  /*
   * Суммарный прогресс группы mentee.
   */
  public String calculateTotalProgress(Mentee[] mentees) {
    int totalCompleted = 0;
    int totalTotal = 0;
    int index = 0;

    while (index < mentees.length) {
      totalCompleted += mentees[index].completedLessons();
      totalTotal += mentees[index].totalLessons();
      index++;
    }

    int remaining = totalTotal - totalCompleted;
    return "Суммарно: пройдено %d из %d уроков, осталось %d уроков"
        .formatted(totalCompleted, totalTotal, remaining);
  }

  /**
   * Средний процент прохождения по всем mentee.
   *
   * @throws IllegalArgumentException если массив пуст
   */
  public double averageProgressPercent(Mentee[] mentees) {
    if (mentees == null || mentees.length == 0) {
      throw new IllegalArgumentException("Пустой список mentee");
    }

    double sumPercent = 0.0;
    int index = 0;
    while (index < mentees.length) {
      sumPercent += mentees[index].completedLessons() * 100.0 / mentees[index].totalLessons();
      index++;
    }
    return sumPercent / mentees.length;
  }

  public static void main(String[] args) {
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend разработка", 5, 12),
        new Mentee("Мария", "Санкт-Петербург", "Fullstack", 8, 12),
        new Mentee("Пётр", "Казань", "Java Backend", 12, 12)
    };

    System.out.println(new ProgressTracker().calculateTotalProgress(mentees));
    System.out.println("Средний процент: "
        + new ProgressTracker().averageProgressPercent(mentees));
  }
}