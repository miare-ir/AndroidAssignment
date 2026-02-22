# Android Live Coding Challenge

## Overview

You are given an Android project that:

- Uses Activity + Fragment
- Contains business logic inside the Fragment
- Displays a list of football players

The current implementation works but is not structured for scalability or long-term maintenance.

Your task is to improve and extend the project to a production-ready level.

---

## Goals

We are interested in:

- How you structure code
- How you manage state
- How you separate responsibilities
- How you reason about architectural decisions
- How you ensure maintainability and testability

Please explain your decisions while working.

---

## Requirements

### 1. Improve the Project Structure

Refactor the existing implementation to improve:

- Separation of concerns
- State management
- Scalability
- Testability
- Code readability

You are free to decide:

- Architectural style
- Project structure
- How responsibilities are divided
- How state is managed

---

### 2. Replace the UI with Jetpack Compose

- Display a list of players.
- Each player item should show:
    - Name
    - League
    - Goals scored
- The UI should react to state changes.
- Keep the data flow predictable and clean.

---

### 3. Follow / Unfollow Players

- The user must be able to follow or unfollow any player.
- The UI should update immediately when toggled.
- The follow state must survive configuration changes (e.g., rotation).

Persistent storage is optional.

---

### 4. Data Consideration

Assume:

- Player data may come from a remote source.
- Follow state may come from a local source.

Design your solution in a way that could support both cleanly.

You do not need to implement networking.

---

### 5. Testing

Add at least one meaningful unit test for core logic.

The test should not depend on Android framework classes.

---

## Optional (If Time Allows)

- Discuss or implement pagination
- Discuss sorting across the full dataset
- Add persistence for follow state
- Improve modularity
- Add additional tests

---

## Evaluation Focus

We are evaluating:

- Architectural decisions
- State management approach
- Code quality and readability
- Testability
- Communication and reasoning
- Ability to scale the solution

---

## Important

This is not about finishing every feature.

We are more interested in:

- How you think
- How you structure code
- How you approach problems
- The trade-offs you make