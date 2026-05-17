# Angular frontend coding style and UI/state reminders

## Purpose

Keep Angular code small, readable, and easy to verify.

Use this file for Angular-only slices in `banko/`: shell setup, routing, components, forms, services, and UI state. If the change also touches Spring Boot, load the fullstack style doc as well.

This repo uses Angular 21 with standalone components, router config, SSR/hydration, and signals in the app shell.

## Project mental model

- The app shell owns providers, routing, and hydration.
- Features own pages, components, forms, and local state.
- Services own HTTP calls and mapping.
- Components render state; they should not hide business flow.
- Signals are a good fit for local UI state and derived values.

## Recommended package structure

Prefer feature folders, then layer within the feature.

```text
src/app
  core
  shared
  features
    wallet
      pages
      components
      services
      models
      guards
      state
```

Keep `app.ts`, `app.routes.ts`, and `app.config.ts` thin and focused on shell concerns.

## Coding style

- Use standalone components and explicit imports.
- Keep the root component minimal; let routes drive the pages.
- Prefer `signal` and `computed` for local state; avoid ad hoc mutable fields when a signal fits.
- Keep component methods short and named after UI actions.
- Put HTTP calls in services, not directly in components.
- Keep form models and view models explicit.
- Prefer typed forms for user input.
- Treat loading, empty, and error states as first-class UI states.
- Keep templates declarative and easy to scan.
- Use accessible labels, semantic HTML, and disabled states for pending actions.
- Keep provider setup in the app shell or dedicated core files.

## Component and template rules

### Shell and routing

- Keep the app shell thin: router outlet, title, and top-level providers only.
- Put route definitions in one place and keep them easy to follow.
- Use route guards for navigation UX, not for backend authorization.

### Pages and components

- Let page components orchestrate child components and data loading.
- Keep dumb UI components reusable and input-driven.
- Do not embed backend-specific logic in templates.
- Keep DOM manipulation out of component code unless the browser requires it.

### State management

- Keep local state local.
- Use services or facades for shared data and API state.
- Derive UI state from a single source of truth where possible.
- Do not duplicate the same state in multiple components.

## Forms, services, and API calls

- Use reactive forms for money actions and other validated flows.
- Validate at the UI boundary for user feedback, but keep the backend as the source of truth.
- Map API errors into readable messages.
- Keep request/response mapping in the service layer.
- Never let a component speak directly to the backend entity model.

## Routing and SSR rules

- Keep routing declarative and feature-based.
- Put auth-related route protection in guards.
- Keep interceptor logic tiny and testable.
- Be SSR/hydration-safe: avoid unguarded browser-only APIs in render paths.
- Keep the application config in `app.config.ts` and server config in the server files.

## Test order

1. Component tests.
2. Service tests.
3. Guard and interceptor tests.
4. Integration or contract tests.
5. End-to-end tests for the main user flow.

## Coding checklist for agents

Before coding, write down:

- the user flow,
- the route or screen,
- the state transitions,
- the loading and error states,
- the API contract it depends on,
- the test that proves the behavior.

## What to avoid

- Business rules in components.
- API calls in templates.
- Direct DOM access in shared UI code.
- Shared mutable state that bypasses Angular state flow.
- Ignoring loading, empty, or error states.
- Coupling the UI to backend entities or raw HTTP responses.

## UI logic summary

- The shell renders the app and owns navigation.
- Features own forms, tables, and page state.
- Services own transport and mapping.
- Components should make the user flow obvious.
- UI failures should be readable and recoverable.
