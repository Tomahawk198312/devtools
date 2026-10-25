![CI](https://github.com/Tomahawk198312/devtools/actions/workflows/ci.yml/badge.svg)
# devtools


Учебный Gradle-проект курса. Java 25 toolchain, JUnit 5 + AssertJ.

## Quick Start

- `./gradlew run` — запустить ProgressDemo
- `./gradlew test` — прогнать тесты
- `./gradlew build` — полная сборка
- `./gradlew checkstyleMain` — проверить стиль
- `./gradlew jacocoTestCoverageVerification` — проверить покрытие ≥ 80%

## Packages

`ru.mentee.power` — namespace проекта: область видимости, которая предотвращает
конфликты имён между модулями. Структура папок (`src/main/java/ru/mentee/power/`)
синхронизирована с объявлением пакета.

## MenteeProgress

| Поле | Тип | Описание |
|---|---|---|
| menteeName | String | имя студента |
| sprintNumber | int | номер спринта |
| plannedHoursPerWeek | int | запланированные часы на спринт |

Правило `readyForSprint()`: `plannedHoursPerWeek >= 3` → `true`, иначе `false`.

## Git локальный цикл

- `git status` — что изменено и что уже в staging.
- `git add <file>` — подготовить изменения к коммиту.
- `git commit -m "..."` — зафиксировать staged изменения в локальной истории.
- `git log --oneline` — краткая история коммитов.
- `git branch --show-current` — показать активную ветку.

## Правило веток: feature/DVT-X

- `master` остаётся чистым: только базовая настройка и стабильные изменения.
- Работа ведётся в ветке `feature/DVT-<номер>`, например `feature/DVT-3`.
- Перед сдачей урока история должна показывать коммиты и в `master`, и в фича-ветке.

## Сценарий ручной проверки DVT-6

### Запуск
./gradlew run
Ожидаемый вывод: Суммарно: пройдено 25 из 36 уроков, осталось 11 уроков

### Тесты
./gradlew test
Ожидаемый вывод: BUILD SUCCESSFUL, все тесты зелёные
(2 теста calculateTotalProgress + 4 теста на исключения Mentee)

### Debug
- Breakpoint на `while (index < mentees.length)` в ProgressTracker
- Debug 'ProgressTracker.main()', Step Over (F8) ×3
- Variables: index / totalCompleted / totalTotal растут как в таблице урока
- Evaluate Expression: `totalCompleted + mentees[index].completedLessons()` = 25
  (после 2-й итерации)

### При ошибках
- Вывод неверный → проверь цикл через Debug
- Тесты красные → открой Test Report, найди AssertionError
- Breakpoint не срабатывает → убедись, что запущен Debug (жук), не Run
## Удалённый репозиторий: протокол доступа

HTTPS (https://github.com/Tomahawk198312/devtools.git)

## Кодстайл-гайд проекта devtools

Проект следует правилам Google Java Style Guide с адаптацией.
Автоматическая проверка: `./gradlew checkstyleMain`

### 1. Именование полей и методов: camelCase

До:    `private List<Student> student_list;`
`public void add_student(Student s) { }`

После: `private List<Student> students;`
`public void addStudent(Student s) { }`

Почему: Java Convention требует camelCase, snake_case запрещён.
Источник: https://google.github.io/styleguide/javaguide.html#s5.3-camel-case

### 2. Пробелы после ключевых слов: if / for / while

До:    `if(student != null)`

После: `if (student != null)`

Почему: пробел отделяет ключевое слово от выражения, улучшает читаемость.
Источник: Oracle Code Conventions — Whitespace

### 3. Обязательные фигурные скобки

До:    `if (student != null)`
`students.add(student);`

После: `if (student != null) {`
`students.add(student);`
`}`

Почему: скобки обязательны даже для однострочных блоков — снижают риск ошибки при правках.
Источник: https://google.github.io/styleguide/javaguide.html#s4.1.1-braces-always-used

### 4. Длина строки: максимум 100 символов

До:    `public List<Student> get_students_from_specific_city_with_very_long_method_name_exceeding_line_limit(String city) {`

После: `public List<Student> getStudentsByCity(String city) {`

Почему: длинные строки не влезают в экран, усложняют review.
Источник: https://google.github.io/styleguide/javaguide.html#s4.4-column-limit

### 5. Импорты: без wildcard, без неиспользуемых

До:    `import java.util.*;`
`import java.io.File;`

После: `import java.util.ArrayList;`
`import java.util.List;`

Почему: wildcard скрывает реальные зависимости, неиспользуемые импорты засоряют файл.
Источник: https://google.github.io/styleguide/javaguide.html#s3.3-import-statements

## Runbook — качество кода

### Сценарий 1: Checkstyle упал (`checkstyleMain` FAILED)

**Реальный случай DVT-7.** После создания `StudentList.java` с намеренными нарушениями
`./gradlew checkstyleMain` упал с 9 violations. Отчёт `build/reports/checkstyle/main.html`
показал (фрагмент):

| Файл | Строка | Правило | Severity | Сообщение |
|---|---|---|---|---|
| StudentList.java | 7 | MemberName | error | Field name 'student_list' must be camelCase |
| StudentList.java | 14 | MethodName | error | Method name 'add_student' must match pattern |
| StudentList.java | 4 | AvoidStarImport | error | Using the '.*' form of import should be avoided |
| StudentList.java | 16 | WhitespaceAround | warning | 'if' is not followed by whitespace |
| StudentList.java | 19 | LineLength | warning | Line is longer than 100 characters |

**Что делал (порядок шагов):**
1. `Code → Reformat Code` (`Ctrl+Alt+L`) — поправил отступы и добавил фигурные скобки.
2. `Code → Optimize Imports` (`Ctrl+Alt+O`) — удалил `import java.util.*`, оставил явные
   `java.util.ArrayList` и `java.util.List` в алфавитном порядке.
3. Ручная правка naming (форматтер имена не трогает):
  - поле `student_list` → `studentList` (`MemberName`);
  - метод `add_student` → `addStudent` (`MethodName`);
  - `if(student != null)` → `if (student != null)` (`WhitespaceAround`).
4. Разбил длинную строку цепочки `studentList.stream().filter(...).toList()` на три строки —
   `LineLength` (лимит 100 символов).
5. Перезапустил `./gradlew checkstyleMain checkstyleTest` → **BUILD SUCCESSFUL**,
   `main.html` — **0 violations**.

### Сценарий 2: JaCoCo не хватает покрытия (`jacocoTestCoverageVerification` FAILED)

**Реальный случай DVT-7.** После добавления класса `StudentList` без тестов
`./gradlew jacocoTestCoverageVerification` упал с сообщением:

    Rule violated for bundle devtools: instructions covered ratio is 0.78,
    but expected minimum is 0.80

**Что делал (порядок шагов):**
1. Открыл `build/reports/jacoco/test/html/index.html`.
2. В отчёте по `StudentList` методы `addStudent` и `getStudentsByCity` — красные
   (0% instruction coverage), а сам record `Student` частично непокрыт (equals/hashCode/toString).
3. Дописал `StudentListTest`:
  - `shouldAddStudentWhenNotNull` (happy-path);
  - `shouldIgnoreNullStudent` (граничный: `null`-аргумент);
  - `shouldReturnOnlyMatchingCity` (фильтр по городу);
  - `shouldReturnEmptyWhenNoMatch` (пустой результат);
  - `shouldTestStudentRecordMethods` (покрытие `equals/hashCode/toString` record `Student`).
4. Перезапустил `./gradlew jacocoTestCoverageVerification` → **BUILD SUCCESSFUL**,
   итоговое покрытие — **0.97 (97% instruction coverage)**.

## Code Review Checklist

### Функциональность
- [ ] Код решает поставленную задачу полностью
- [ ] Обработаны граничные случаи (null, пустые данные, экстремальные значения)
- [ ] Обработка ошибок реализована корректно

### Тесты
- [ ] Добавлены тесты для нового функционала
- [ ] Все тесты проходят локально: ./gradlew test
- [ ] Покрыты позитивные и негативные сценарии
- [ ] JaCoCo coverage >= 80% для нового кода

### Читаемость и стиль
- [ ] Имена переменных, методов и классов отражают назначение
- [ ] Нет дублирования кода (DRY)
- [ ] Checkstyle проходит без ошибок: ./gradlew checkstyleMain
- [ ] Нет закомментированного кода или отладочного вывода (System.out.println)

### Документация
- [ ] README обновлён (если добавлена новая функциональность)
- [ ] Публичные методы имеют JavaDoc (если применимо)
- [ ] Runbook обновлён (если изменились команды)

### Производительность и безопасность
- [ ] Нет очевидных проблем производительности
- [ ] Нет хардкода паролей, токенов или конфиденциальных данных

## Примеры Code Review комментариев

### Конструктивный пример 1 — конкретность и предложение

**Проблема:** `ProgressDemo.java`, строка 15 — оставлен отладочный вывод
`System.out.println("Current branch: feature/DVT-3");`.
**Почему это важно:** отладочный вывод попадает в проде и шумит в логах;
имя ветки захардкожено и станет неверным после мержа.
**Предложение:** удалить строку — информация о ветке доступна из Git.

### Конструктивный пример 2 — критика кода, не автора

**Проблема:** `StudentList.java`, публичные методы `addStudent` и
`getStudentsByCity` не имеют JavaDoc.
**Почему это важно:** по контракту метода не видно, что происходит с `null`-
аргументом и какая семантика фильтра.
**Предложение:** добавить JavaDoc с параметрами и `@return` — прецедент уже есть
в `ProgressTracker.calculateTotalProgress`.

### Токсичный пример 1 — неконкретная критика

❌ «Этот код ужасен, переписать всё.»
Почему плохо: нет файла, строки, сути проблемы, нет предложения.
Автор не понимает, что править и зачем.

### Токсичный пример 2 — критика автора, а не кода

❌ «Ты вообще читал чеклист? Убери свой println, сколько можно.»
Почему плохо: переходит на личность, демотивирует, не объясняет «почему».
Правильно: «`ProgressDemo.java:15` — отладочный вывод, предлагаю удалить,
чтобы не шумел в логах.»

## Результаты само-ревью DVT-9

Само-ревью по чеклисту проведено для изменений PR DVT-8 (CI workflow,
README badge). Найдено 3 замечания:

### Замечание 1 — отладочный вывод в проде

- **Файл/строка:** `src/main/java/ru/mentee/power/ProgressDemo.java:15`
- **Суть:** оставлен `System.out.println("Current branch: feature/DVT-3");`
- **Почему важно:** отладочный вывод в проде, захардкоженное имя ветки
  станет неверным после мержа.
- **Как исправить:** удалить строку.

### Замечание 2 — отсутствует JavaDoc на публичных методах

- **Файл/строка:** `src/main/java/ru/mentee/power/devtools/student/StudentList.java:14,20`
- **Суть:** `addStudent(Student)` и `getStudentsByCity(String)` — без JavaDoc.
- **Почему важно:** не видно контракта — что с `null`, что вернёт фильтр.
- **Как исправить:** добавить JavaDoc с `@param` / `@return`.

### Замечание 3 — Quick Start без команд проверки качества

- **Файл/строка:** `README.md` → раздел Quick Start
- **Суть:** нет команд `./gradlew checkstyleMain` и
  `./gradlew jacocoTestCoverageVerification`.
- **Почему важно:** новый разработчик не знает, как прогнать проверки до PR,
  и ловит красный CI на первом же push.
- **Как исправить:** добавить обе команды в Quick Start.