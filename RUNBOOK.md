# Runbook — devtools

## Git-гигиена перед push

**Правило: перед push `git status` должен быть чистым.**

- [ ] Открыть Commit Tool Window (Alt+0) и CLI `git status` — картина совпадает.
- [ ] Нет untracked-мусора из `.idea/workspace.xml`, `build/`, `out/`, `*.iml`, `.DS_Store`, `Thumbs.db`.
- [ ] Новые инструменты → сначала паттерн в `.gitignore`, затем `git rm --cached`, затем коммит.
- [ ] Работа ведётся в `feature/DVT-X`, не в `master`.
- [ ] Коммиты атомарные: чистка Git — отдельно от функциональных правок.

## Диагностика «что попало в индекс»

- `git ls-files .idea build out` — что уже отслеживается.
- `git check-ignore -v <путь>` — какой паттерн `.gitignore` сработал.
- `git diff --name-status HEAD~1..HEAD` — что вошло в последний коммит.