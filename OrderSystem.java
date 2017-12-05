import java.util.Random;
/*
  
  MENU:
  1 Burger                Bun, Patty, Lettuce, Tomato, Onion
  2 Cheeseburger          Bun, Patty, Lettuce, Tomato, Onion, Cheese
  3 Vegan Lettuce Wrap    Lettuce x2, Tomato, Onion
  4 Burger no Onion       Bun, Patty, Lettuce, Tomato
  5 Cheeseburger no Onion Bun, Patty, Lettuce, Tomato, Cheese
  6 Burger no Tomato      Bun, Patty, Lettuce, Onion
  
  FOOD ITEMS:
  0 Bun
  1 Patty
  2 Lettuce
  3 Tomato
  4 Onion
  5 Cheese
  */
public class OrderSystem {
  private Queue customerQueue;
  private Random rng;
  private Stack[] inventory;
  private int nextShipment;

  OrderSystem() {
    customerQueue = new Queue<Integer>();
    rng = new Random();
    nextShipment = -1;
    inventory = new Stack[6];
    for (int i = 0; i < inventory.length; i++) {
      inventory[i] = new Stack<Integer>();
    }
  }

  public int addCustomers() {
    int customerCount = rng.nextInt(51) + 50;
    // System.out.println("customercount: " + customerCount);
    for (int i = 0; i < customerCount; i++) {
      customerQueue.enqueue(i);
    }
    return customerCount;
  }

  public Day startDay(int date) {
    Day today = new Day(date);
    if (this.nextShipment == -1) {
      this.takeShipment(date);
      this.nextShipment = getNextShipmentDate(date);
    }
    if (this.nextShipment == date) {
      System.out.println("Shipment day!");
      this.takeShipment(date);
      this.nextShipment = getNextShipmentDate(date);
    }

    for (int i = 0; i < 10; i++) {
      takeCustomerOrders(today);
    }
    this.checkExpiringItems(today);
    return today;
  }

  public void checkExpiringItems(Day today) {
    for (int currentType = 0; currentType < inventory.length; currentType++) {
      boolean stop = false;
      Stack<Integer> foodStack = inventory[currentType];
      while (!stop) {
        if (foodStack.isEmpty()) {
          stop = true;
        } else {
          int expirationDate = foodStack.peek();
          if (today.date + 1 <= expirationDate) {
            foodStack.pop();
            System.out.println("threw away food!");
            today.addWasteItem(currentType);
          } else {
            stop = true;
          }
        }
      }
    }
  }

  public void takeShipment(int date) {
    int shipmentSize = rng.nextInt(301) + 700;
    Stack<Integer>[] tempStack = new Stack[6];
    for (int i = 0; i < tempStack.length; i++) {
      tempStack[i] = new Stack<Integer>();
    }
    for (int i = 0; i < tempStack.length; i++) {
      while (!inventory[i].isEmpty()) {
        tempStack[i].push((int) inventory[i].pop());
      }
    }
    int count = 0;

    for (int itemNumber = 0; itemNumber < shipmentSize; itemNumber++) {
      int itemToAdd = rng.nextInt(6);
      inventory[itemToAdd].push(date);
      count++;
    }

    for (int i = 0; i < tempStack.length; i++) {
      while (!tempStack[i].isEmpty()) {
        inventory[i].push(tempStack[i].pop());
        count++;
      }
    }
    System.out.println("recieved " + count + " items.");
  }

  public int getNextShipmentDate(int fromDate) {
    return fromDate + rng.nextInt(3) + 3;
  }

  public void takeCustomerOrders(Day currentDay) {
    int customersLost = 0;
    int customersInLine = addCustomers();
    currentDay.totalCustomers += customersInLine;
    if (customersInLine > 50) {
      customersLost += customersInLine - 50;
      customersInLine = 50;
    }
    while (!customerQueue.isEmpty() && customersInLine < 0) {
      int customer = (int) customerQueue.dequeue();
      int order = rng.nextInt(6) + 1;
      boolean wasServed = takeOrder(order, currentDay);
      if (wasServed) {
        currentDay.addOrderedItem(order);
      } else {
        currentDay.addLostCustomer();
      }
    }
    while (!customerQueue.isEmpty()) {
      customerQueue.dequeue();
      currentDay.addLostCustomer();
    }
    // System.out.println("Customers lost: " + customersLost);
  }

  /*
  Orders => inventory index
  1 Burger                0, 1, 2, 3, 4
  2 Cheeseburger          0, 1, 2, 3, 4, 5
  3 Vegan Lettuce Wrap    2, 2, 3, 4
  4 Burger no Onion       0, 1, 2, 3
  5 Cheeseburger no Onion 0, 1, 2, 3, 5
  6 Burger no Tomato      0, 1, 2, 4
  
  */
  List<Integer>[] buildMenuItems() {
    List<Integer>[] menu = new List[6];



    return menu;
  }

  public boolean takeOrder(int orderNumber, Day today) {

    int[] orderIngredients;
    switch (orderNumber) {
    case 1: //Burger
      orderIngredients = new int[] { 0, 1, 2, 3, 4 };
      break;
    case 2:
      orderIngredients = new int[] { 0, 1, 2, 3, 4, 5 };
      break;
    case 3:
      orderIngredients = new int[] { 2, 2, 3, 4 };
      break;
    case 4:
      orderIngredients = new int[] { 0, 1, 2, 3 };
      break;
    case 5:
      orderIngredients = new int[] { 0, 1, 2, 3, 5 };
      break;
    case 6:
      orderIngredients = new int[] { 0, 1, 2, 4 };
      break;
    default:
      orderIngredients = new int[0];
      break;
    }
    for (int ingredient : orderIngredients) {
      Stack<Integer> ingredStack = inventory[ingredient];
      if (ingredStack.isEmpty()) {
        return false; // not served successfully
      }
      ingredStack.pop();
    }
    return true;
  }

  public boolean shouldThrowAway(int ingredient, int itemDate, int todaysDate) {
    int[] shelfLives = new int[] { 5, 4, 3, 3, 5, 2 };
    return todaysDate - shelfLives[ingredient] > itemDate;
  }

  class Day {
    public int customersLost;
    public int totalCustomers;
    public int date;
    private int[] wasteItems;
    private int[] orderedItems;
    String[] itemNames;

    Day(int date) {
      this.customersLost = 0;
      this.totalCustomers = 0;
      this.date = date;
      this.wasteItems = new int[6];
      this.orderedItems = new int[6];
      this.itemNames = new String[] { "Bun", "Patty", "Lettuce", "Tomato", "Onion", "Cheese" };
    }

    public void addOrderedItem(int orderNumber) {
      orderNumber--; //off-by-one
      this.orderedItems[orderNumber] = this.orderedItems[orderNumber] + 1;
    }

    public void addWasteItem(int item) {
      wasteItems[item] = wasteItems[item] + 1;
    }

    public int getWaste(int i) {
      return wasteItems[i];
    }

    public void addLostCustomer() {
      customersLost++;
    }

    public void print() {
      System.out.println("Total Customers: " + this.totalCustomers);
      System.out.println("Customers Lost:  " + this.customersLost);
      System.out.println("Orders:");
      for (int i = 0; i < orderedItems.length; i++) {
        System.out.println(" " + i + ": " + orderedItems[i]);
      }
      // System.out.println("Waste Items:");
      // for (int i = 0; i < itemNames.length; i++) {
      //   System.out.println(itemNames[i] + ": " + getWaste(i));
      // }
    }
  }
}