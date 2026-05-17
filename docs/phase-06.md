# Phase 06 — Security

## Outcome

The app feels like a real banking system.

## Use cases

- Log in and access wallet data securely.
- Protect sensitive endpoints.
- Record who did what and when.

## User stories

- As a user, I want only my own data to be accessible.
- As a learner, I want to see JWT/OAuth2 in a real app.

## Functional requirements

- Add JWT/OAuth2 support.
- Protect backend endpoints.
- Add roles such as user and admin.
- Add Angular route guards.
- Add audit logging.

## Non-functional requirements

- Access must follow least privilege.
- Secrets must be handled safely.
- Tokens must expire.
- Auth failures must be clear but not verbose.

## Executable code tasks

- Add Spring Security configuration for JWT/OAuth2 resource server support.
- Secure the wallet API routes and return proper unauthorized/forbidden responses.
- Add Angular auth interceptors for attaching access tokens.
- Add Angular route guards for protected pages.
- Add auth-focused tests for token validation and protected route access.

## Done when

- Protected routes require authentication.
- Sensitive actions are authorized.

## Reference docs

- [Fullstack coding style and cross-layer coordination](./agent-skills/llm-coding-style-fullstack.md)
