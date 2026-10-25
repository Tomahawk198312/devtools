package ru.mentee.power;

public class ProgressDemo {
  public static void main(String[] args) {
    MenteeProgress progress = new MenteeProgress(
        "Васил Хамидуллин", // значение из твоего плана PLAN-2
        1,               // номер спринта
        6                // запланированные часы на спринт
    );

    System.out.println(progress.summary());

    if (progress.readyForSprint()) {
      System.out.println("Status: sprint ready");
    } else {
      System.out.println("Status: backlog first");
    }
  }
}
