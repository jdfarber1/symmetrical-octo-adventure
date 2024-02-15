import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CoffeeRotation {

    public static void main(String[] args) {
        Map<String, FinancialInfo> financialData = new HashMap<>();
        initializeFinancialData(financialData);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Bob always orders a cappuccino
            financialData.get("Bob").addExpense(3.0);

            // Enter drink costs for coworkers
            enterDrinkCosts(financialData);

            // Determine who should pay
            determinePersonToPay(financialData);

            // Display net contributions
            displayNetContributions(financialData);

            // Ask if the user wants to reset
            System.out.print("\nDo you want to reset? (yes/no): ");
            String resetChoice = scanner.next().toLowerCase();

            if ("yes".equals(resetChoice)) {
                initializeFinancialData(financialData);
                System.out.println("Program and private variables reset.");
                break;
            } else {
                System.out.println("Continuing with existing data.");
            }
        }
    }

    private static void initializeFinancialData(Map<String, FinancialInfo> financialData) {
        financialData.put("Bob", new FinancialInfo());
        financialData.put("Jeremy", new FinancialInfo());
        financialData.put("Coworker3", new FinancialInfo());
        financialData.put("Coworker4", new FinancialInfo());
        financialData.put("Coworker5", new FinancialInfo());
    }

    private static void enterDrinkCosts(Map<String, FinancialInfo> financialData) {
        Scanner scanner = new Scanner(System.in);

        for (String person : financialData.keySet()) {
            if (!person.equals("Bob")) {
                System.out.print("Enter the cost of the drink for " + person + ": ");
                double drinkCost = scanner.nextDouble();
                financialData.get(person).addExpense(drinkCost);
            }
        }
    }

    private static void determinePersonToPay(Map<String, FinancialInfo> financialData) {
        String personToPay = null;
        double maxNetContribution = Double.NEGATIVE_INFINITY;

        for (Map.Entry<String, FinancialInfo> entry : financialData.entrySet()) {
            String person = entry.getKey();
            FinancialInfo info = entry.getValue();
            double netContribution = info.getNetContribution();

            if (netContribution >= maxNetContribution) {
                maxNetContribution = netContribution;
                personToPay = person;
            }
        }

        // Update totalSpent for the person who is paying
        financialData.get(personToPay).updateTotalSpent();

        System.out.println("\nPerson to pay today: " + personToPay);
    }

    private static void displayNetContributions(Map<String, FinancialInfo> financialData) {
        System.out.println("\nNet Contributions:");
        for (Map.Entry<String, FinancialInfo> entry : financialData.entrySet()) {
            String person = entry.getKey();
            FinancialInfo info = entry.getValue();
            double netContribution = info.getNetContribution();
            System.out.println(person + ": " + netContribution);
        }
    }
}

// Financial info to be input into to the map 
class FinancialInfo {
    private double personalDrinkCost;
    private double totalSpent;

    public FinancialInfo(double initialDrinkCost) {
        personalDrinkCost = initialDrinkCost;
        totalSpent = 0.0;
    }

    public FinancialInfo() {
        personalDrinkCost = 0.0;
        totalSpent = 0.0;
    }

    public void addExpense(double expense) {
        totalSpent += expense;
    }

    public double getNetContribution() {
    	// returns net contribution, highest net cont will be the person who pays
        return totalSpent - personalDrinkCost;
    }

    public void updateTotalSpent() {
    	// Reset drink cost 
        personalDrinkCost = 0.0; 
        totalSpent = 0.0;
    }

}
