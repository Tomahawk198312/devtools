package ru.mentee.power.devtools.progress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Тестирование ProgressTracker")
class ProgressLoopTest {

    @Test
    @DisplayName("Суммарный прогресс для нескольких mentee с разным прогрессом")
    void shouldCalculateTotalProgress_whenMultipleMentees() {
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
    void shouldCalculateTotalProgress_whenAllMenteesCompleted() {
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
    void shouldThrow_whenCompletedExceedsTotal() {
        assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 15, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Некорректные значения прогресса");
    }

    @Test
    @DisplayName("Исключение, когда completedLessons отрицательный")
    void shouldThrow_whenCompletedNegative() {
        assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", -1, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Некорректные значения прогресса");
    }

    @Test
    @DisplayName("Исключение, когда totalLessons меньше либо равен нулю")
    void shouldThrow_whenTotalNotPositive() {
        assertThatThrownBy(() -> new Mentee("Иван", "Москва", "Backend", 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Некорректные значения прогресса");
    }

    @Test
    @DisplayName("Корректный Mentee не выбрасывает исключений")
    void shouldNotThrow_whenDataValid() {
        assertThatCode(() -> new Mentee("Иван", "Москва", "Backend", 5, 12))
                .doesNotThrowAnyException();
    }
}