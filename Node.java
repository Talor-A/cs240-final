public class Node<T> {
  private T _data;
  private Node _prev;
  Node(T data, Node previous) {
    _data = data;
    _prev = previous;
  }
  public T getData() { return _data; }
  public Node getLink() { return _prev; }
  public void setLink(Node n) { _prev = n; }
}