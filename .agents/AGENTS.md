# Git Commit Convention
When committing code to this repository, you MUST follow this standard format to support future cross-platform development (Web, iOS, Android, Backend).

Format:
`<type>(<platform>): <short description>`

- `type`: feat, fix, chore, docs, refactor, style, test
- `platform`: `flutter`, `springboot`, `ios`, `web`, `android`, `shared`

Example:
```
feat(flutter): add onboarding flow UI
fix(springboot): resolve null pointer in AuthController
chore(shared): update gitignore
```

In the commit body, briefly describe the contents of the change.
