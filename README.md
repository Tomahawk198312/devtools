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
