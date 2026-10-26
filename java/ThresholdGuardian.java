import java.time.LocalDateTime;

public class ThresholdGuardian {

    // Requirement 02: Configurable conditions and thresholds (not hardcoded)
    public static class GuardianConfig {
        double minPriceMovePercent;
        double maxGasPriceGwei;
        double minVaultLiquidityUSD;

        public GuardianConfig(double minPriceMove, double maxGas, double minLiquidity) {
            this.minPriceMovePercent = minPriceMove;
            this.maxGasPriceGwei = maxGas;
            this.minVaultLiquidityUSD = minLiquidity;
        }
    }

    // Requirement 01: Reads at least 3 independent conditions from the chain
    public static class ChainState {
        double actualPriceMovePercent;
        double actualGasPriceGwei;
        double actualVaultLiquidityUSD;

        public ChainState(double priceMove, double gas, double liquidity) {
            this.actualPriceMovePercent = priceMove;
            this.actualGasPriceGwei = gas;
            this.actualVaultLiquidityUSD = liquidity;
        }
    }

    // Requirement 03: Decision trail logs showing each condition's value, pass/fail, and final decision
    public static class DecisionAuditTrail {
        LocalDateTime timestamp;
        String condition1Result;
        String condition2Result;
        String condition3Result;
        boolean finalDecisionTriggered;
        String explanation;

        public DecisionAuditTrail(String c1, String c2, String c3, boolean trigger, String explanation) {
            this.timestamp = LocalDateTime.now();
            this.condition1Result = c1;
            this.condition2Result = c2;
            this.condition3Result = c3;
            this.finalDecisionTriggered = trigger;
            this.explanation = explanation;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== THRESHOLD GUARDIAN AUDIT TRAIL ===\n");
            sb.append(String.format("Timestamp: %s\n", timestamp));
            sb.append(String.format(" [Cond 1 - Price Move]: %s\n", condition1Result));
            sb.append(String.format(" [Cond 2 - Gas Price]: %s\n", condition2Result));
            sb.append(String.format(" [Cond 3 - Vault Liquidity]: %s\n", condition3Result));
            sb.append(String.format(" Final Decision: %s\n", finalDecisionTriggered ? "TRIGGER ACTION (Execute Rebalance)" : "HOLD / IGNORE ACTION"));
            sb.append(String.format(" Explanation: %s\n", explanation));
            return sb.toString();
        }
    }

    // Evaluation Engine
    public DecisionAuditTrail evaluateGuardian(GuardianConfig config, ChainState state) {
        // Condition 1: Price move check
        boolean cond1Pass = state.actualPriceMovePercent >= config.minPriceMovePercent;
        String c1Text = String.format("Measured: %.2f%% | Required >= %.2f%% -> %s", 
            state.actualPriceMovePercent, config.minPriceMovePercent, cond1Pass ? "PASS" : "FAIL");

        // Condition 2: Gas price check
        boolean cond2Pass = state.actualGasPriceGwei <= config.maxGasPriceGwei;
        String c2Text = String.format("Measured: %.1f Gwei | Required <= %.1f Gwei -> %s", 
            state.actualGasPriceGwei, config.maxGasPriceGwei, cond2Pass ? "PASS" : "FAIL");

        // Condition 3: Vault liquidity check
        boolean cond3Pass = state.actualVaultLiquidityUSD >= config.minVaultLiquidityUSD;
        String c3Text = String.format("Measured: $%.2f | Required >= $%.2f -> %s", 
            state.actualVaultLiquidityUSD, config.minVaultLiquidityUSD, cond3Pass ? "PASS" : "FAIL");

        // Multi-condition guard logic
        boolean finalTrigger = cond1Pass && cond2Pass && cond3Pass;
        String explanation;

        if (finalTrigger) {
            explanation = "All independent on-chain conditions met successfully. Action triggered safely.";
        } else {
            explanation = "One or more conditions failed. Guardian held execution to prevent suboptimal state.";
        }

        return new DecisionAuditTrail(c1Text, c2Text, c3Text, finalTrigger, explanation);
    }

    // Requirement 04: Demonstrates both outcomes (one run where it triggers and another where it holds)
    public static void main(String[] args) {
        ThresholdGuardian guardian = new ThresholdGuardian();

        // Configurable thresholds
        GuardianConfig config = new GuardianConfig(3.0, 25.0, 500000.0);

        System.out.println("=== RUNNING THRESHOLD GUARDIAN AGENT ===");

        // Run 1: Trigger outcome (all conditions pass)
        System.out.println("\n--- Run 1: Favorable Conditions (Expected: TRIGGER) ---");
        ChainState favorableState = new ChainState(4.5, 18.0, 750000.0);
        DecisionAuditTrail resultA = guardian.evaluateGuardian(config, favorableState);
        System.out.println(resultA);

        // Run 2: Hold outcome (conditions fail)
        System.out.println("\n--- Run 2: Unfavorable Conditions (Expected: HOLD) ---");
        ChainState unfavorableState = new ChainState(2.1, 35.0, 600000.0);
        DecisionAuditTrail resultB = guardian.evaluateGuardian(config, unfavorableState);
        System.out.println(resultB);
    }
}
