# GitHub Rules

This file contains guidelines for working within this GitHub repository, including branch naming conventions, commit messages, and pull request descriptions.

## Branch Naming Conventions

There are three main types of branches:

1. **Feature** – For new functionalities or enhancements.
2. **Bugfix** – For fixing bugs.
3. **Hotfix** – For urgent fixes in production.

### Branch Name Composition

- Branch names should be clear and follow the format:

- **Branch types**:
- `feature`: For new features or significant changes.
- `bugfix`: For bug fixes.
- `hotfix`: For critical, urgent fixes.

- **Team abbreviation**: A short identifier for your team (e.g., `wbl` for "Webapplication /Businesslogic").

- **Task description**: A concise description of the task or change.

#### Example:
- `feature/wbl-svelteKit-implementation` – Implementation of SvelteKit for the web team.

## Commit Messages

- Keep commit messages **short and precise**.
- Focus on **what** has been done, not **how** or **why**.
- Use the imperative mood (e.g., "Add feature X", "Fix bug Y").

#### Example:
- `Fix button alignment issue on the homepage`
- `Add API integration for user profiles`

## Pull Request Descriptions

Each pull request should have a **clear description** to help reviewers understand the purpose and how to test it.

### Structure:
1. **What does this PR do?** – A brief explanation of the changes.
2. **How to test?** – Instructions on how to verify the changes.
3. **How does it work?** – Provide any relevant details on how the changes function, especially if they are complex or non-obvious.

#### Example:
**What does this PR do?**
- Adds SvelteKit implementation to handle client-side routing.

**How to test?**
- Run `npm install` and `npm run dev`, then navigate to `/profile` to see the new functionality in action.

**How does it work?**
- This PR sets up SvelteKit routing for the `profile` page, ensuring smooth transitions between views.
