import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int money;
    private Map<String, Integer> sales;
    private Map<String, Integer> prices;
    private Map<String, Map<String, Integer>> ingredients;

    public CoffeeMachine() {
        water = 0;
        milk = 0;
        beans = 0;
        money = 0;

        sales = new HashMap<>();
        sales.put("Espresso", 0);
        sales.put("Latte", 0);
        sales.put("Cappuccino", 0);

        prices = new HashMap<>();
        prices.put("Espresso", 4);
        prices.put("Latte", 7);
        prices.put("Cappuccino", 6);

        ingredients = new HashMap<>();
        Map<String, Integer> espressoIngredients = new HashMap<>();
        espressoIngredients.put("water", 250);
        espressoIngredients.put("milk", 0);
        espressoIngredients.put("beans", 16);

        Map<String, Integer> latteIngredients = new HashMap<>();
        latteIngredients.put("water", 350);
        latteIngredients.put("milk", 75);
        latteIngredients.put("beans", 20);

        Map<String, Integer> cappuccinoIngredients = new HashMap<>();
        cappuccinoIngredients.put("water", 200);
        cappuccinoIngredients.put("milk", 100);
        cappuccinoIngredients.put("beans", 12);

        ingredients.put("Espresso", espressoIngredients);
        ingredients.put("Latte", latteIngredients);
        ingredients.put("Cappuccino", cappuccinoIngredients);
    }

    public void showMenu() {
        System.out.println("=== Coffee Machine Simulator ===");
        System.out.println("Menu:");
        for (String coffee : prices.keySet()) {
            System.out.println("- " + coffee + ": $" + prices.get(coffee) + "/cup");
        }
        System.out.println();
    }

    public void buyCoffee(String coffeeType) {
        if (!prices.containsKey(coffeeType)) {
            System.out.println("Invalid coffee type!");
            return;
        }

        Map<String, Integer> coffeeIngredients = ingredients.get(coffeeType);

        if (water < coffeeIngredients.get("water")) {
            System.out.println("Sorry, not enough water.");
            return;
        }

        if (milk < coffeeIngredients.get("milk")) {
            System.out.println("Sorry, not enough milk.");
            return;
        }

        if (beans < coffeeIngredients.get("beans")) {
            System.out.println("Sorry, not enough coffee beans.");
            return;
        }

        System.out.println("Dispensing " + coffeeType + "...");
        water -= coffeeIngredients.get("water");
        milk -= coffeeIngredients.get("milk");
        beans -= coffeeIngredients.get("beans");
        money += prices.get(coffeeType);
        sales.put(coffeeType, sales.get(coffeeType) + 1);
        System.out.println("Enjoy your coffee!");
    }

    public void fillMachine(int addedWater, int addedMilk, int addedBeans) {
        water += addedWater;
        milk += addedMilk;
        beans += addedBeans;
        System.out.println("Machine has been refilled.");
    }

    public void takeMoney() {
        System.out.println("Collecting $" + money + " from the machine.");
        money = 0;
    }

    public void showStatus() {
        System.out.println("Current Machine Status:");
        System.out.println("Water: " + water + " ml");
        System.out.println("Milk: " + milk + " ml");
        System.out.println("Coffee Beans: " + beans);
        System.out.println("Money: $" + money);
        System.out.println();
    }

    public void showAnalytics() {
        System.out.println("Sales Analytics:");
        for (String coffee : sales.keySet()) {
            System.out.println(coffee + ": " + sales.get(coffee) + " cups sold");
        }
        int totalEarnings = sales.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * prices.get(entry.getKey()))
                .sum();
        System.out.println("Total Earnings: $" + totalEarnings);
        int totalWater = sales.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * ingredients.get(entry.getKey()).get("water"))
                .sum();
        int totalMilk = sales.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * ingredients.get(entry.getKey()).get("milk"))
                .sum();
        int totalBeans = sales.entrySet().stream()
                .mapToInt(entry -> entry.getValue() * ingredients.get(entry.getKey()).get("beans"))
                .sum();
        System.out.println("Total Ingredients Consumed: Water - " + totalWater + " ml, Milk - " + totalMilk + " ml, Beans - " + totalBeans);
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("===== Coffee Machine Simulator =====");
            System.out.println("Options:");
            System.out.println("1. Show Menu");
            System.out.println("2. Buy Coffee [Espresso, Latte, Cappuccino]");
            System.out.println("3. Fill Machine");
            System.out.println("4. Take Money");
            System.out.println("5. Show Machine Status");
            System.out.println("6. Show Sales Analytics");
            System.out.println("7. Exit");
            System.out.print("Select an option (1-7): ");
            int option = scanner.nextInt();

            if (option == 1) {
                showMenu();
            } else if (option == 2) {
                System.out.print("Enter the coffee type (Espresso, Latte, Cappuccino): ");
                String coffeeType = scanner.next();
                buyCoffee(coffeeType);
            } else if (option == 3) {
                System.out.print("Enter the amount of water to add: ");
                int water = scanner.nextInt();
                System.out.print("Enter the amount of milk to add: ");
                int milk = scanner.nextInt();
                System.out.print("Enter the amount of coffee beans to add: ");
                int beans = scanner.nextInt();
                fillMachine(water, milk, beans);
            } else if (option == 4) {
                takeMoney();
            } else if (option == 5) {
                showStatus();
            } else if (option == 6) {
                showAnalytics();
            } else if (option == 7) {
                exit = true;
            } else {
                System.out.println("Invalid option! Please try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.start();
    }
}