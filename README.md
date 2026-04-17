# 🏦 Banking with Hanzo ♥

> A not-so-small playground for building a **high-performance, fault-tolerant banking system** using the latest tech stack 🚀

---

## ✨ What is this?

This project is a **modern banking backend + frontend system** designed with:

* 💸 Real-world transaction handling (no fake balance updates here)
* 🔁 Event-driven architecture (Kafka-powered)
* 🧠 Smart concurrency with Java 25
* 🔒 Security & audit as first-class citizens

Think of it as:

> *"What if you built a fintech system the right way from day one?"*

---

## 🧰 Tech Stack

| Layer     | Tech                    |
| --------- | ----------------------- |
| Backend   | Java 25 + Spring Boot 4 |
| Database  | PostgreSQL              |
| Cache     | Redis                   |
| Messaging | Kafka                   |
| Frontend  | Next.js (React)         |
| Payments  | Stripe / MoMo           |

---

## 🧱 Architecture Overview

* Microservices-based
* Event-driven (Kafka as backbone)
* Ledger-based accounting system (no direct balance mutation ❌)

### Core Services

* 🔐 Identity Service — Auth, JWT, KYC
* 💼 Wallet Service — Balance & transactions
* 💳 Payment Service — External integrations
* 🔔 Notification Service — Async events

---

## 💡 Key Concepts

### 🧾 Ledger > Balance

We don’t “update balance”.

We:

* Write **transactions to a ledger**
* Derive balance from it

Because:

> Logs don’t lie. Mutations do.

---

### 🔁 Idempotency

Every transaction has a unique `request_id`.

* Duplicate request? → same response
* No double spending 💪

---

### 🔄 Saga Pattern

Distributed transactions without chaos:

* Step 1: Deduct (PENDING)
* Step 2: Call payment provider
* Step 3:

  * ✅ Success → CONFIRMED
  * ❌ Fail → Rollback (refund)

---

## ⚙️ Running Locally

```bash
docker-compose up -d
```

This spins up:

* PostgreSQL 🐘
* Redis ⚡
* Kafka 📨

---

## 📡 Realtime Updates

* WebSocket / SSE for live balance updates
* No refresh spam needed 😄

---

## 🔒 Security

* RBAC with Spring Security
* Audit logging (who did what, when)
* Webhook signature validation (Stripe)

---

## 🧪 Testing

* Integration tests with Testcontainers
* Real DB, real behavior, fewer surprises

---

## 🎯 Why this project?

Because most demos:

* Skip edge cases ❌
* Ignore consistency ❌
* Fake transactions ❌

This one doesn’t.

---

## 🚀 What's next?

* [ ] Docker infra setup
* [ ] Ledger implementation
* [ ] Wallet service
* [ ] Payment integration
* [ ] Frontend dashboard

---

## 🧠 Final Thought

> Banking systems are less about moving money…
> and more about **never losing track of it**.
