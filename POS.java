import java.util.Iterator;
import java.util.Random;

/**

 * Point of Sale Class
 */
public class POS {
  Queue<Integer> customerQueue;
  private int shipmentArrivalDate;
  private List[] menu;
  private Random rng;
  Stack<Integer> bun;
  Stack<Integer> patty;
  Stack<Integer> lettuce;
  Stack<Integer> tomato;
  Stack<Integer> onion;
  Stack<Integer> cheese;

  POS() {
    customerQueue = new Queue<Integer>();
    shipmentArrivalDate = -1;
    this.rng = new Random();
    this.menu = new List[6];

    this.patty = new Stack<Integer>();
    this.bun = new Stack<Integer>();
    this.lettuce = new Stack<Integer>();
    this.tomato = new Stack<Integer>();
    this.onion = new Stack<Integer>();
    this.cheese = new Stack<Integer>();

    List burger = new List<Stack>();
    List cheeseBurger = new List<Stack>();
    List vegan = new List<Stack>();
    List burgerNoOnion = new List<Stack>();
    List cheeseBurgerNoOnion = new List<Stack>();
    List burgerNoTomato = new List<Stack>();

    menu[0] = burger;
    menu[1] = cheeseBurger;
    menu[2] = vegan;
    menu[3] = burgerNoOnion;
    menu[4] = cheeseBurgerNoOnion;
    menu[5] = burgerNoTomato;

    burger.add(lettuce);
    burger.add(bun);
    burger.add(patty);
    burger.add(tomato);
    burger.add(onion);

    cheeseBurger.add(onion);
    cheeseBurger.add(cheese);
    cheeseBurger.add(lettuce);
    cheeseBurger.add(tomato);
    cheeseBurger.add(patty);
    cheeseBurger.add(bun);

    vegan.add(lettuce);
    vegan.add(lettuce);
    vegan.add(tomato);
    vegan.add(onion);

    burgerNoOnion.add(lettuce);
    burgerNoOnion.add(patty);
    burgerNoOnion.add(tomato);
    burgerNoOnion.add(bun);

    cheeseBurgerNoOnion.add(cheese);
    cheeseBurgerNoOnion.add(bun);
    cheeseBurgerNoOnion.add(lettuce);
    cheeseBurgerNoOnion.add(tomato);
    cheeseBurgerNoOnion.add(patty);

    burgerNoTomato.add(patty);
    burgerNoTomato.add(lettuce);
    burgerNoTomato.add(bun);
    burgerNoTomato.add(onion);

  }

  public void beginDay(int todaysDate) {
    int lostCustomerDay = 0;
    int[] wasteItems = new int[6];
    String[] wasteItemNames = new String[] { "patty", "bun", "lettuce", "tomato", "onion", "cheese" };
    int[] orders = new int[6];
    Dictionary customerDictionary = new Dictionary<Integer, Integer>();
    int servedCustomerNumber = 1;

    //------------------------------------------  SHIPMENTS
    if (this.shipmentArrivalDate == todaysDate || this.shipmentArrivalDate == -1) {
      System.out.println("Shipment day!");
      this.takeShipment(todaysDate);
      this.shipmentArrivalDate = calcNextShipmentDate(todaysDate);
    }

    int caughtA = 0;
    int caughtB = 0;
    //------------------------------------------  HOURLY OPERATION
    for (int hour = 0; hour < 10; hour++) {

      //----------------------------------------  ADD CUSTOMERS
      int customerCount = rng.nextInt(100) + 1;
      // System.out.println("arrived customers: "+customerCount);
      if (customerCount > 50) {
        lostCustomerDay = +(customerCount - 50);
        // System.out.println("customers turned away: " + lostCustomerDay);
        customerCount = 50;
      }
      for (int i = 0; i < customerCount; i++) {
        int customerOrderNumber = rng.nextInt(6) + 1;
        customerQueue.enqueue(customerOrderNumber);
      }
      //----------------------------------------  TAKE ORDERS
      while (!customerQueue.isEmpty()) {
        int orderNumber = (int) customerQueue.dequeue();

        List menuItem = getItemForOrderNumber(orderNumber);
        boolean outOfIngredients = false;

        int i = 0;
        // special case for lettuce
        // if (orderNumber == 3) {
        //   if (this.lettuce.isEmpty()) {
        //     outOfIngredients = true;
        //   } else {
        //     int item = this.lettuce.pop();
        //     if (this.lettuce.isEmpty()) {
        //       outOfIngredients = true;

        //     }
        //     this.lettuce.push(item);
        //   }
        // }
        // Check for empty
        while (!outOfIngredients && i < menuItem.getLength()) {
          Stack ingredientStack = (Stack) menuItem.getEntry(i);
          if (ingredientStack.isEmpty()) {
            outOfIngredients = true;
            caughtA++;
            break;
          }
          i++;
        }
        i = 0;
        //remove
        while (!outOfIngredients && i < menuItem.getLength()) {
          Stack ingredientStack = (Stack) menuItem.getEntry(i);
          if (ingredientStack.isEmpty()) {
            // System.out.println("err!");
            outOfIngredients = true;
            caughtB++;
            break;
          }
          ingredientStack.pop();
          i++;
        }

        if (outOfIngredients) {
          lostCustomerDay++;
        } else {
          orders[orderNumber - 1] = orders[orderNumber - 1] + 1;
          customerDictionary.add(servedCustomerNumber, orderNumber);
          servedCustomerNumber++;
        }

      }

    }
    //------------------------------------------  DISPOSE OF WASTE
    // Stack[] inventory = new Stack[] { this.patty, this.bun, this.lettuce, this.tomato, this.onion, this.cheese };
    // for (int i = 0; i < inventory.length; i++) {
    //   boolean stop = false;
    //   Stack itemStack = inventory[i];

    //   while (!itemStack.isEmpty() && !stop) {
    //     if ((int) itemStack.peek() < todaysDate) {
    //       itemStack.pop();
    //       wasteItems[i] = wasteItems[i] + 1;
    //     }
    //   }
    // }

    System.out.println("Lost Customers today: " + lostCustomerDay);
    for (int i = 0; i < wasteItems.length; i++) {
      System.out.println("waste " + wasteItemNames[i] + ": " + wasteItems[i]);
    }
    System.out.println("Orders:");
    for (int i = 0; i < wasteItems.length; i++) {
      System.out.println("#" + (i + 1) + ": " + orders[i]);
    }

    Iterator keyIter = customerDictionary.getKeyIterator();
    Iterator valIter = customerDictionary.getValueIterator();
    System.out.println("Caught A: " + caughtA + ", caught B: " + caughtB);
    System.out.println("Customer Dictionary: " + customerDictionary.getSize() + " customers ");

    while (valIter.hasNext()) {

      System.out.println(" " + keyIter.next() + " => " + valIter.next());
    }

  }

  public List getItemForOrderNumber(int orderNumber) {
    return this.menu[orderNumber - 1];
  }

  public int calcNextShipmentDate(int todaysDate) {
    return todaysDate + rng.nextInt(3) + 3;

  }

  public void takeShipment(int todaysDate) {
    Stack[] inventory = new Stack[] { this.patty, this.bun, this.lettuce, this.tomato, this.onion, this.cheese };
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
      int expirationDate = todaysDate + getShelfLife(itemToAdd);
      inventory[itemToAdd].push(todaysDate + expirationDate);
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

  int getShelfLife(int itemIndex) {
    int[] shelfLives = new int[] { 5, 4, 3, 3, 5, 2 };
    return shelfLives[itemIndex];
  }
}