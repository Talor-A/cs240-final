public class POSTest {

  public static void main(String args[]) {
    POS pointOfSaleSystem = new POS();
    SortedList<String> list = new SortedList<String>();
    list.add("jello");
    list.add("hello");
    list.add("jello");
    list.add("jello");
    list.add("jello");
    list.add("jello");
    list.add("jello");
    list.add("jello");
    list.add("jello");
    System.out.println(list.getLength());
    pointOfSaleSystem.beginDay(1201);
    pointOfSaleSystem.beginDay(1202);
    pointOfSaleSystem.beginDay(1203);
    pointOfSaleSystem.beginDay(1204);
  }
}
