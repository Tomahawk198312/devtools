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

    public static void main(String[] args) {
        Mentee[] mentees = {
                new Mentee("Иван", "Москва", "Backend разработка", 5, 12),
                new Mentee("Мария", "Санкт-Петербург", "Fullstack", 8, 12),
                new Mentee("Пётр", "Казань", "Java Backend", 12, 12)
        };

        System.out.println(new ProgressTracker().calculateTotalProgress(mentees));
    }
}