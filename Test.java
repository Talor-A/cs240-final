public class Test {
  public static void main(String[] args) {
    OrderSystem orderSystem = new OrderSystem();
    int startDay = 1201;
    int endDay = 1210;
    for (int currentDay = startDay; currentDay <= endDay; currentDay++) {
      OrderSystem.Day result = orderSystem.startDay(currentDay);
      result.print();
    }

  }
}