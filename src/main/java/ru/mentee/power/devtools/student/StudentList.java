package ru.mentee.power.devtools.student;

import java.util.ArrayList;
import java.util.List;

/**
 * Коллекция студентов.
 * DVT-7: намеренные нарушения стиля исправлены — MemberName, MethodName,
 * WhitespaceAround, LineLength, AvoidStarImport, CustomImportOrder.
 */
public class StudentList {
  private final List<Student> studentList;

  public StudentList() {
    studentList = new ArrayList<>();
  }

  /**
   * Добавляет студента в список. {@code null} игнорируется.
   *
   * @param student студент; {@code null} допускается
   */
  public void addStudent(Student student) {
    if (student != null) {
      studentList.add(student);
    }
  }

  /**
   * Возвращает студентов указанного города. Сравнение точное, регистрозависимое.
   *
   * @param city название города
   * @return список студентов этого города; пустой, если совпадений нет
   */
  public List<Student> getStudentsByCity(String city) {
    return studentList.stream()
        .filter(s -> s.city().equals(city))
        .toList();
  }
}