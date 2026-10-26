# Pharos Threshold Guardian Agent

An intelligent decision-making agent developed in **Java** for the Pharos Agent Arena designed to evaluate multiple independent on-chain conditions before deciding whether to trigger an action[cite: 9].

## 📋 Requirements Met

1. **Multi-Condition Chain Reading (Req. 01):** Evaluates at least 3 independent on-chain conditions (Price movement percentage, gas price, and vault liquidity)[cite: 9].
2. **Configurable Thresholds (Req. 02):** Parameters and thresholds are fully configurable via settings rather than hardcoded[cite: 9].
3. **Comprehensive Audit Trail (Req. 03):** Logs a clear decision trace for every execution showing individual condition values, pass/fail status, and the final verdict[cite: 9].
4. **Dual Outcome Demonstration (Req. 04):** Built-in execution runner demonstrating both scenarios—one where conditions trigger the action and another where the guardian holds execution[cite: 9].

## 🚀 How to Run

1. Certifique-se de ter o **Java JDK** instalado.
2. Compile o ficheiro:
   ```bash
   javac ThresholdGuardian.java
