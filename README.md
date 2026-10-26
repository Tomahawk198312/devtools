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

## Информационный поиск — результаты DVT-11

### Запросы и источники

| № | Запрос | Операторы | Официальный источник | Альтернатива | Статус | Дата |
|---|---|---|---|---|---|---|
| 1 | Актуальная версия Lombok в Maven Central | `site:search.maven.org "lombok" OR site:mvnrepository.com "lombok"` | https://central.sonatype.com/artifact/org.projectlombok/lombok — версия **1.18.48** (2026-09-02) | https://mvnrepository.com/artifact/org.projectlombok/lombok | 200 OK | 2026-10-25 |
| 2 | Java 25 Stream API | `site:docs.oracle.com "stream api" intitle:api` | https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/package-summary.html | https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html (если 25 недоступна) | 200 OK | 2026-10-25 |
| 3 | Горячие клавиши IntelliJ IDEA | `site:www.jetbrains.com/help "keymap"` | https://www.jetbrains.com/help/idea/2025.3/settings-keymap.html | Help → Keymap Reference (PDF внутри IDE) | 200 OK | 2026-10-25 |

### AI-промпты и сравнение

#### Промпт 1: План решения

**Задача:** исправить потенциальный `ArrayIndexOutOfBoundsException` в цикле `while` в `ProgressTracker.calculateTotalProgress`.

**Контекст:** Java 25, Gradle-проект `devtools`, уже есть `record Mentee` и класс `ProgressTracker` с циклом `while (index < mentees.length)`.

**Ограничения:** нельзя использовать `for-each`, потому что по заданию нужен именно `while`; нельзя менять сигнатуру метода.

**Что нужно:** план из 3–5 шагов с объяснением «почему». **НЕ пиши код** — реализую сам.

**Критерии успеха:** `./gradlew test` зелёный, тест `shouldThrowWhenNullArray` проходит.

**AI-1 (DeepSeek):**
> 1. Добавить проверку `mentees == null` в начале метода — иначе `mentees.length` бросит `NullPointerException`.
> 2. Проверить пустой массив: `mentees.length == 0` — вернуть строку «Суммарно: пройдено 0 из 0 уроков, осталось 0 уроков».
> 3. Оставить цикл `while (index < mentees.length)` — это корректная граница, off-by-one нет.
> 4. Добавить тест на `null` и на пустой массив.  
     > Почему: `null` и пустой массив — два разных граничных случая; `while` с `<` не даёт выйти за границу, поэтому сам цикл менять не нужно.

**AI-2 (YandexGPT):**
> 1. Проверить, что `mentees` не `null`.
> 2. Проверить, что массив не пустой.
> 3. В цикле `while` использовать условие `index < mentees.length`, не `<=`.
> 4. Убедиться, что `index++` стоит в теле цикла.
> 5. Добавить тесты на граничные случаи.  
     > Ответ получился более общим, без явного указания, что возвращать при пустом массиве.

**Сравнение:** оба ассистента сошлись на проверке `null` и на том, что условие `index < mentees.length` корректно. DeepSeek дал более конкретный план: что именно возвращать при пустом массиве и почему `while` менять не нужно. YandexGPT перечислил шаги более обще, без деталей возврата. Выбрал **DeepSeek**, потому что его план можно сразу реализовать без уточнений.

#### Промпт 2: Проверка кода

**Код для проверки:**
```java
public List<Student> getStudentsByCity(String city) {
  return studentList.stream()
      .filter(s -> s.city().equals(city))
      .toList();
}

**AI-1 (DeepSeek):**

> Разбор по трём пунктам, которые ты просил проверить.
>
> **1. Логические ошибки / граничные случаи.**
> — `s.city()` может быть `null` (в `record Student(String name, String city)` поле не валидируется в конструкторе). Тогда `.equals(city)` бросит `NullPointerException`.
> — Параметр `city` тоже может быть `null`. В текущем виде `city.equals(...)` внутри лямбды упадёт с `NullPointerException`, потому что ты вызываешь `equals` на `s.city()`, но если передать `null` — вернёт `false` на каждой итерации, что маскирует ошибку вызывающего кода.
> — Пустой `studentList` обрабатывается корректно: `.toList()` вернёт пустой неизменяемый список.
>
> **2. Java Code Conventions.**
> — Имя метода `getStudentsByCity` — корректный camelCase.
> — Параметр `city` — корректный camelCase.
> — Лямбда-параметр `s` слишком короткий для читаемости, лучше `student`.
> — Импорт `List` в файле есть, порядок импортов не нарушен.
>
> **3. Производительность.**
> — Стрим по маленькому списку — нормально. Для больших коллекций `filter().toList()` создаёт промежуточный поток — O(n) по времени, O(k) по памяти, где k — число совпадений. Оптимально.
> — Если метод будет вызываться часто, имеет смысл кэшировать результат или хранить студентов в `Map<String, List<Student>>` по городу, но это уже архитектурное решение, не часть этого метода.
>
> **Как исправить (не переписывая):** используй `Objects.equals(s.city(), city)` — он безопасно обрабатывает `null` с обеих сторон. Для явного контракта добавь в JavaDoc, что `city == null` возвращает пустой список или бросает `IllegalArgumentException` — выбери и зафиксируй.

**AI-2 (YandexGPT):**

> Код в целом корректен, но есть потенциальные проблемы.
>
> — Если `s.city()` вернёт `null`, вызов `.equals(city)` приведёт к `NullPointerException`. Рекомендую добавить проверку на `null` для поля `city`.
> — Стоит рассмотреть использование `Objects.equals()` для безопасного сравнения.
> — Параметр `city` желательно провалидировать: если он `null`, метод сейчас вернёт `false` для всех элементов, что может быть неожиданно для вызывающего кода.
> — Стиль кода соответствует Java Code Conventions, имя метода и параметра корректные.
> — Производительность: стрим проходит по всему списку один раз, это O(n), для типичных задач достаточно.
>
> В остальном код можно оставить как есть, добавив обработку `null`.

**Сравнение:**

Оба ассистента сошлись на главной проблеме — `NullPointerException` при `s.city() == null` — и оба предложили `Objects.equals` как безопасное сравнение. Различие в глубине: DeepSeek разобрал три запрошенных пункта по отдельности (логика, стиль, производительность) и явно указал, что пустой список обрабатывается корректно; YandexGPT дал более короткий ответ и не проверил отдельно производительность и JavaDoc, но верно заметил проблему `null`-параметра `city`. Выбрал **DeepSeek**, потому что его ответ структурно совпадает с тремя пунктами промпта и содержит конкретное предложение по исправлению без переписывания кода — то, что я просил.

## Технологический стек проекта

### Языки и платформы
- Java 25 LTS — основной язык
- Gradle (через Gradle Wrapper) — система сборки

### Инструменты качества кода
- Checkstyle — статический анализ стиля (`config/checkstyle/checkstyle.xml`, `./gradlew checkstyleMain`)
- JUnit 5 + AssertJ — тесты (`./gradlew test`)
- JaCoCo — покрытие тестами (порог 80%, `./gradlew jacocoTestCoverageVerification`)

### CI/CD
- GitHub Actions — Checkstyle, тесты, JaCoCo и сборка на каждый push и PR (`.github/workflows/ci.yml`)

### Правила кода
- Стиль: Google Java Style (через Checkstyle)
- Ветки: `feature/DVT-X`, `master` — основная; PR обязателен для слияния
- Само-ревью по чеклисту перед сдачей

## Личный глоссарий терминов Dev Tools

### Категория: Java-экосистема

#### JDK — Java Development Kit
**Определение:** Development environment for building Java applications; includes javac compiler, jar and javadoc tools.
**Контекст использования:** нужен для компиляции кода в байт-код и сборки проекта; без JDK проект не собрать.
**Пример:** `java -version` проверяет версию; в IntelliJ IDEA Project SDK указывает на установленный JDK.
**Источник:** https://docs.oracle.com/en/java/javase/

#### JRE — Java Runtime Environment
**Определение:** Runtime environment that provides the libraries and JVM needed to run Java applications, but not development tools like the compiler.
**Контекст использования:** нужен для запуска готовых приложений на машине пользователя; для разработки недостаточен.
**Пример:** пользователь получает JRE вместе с Java-приложением и может его запустить, но не скомпилировать новый код.
**Источник:** https://docs.oracle.com/javase/8/docs/technotes/guides/

#### JVM — Java Virtual Machine
**Определение:** Virtual machine that executes Java bytecode, providing platform independence ("write once, run anywhere").
**Контекст использования:** обеспечивает кроссплатформенность: один и тот же байт-код запускается на Windows, macOS, Linux.
**Пример:** `java HelloWorld` запускает JVM, которая интерпретирует `.class`-файл.
**Источник:** https://docs.oracle.com/javase/specs/jvms/se21/html/index.html

#### Build Tool — Система сборки
**Определение:** Software that automates compiling, testing, packaging and dependency resolution of a project.
**Контекст использования:** избавляет от ручного скачивания JAR и настройки classpath; Gradle и Maven — типичные примеры.
**Пример:** `./gradlew build` компилирует код, гоняет тесты и собирает артефакт одной командой.
**Источник:** https://docs.gradle.org/current/userguide/what_is_gradle.html

#### Dependency — Зависимость
**Определение:** External library or module that a project requires to compile, run, or test; in Gradle declared via `implementation` / `testImplementation`.
**Контекст использования:** Gradle подключает внешние библиотеки (Lombok, JUnit); без объявления зависимости код не скомпилируется.
**Пример:** `testImplementation 'org.assertj:assertj-core:3.27.7'` подключает AssertJ как зависимость.
**Источник:** https://docs.gradle.org/current/userguide/dependency_management.html

### Категория: Инструменты разработки

#### IDE — Integrated Development Environment
**Определение:** Application that combines code editor, compiler, debugger, build tools and version control in one interface.
**Контекст использования:** ускоряет разработку за счёт подсветки ошибок, автодополнения, рефакторинга и отладки.
**Пример:** IntelliJ IDEA — IDE, в которой мы пишем, запускаем и отлаживаем Java-код.
**Источник:** https://www.jetbrains.com/help/idea/discover-intellij-idea.html

#### SDK — Software Development Kit
**Определение:** Set of tools, libraries and documentation for developing software for a specific platform.
**Контекст использования:** более широкое понятие, чем JDK; может включать эмуляторы, CLI-утилиты, примеры.
**Пример:** Android SDK — для разработки Android-приложений; JDK — это частный случай SDK для Java.
**Источник:** https://developer.android.com/studio/intro/update

#### Git — Git
**Определение:** Distributed version control system that tracks changes in source code and supports branching and merging.
**Контекст использования:** хранит историю изменений локально; без Git не работают PR, code review и CI.
**Пример:** `git commit -m "..."` фиксирует изменения в локальной истории.
**Источник:** https://git-scm.com/book/en/v2/Getting-Started-What-is-Git%3F

#### Repository — Репозиторий
**Определение:** Storage location for version-controlled code (Git) or for packaged artifacts (Maven/Gradle).
**Контекст использования:** в Git — хранилище истории; в Maven Central — хранилище библиотек; контекст различает смысл.
**Пример:** `https://github.com/Tomahawk198312/devtools` — Git-репозиторий проекта; `mavenCentral()` в `build.gradle` — artifact-репозиторий.
**Источник:** https://git-scm.com/book/en/v2/Git-Basics-Getting-a-Git-Repository

#### Commit — Коммит
**Определение:** Snapshot of the project at a point in time, recorded in the repository history with a unique hash, author and message.
**Контекст использования:** атомарная единица изменений; сообщение коммита объясняет «что и почему».
**Пример:** `git commit -m "DVT-12: глоссарий и технологический стек"` фиксирует изменения в истории.
**Источник:** https://git-scm.com/docs/git-commit

#### Branch — Ветка
**Определение:** Movable pointer to a commit that lets you isolate work without affecting other lines of development.
**Контекст использования:** в команде каждая задача — отдельная `feature/DVT-X`; `master` остаётся чистым.
**Пример:** `git checkout -b feature/DVT-12` создаёт и переключает на новую ветку.
**Источник:** https://git-scm.com/book/en/v2/Git-Branching-Branches-in-a-Nutshell

#### Pull Request — Pull Request
**Определение:** Request to merge changes from one branch into another, with built-in code review, checks and discussion.
**Контекст использования:** обязательный шаг перед слиянием в `master`; CI и ревью проходят в самом PR.
**Пример:** на GitHub открываем PR `feature/DVT-12 → master`; зелёный CI и merge подтверждают готовность.
**Источник:** https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests

#### Gradle Wrapper — Gradle Wrapper
**Определение:** Script (`gradlew`/`gradlew.bat`) plus configuration that pins a specific Gradle version and downloads it on demand.
**Контекст использования:** обеспечивает одинаковую версию Gradle у всех разработчиков и на CI.
**Пример:** `./gradlew build` скачивает нужную версию Gradle и запускает сборку.
**Источник:** https://docs.gradle.org/current/userguide/gradle_wrapper.html

#### Checkstyle — Checkstyle
**Определение:** Static analysis tool that checks Java source code against a configurable set of coding-style rules.
**Контекст использования:** ловит нарушения стиля до code review; ошибки (naming) блокируют сборку.
**Пример:** `./gradlew checkstyleMain` запускает проверку main-кода; отчёт — `build/reports/checkstyle/main.html`.
**Источник:** https://checkstyle.org/

#### Debug — Отладка
**Определение:** Process of finding and fixing defects by inspecting program execution step by step and examining variable values.
**Контекст использования:** позволяет понять, почему код работает не так, без временных `println`.
**Пример:** запуск `Debug 'ProgressTracker.main()'` в IntelliJ IDEA с breakpoint на цикле.
**Источник:** https://www.jetbrains.com/help/idea/debugging-code.html

#### Breakpoint — Точка останова
**Определение:** Marker on a source line that pauses program execution when reached in debug mode, allowing inspection of state.
**Контекст использования:** останавливает программу в нужном месте, чтобы посмотреть значения переменных.
**Пример:** ставим breakpoint на `while (index < mentees.length)` и проходим итерации через Step Over (F8).
**Источник:** https://www.jetbrains.com/help/idea/using-breakpoints.html

### Категория: Процессы и практики

#### Code Review — Код-ревью
**Определение:** Systematic examination of source code by someone other than the author to find defects and improve quality.
**Контекст использования:** обязательный шаг перед merge; ловит логические ошибки, проблемы архитектуры, читаемости.
**Пример:** в PR оставляем комментарий по шаблону «Проблема → Почему важно → Предложение».
**Источник:** https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/reviewing-changes-in-pull-requests/about-pull-request-reviews

#### CI/CD
**Определение:** Continuous Integration (frequent automated build/test on every change) and Continuous Delivery/Deployment (automated release pipeline).
**Контекст использования:** CI запускается на каждый push/PR и ловит поломки до code review.
**Пример:** GitHub Actions workflow `.github/workflows/ci.yml` на каждый push прогоняет Checkstyle, тесты, JaCoCo и build.
**Источник:** https://docs.github.com/en/actions/automating-builds-and-tests/about-continuous-integration

## Вопросы по сложным терминам

### Вопрос 1: JDK / JRE / JVM — в чём разница и когда что использовать
**Задача:** понять, зачем нужны три разных понятия и какое из них выбирать в каждой ситуации.
**Контекст:** в DVT-1 ставили JDK, в CI указываем `distribution: temurin`, на проде пользователь получает приложение с JRE внутри.
**Ограничения:** уже читал общие статьи, но путался, что включает JDK, а что — JRE.
**Ожидаемый результат:** чёткое понимание иерархии (JDK ⊃ JRE ⊃ JVM) и умение выбирать нужный компонент под задачу.
**Критерии успеха:** могу без подсказок объяснить коллеге, почему для компиляции нужен JDK, а для запуска хватит JRE.

### Вопрос 2: Pull Request vs прямой push в master — зачем нужен PR
**Задача:** понять, какую проблему решает PR и почему прямой push в master — антипаттерн.
**Контекст:** в DVT-4…DVT-8 работаем только через PR; в командных проектах прямой push запрещён политикой.
**Ограничения:** знаю, что «так принято», но не до конца понимаю механику проверок до merge.
**Ожидаемый результат:** понимание ролей CI, code review и обсуждения внутри PR.
**Критерии успеха:** могу объяснить, что произойдёт, если запушить прямо в master без PR — какие проверки пропустятся и почему это риск.

## Как называются тесты в этом проекте

Схема: `shouldРезультат_whenУсловие`.

Примеры из проекта:
- `shouldCalculateTotalProgressWhenMultipleMentees`
- `shouldThrowWhenCompletedExceedsTotal`
- `shouldReturnApproximatePercentWhenNotDivisibleEvenly`
- `shouldReturnExactPercentWhenAllMenteesHalfDone`

Правила:
- Имя теста читается как утверждение: что должно произойти и при каком условии.
- Плохие имена: `test1`, `testMentee`, `shouldWork` — не говорят, что именно проверяется.
- Если к имени хочется добавить «And» — тест делает слишком много, разбей на два.
- Тест с несколькими проверками одного объекта — один логический тест; разные объекты — разные тесты.

Ассерты:
- Дробные числа (`double`/`float`) — `isCloseTo(..., offset(...))`, не `isEqualTo`.
- Коллекции — `extracting(...).containsExactly(...)` проверяет состав и порядок.
- Исключения — `assertThatThrownBy(...).isInstanceOf(...).hasMessageContaining(...)` —
  проверяем и тип, и причину.
- Цепочка ассертов на одном объекте — `assertThat(x).isGreaterThan(0).isLessThanOrEqualTo(100)`.