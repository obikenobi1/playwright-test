# Playwright Learning Roadmap

A phased plan for learning Playwright end-to-end test automation, from locators through CI integration. Each phase ends with a concrete deliverable so progress is demonstrable.

## Phase 1 — Locators & Web-First Assertions

Build a solid foundation in how Playwright finds elements and verifies state.

- Master role-based locators: `getByRole`, `getByLabel`, `getByText`, `getByTestId`. Prefer these over CSS or XPath selectors.
- Learn web-first assertions: `expect(locator).toBeVisible()`, `toHaveText()`, `toHaveValue()`.
- Internalize auto-waiting and auto-retry. This is the core mindset shift coming from Selenium — explicit waits are no longer needed because locators retry until the element is actionable.

**Deliverable:** A spec that logs into SauceDemo and asserts the user lands on the inventory page.

## Phase 2 — Structure: Page Object Model, Config, Fixtures

Organize tests for maintainability and scale.

- Apply the Page Object Model the Playwright way: locators as class properties, with action methods encapsulating interactions.
- Configure `playwright.config.ts`: `baseURL`, browser projects, retries, and reporters.
- Use custom fixtures (`test.extend`) — Playwright's dependency injection, which replaces most setup logic that would live in TestNG `@BeforeMethod` hooks.

**Deliverable:** The login flow refactored into a `LoginPage` Page Object plus a logged-in fixture.

## Phase 3 — Real End-to-End Flows

Implement realistic user journeys driven by actual test cases.

- Cover key login scenarios: valid login, invalid credentials, locked-out user, and empty-field validation.
- Use `storageState` to save authentication and skip login on subsequent tests — a core Playwright idiom.
- Write data-driven tests that loop over multiple credential sets.

**Deliverable:** A suite that implements a full set of login test cases, demonstrating the path from specification to automation.

## Phase 4 — API & Network

Combine UI testing with API control and network interception.

- Use the `request` fixture for API testing (the equivalent of REST-Assured).
- Use `page.route()` to mock or stub backend responses.
- Set up application state via the API, then verify the result through the UI.

**Deliverable:** A test that seeds data through the API and then asserts it appears correctly in the UI.

## Phase 5 — Debugging, Reporting, CI

Make tests observable, debuggable, and continuously run.

- Use Trace Viewer (`--trace on`), UI mode (`--ui`), and `--debug`.
- Configure the HTML reporter, with screenshots and video captured on failure.
- Run the suite in GitHub Actions and surface a passing build badge.
- Apply parallelization and sharding for faster runs.

**Deliverable:** A repository with passing CI and a published HTML report.

## Phase 6 — Advanced & Specialized Topics

Round out the skill set with high-signal extras.

- Explore Playwright MCP integration with an AI assistant — a distinctive capability that connects browser automation to agentic workflows.
- Gain awareness of accessibility and visual snapshot testing (familiarity is sufficient).

**Deliverable:** A short proof-of-concept or notes demonstrating one advanced topic.