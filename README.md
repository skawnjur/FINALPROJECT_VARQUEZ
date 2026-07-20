# E-Wallet Application (Capstone Project)

## Software System Description
This application is a desktop-based Digital E-Wallet platform built in Java using JavaFX. It simulates a digital banking engine allowing users to register accounts, review real-time balances formatted to local currency (`en_PH`), initiate cash deposits, process cash withdrawals, and review color-coded chronological interactive transaction graphs and lists.

### Major System Features
* **User Authentication:** Robust login/logout workflows linked to unique access profiles.
* **Wallet Balance Management:** Input-validated financial mutation parameters (Cash-In/Cash-Out).
* **Color-Coded Transaction History:** Custom dynamic ListView layouts highlighting incoming and outgoing fund structures.
* **Persistent Session Architecture:** Seamless file checkpoint systems allowing app closures without active progress loss.

---

## Session Management & Serialization Mechanism
User session persistence is handled using **Java Serialization**. 
* **Creation:** Upon a successful login verification, a `UserSessionData` object is initialized and exported as a byte-stream binary data file named `session.dat` on the root workspace path using an `ObjectOutputStream`.
* **Validation:** During navigation transitions and application reboots, the system queries for this file using `ObjectInputStream`. If found, the application recovers the state context seamlessly without prompting redundant login constraints.
* **Deletion:** When the user actively selects the "Logout" trigger action button, the system systematically calls `Session.endSession()`, wiping memory registers and executing a hard file delete on `session.dat` before safely routing back to the primary login landing page.

---

## Applied SOLID Design Principles

### 1. Single Responsibility Principle (SRP)
* **Application Context:** Isolated data persistence logic out of the standard `Session` state controller class into a specialized standalone file infrastructure utility class called `SessionSerializer`.
* **Involved Classes:** `Session` and `SessionSerializer`.
* **Benefit Gained:** Code maintainability improves significantly. If our persistent file mechanism ever upgrades from localized flat binary files (`.dat`) to a cloud SQL database structure, zero adjustments are needed inside the core session/business loop. We modify solely the `SessionSerializer` layer.

### 2. Dependency Inversion Principle (DIP)
* **Application Context:** Created a decoupled design abstraction named `TransactionService`. Controllers and backend systems interact purely with this abstract contractual architecture interface instead of binding to static concrete classes directly.
* **Involved Classes/Interfaces:** `TransactionService` (Interface) and `Session` (Implementation).
* **Benefit Gained:** High decoupling flexibility. UI display widgets are no longer permanently hard-coded to a singular database simulator class. Any concrete implementation handling logging protocols matching the interface signature can slide directly under the view layer smoothly, facilitating seamless unit testing through mocking layers.
