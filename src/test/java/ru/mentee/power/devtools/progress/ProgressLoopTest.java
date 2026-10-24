package ru.mentee.power.devtools.progress;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  // ===== ТВОЯ ЧАСТЬ: тесты на исключения Mentee =====

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

    org.assertj.core.api.Assertions.assertThat(m1).isEqualTo(m2);
    org.assertj.core.api.Assertions.assertThat(m1).isNotEqualTo(m3);
    org.assertj.core.api.Assertions.assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
    org.assertj.core.api.Assertions.assertThat(m1.toString()).contains("Иван");
  }
}