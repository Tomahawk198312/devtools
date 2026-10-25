package ru.mentee.power.devtools.student;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class StudentListTest {

  @Test
  void shouldAddStudentWhenNotNull() {
    StudentList list = new StudentList();
    list.addStudent(new Student("Иван", "Москва"));
    assertThat(list.getStudentsByCity("Москва")).hasSize(1);
  }

  @Test
  void shouldIgnoreNullStudent() {
    StudentList list = new StudentList();
    list.addStudent(null);
    assertThat(list.getStudentsByCity("Москва")).isEmpty();
  }

  @Test
  void shouldReturnOnlyMatchingCity() {
    StudentList list = new StudentList();
    list.addStudent(new Student("Иван", "Москва"));
    list.addStudent(new Student("Мария", "СПб"));
    list.addStudent(new Student("Пётр", "Москва"));

    List<Student> moscow = list.getStudentsByCity("Москва");

    assertThat(moscow).hasSize(2);
    assertThat(moscow).allMatch(s -> s.city().equals("Москва"));
  }

  @Test
  void shouldReturnEmptyWhenNoMatch() {
    StudentList list = new StudentList();
    list.addStudent(new Student("Иван", "Москва"));
    assertThat(list.getStudentsByCity("Казань")).isEmpty();
  }

  @Test
  void shouldTestStudentRecordMethods() {
    Student s1 = new Student("Иван", "Москва");
    Student s2 = new Student("Иван", "Москва");
    Student s3 = new Student("Мария", "СПб");

    assertThat(s1).isEqualTo(s2);
    assertThat(s1).isNotEqualTo(s3);
    assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
    assertThat(s1.toString()).contains("Иван").contains("Москва");
  }
}