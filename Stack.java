public class Stack<T> implements StackInterface<T> {
  private Node top = null;
  public void clear() {
    top = null;
  }
  public boolean isEmpty () {
    return top == null; //TODO FIX IN GITHUB
  }
  public void push(T newEntry) {
    Node newNode = new Node(newEntry, top);
    top = newNode;
  }
  public T pop () {
    Node temp = top;
    top = temp.getLink();
    return (T)temp.getData();
  }
  public T peek () {
    return (T)top.getData();
  }
}