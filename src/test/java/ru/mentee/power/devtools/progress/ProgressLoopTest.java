package ru.mentee.power.devtools.progress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.offset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Тестирование ProgressTracker")
class ProgressLoopTest {

  @Test
  @DisplayName("Суммарный прогресс для нескольких mentee с разным прогрессом")
  void shouldCalculateTotalProgressWhenMultipleMentees() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend разработка", 5, 12),
        new Mentee("Мария", "Санкт-Петербург", "Fullstack", 8, 12),
        new Mentee("Пётр", "Казань", "Java Backend", 12, 12)
    };

    String result = tracker.calculateTotalProgress(mentees);

    assertThat(mentees).extracting(Mentee::name).containsExactly("Иван", "Мария", "Пётр");
    assertThat(result)
        .contains("пройдено 25 из 36 уроков")
        .contains("осталось 11 уроков");
  }

  @Test
  @DisplayName("Все mentee завершили курс — осталось 0")
  void shouldCalculateTotalProgressWhenAllMenteesCompleted() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend", 12, 12),
        new Mentee("Мария", "СПб", "Fullstack", 12, 12)
    };

    String result = tracker.calculateTotalProgress(mentees);

    assertThat(result)
        .contains("пройдено 24 из 24 уроков")
        .contains("осталось 0 уроков");
  }

  // ===== Тесты на averageProgressPercent (DVT-13) =====

  @Test
  @DisplayName("Средний процент — ровный случай, все ровно на 50%")
  void shouldReturnExactPercentWhenAllMenteesHalfDone() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend", 6, 12),
        new Mentee("Мария", "СПб", "Fullstack", 6, 12)
    };

    double result = tracker.averageProgressPercent(mentees);

    assertThat(result).isEqualTo(50.0);
  }

  @Test
  @DisplayName("Средний процент — некруглый случай, точный ассерт через isCloseTo")
  void shouldReturnApproximatePercentWhenNotDivisibleEvenly() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend", 5, 12),
        new Mentee("Мария", "СПб", "Fullstack", 8, 12),
        new Mentee("Пётр", "Казань", "Java", 12, 12)
    };

    double result = tracker.averageProgressPercent(mentees);

    assertThat(result).isCloseTo(69.44, offset(0.01));
  }

  @Test
  @DisplayName("Средний процент — исключение на пустом массиве")
  void shouldThrowWhenMenteesArrayIsEmpty() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {};

    assertThatThrownBy(() -> tracker.averageProgressPercent(mentees))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Пустой список mentee");
  }

  @Test
  @DisplayName("Средний процент — границы результата (цепочка ассертов)")
  void shouldReturnPercentWithinBoundsWhenAtLeastOneNotCompleted() {
    ProgressTracker tracker = new ProgressTracker();
    Mentee[] mentees = {
        new Mentee("Иван", "Москва", "Backend", 5, 12),
        new Mentee("Пётр", "Казань", "Java", 12, 12)
    };

    double result = tracker.averageProgressPercent(mentees);

    assertThat(result)
        .isGreaterThan(0)
        .isLessThanOrEqualTo(100.0);
  }

  // ===== Тесты на исключения Mentee (DVT-6) =====

  @Test
  @DisplayName("Исключение, когда completedLessons больше totalLessons")
  void shouldThrowWhenCompletedExceedsTotal() {
    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 15, 10))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Некорректные значения прогресса");
  }

  @Test
  @DisplayName("Исключение, когда completedLessons отрицательный")
  void shouldThrowWhenCompletedNegative() {
    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", -1, 10))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Некорректные значения прогресса");
  }

  @Test
  @DisplayName("Исключение, когда totalLessons меньше либо равен нулю")
  void shouldThrowWhenTotalNotPositive() {
    assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 0, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Некорректные значения прогресса");
  }

  @Test
  @DisplayName("Корректный Mentee не выбрасывает исключений")
  void shouldNotThrowWhenDataValid() {
    assertThatCode(() -> new Mentee("Иван", "Москва", "Backend", 5, 12))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Покрытие методов record Mentee")
  void shouldTestMenteeRecordMethods() {
    Mentee m1 = new Mentee("Иван", "Москва", "Backend", 5, 12);
    Mentee m2 = new Mentee("Иван", "Москва", "Backend", 5, 12);
    Mentee m3 = new Mentee("Мария", "СПб", "Fullstack", 8, 12);

    assertThat(m1).isEqualTo(m2);
    assertThat(m1).isNotEqualTo(m3);
    assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
    assertThat(m1.toString()).contains("Иван");
  }
}