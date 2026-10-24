# devtools


Учебный Gradle-проект курса. Java 25 toolchain, JUnit 5 + AssertJ.

## Quick Start

- `./gradlew run` — запустить ProgressDemo
- `./gradlew test` — прогнать тесты
- `./gradlew build` — полная сборка

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

### Сценарий: Checkstyle упал

1. `./gradlew checkstyleMain` → FAILED.
2. Открой `build/reports/checkstyle/main.html`.
3. Для каждого нарушения: файл / строка / правило → исправь.
4. Полезно: Code → Reformat Code (`Ctrl+Alt+L`) → Code → Optimize Imports (`Ctrl+Alt+O`) → вручную исправить naming и длинные строки.
5. Перезапусти `./gradlew checkstyleMain` → SUCCESS.

### Сценарий: JaCoCo не хватает покрытия

1. `./gradlew jacocoTestCoverageVerification` → FAILED (`Instruction coverage is X, but expected minimum is 0.80`).
2. Открой `build/reports/jacoco/test/html/index.html`.
3. Найди красные (непокрытые) строки.
4. Допиши тесты для этих строк.
5. Перезапусти `./gradlew jacocoTestCoverageVerification` → SUCCESS.