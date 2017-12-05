/**
 * ListSingle
 */
public class List<T> implements ListInterface<T> {
  private SNode front = null;
  private SNode back = null;

  public void add(T newEntry) {
    SNode newNode = new SNode(newEntry);
    if (isEmpty()) {
      front = newNode;
      back = newNode;
    } else {
      back.setLink(newNode);
    }
  }

  public void add(int position, T newEntry) {
    SNode newNode = new SNode(newEntry);
    if (position == 0) {
      newNode.setLink(front);
      front = newNode;
    } else {
      SNode before = getNodeAt(position);
      SNode after = before.getLink();
      before.setLink(newNode);
      newNode.setLink(after);
      if (after == null) {
        back = newNode;
      }
    }
  }

  public T remove(int position) {
    if (position == 0) {
      T data = (T)front.getData();
      front = front.getLink();
      return data;
    } else {
      SNode before = getNodeAt(position - 1);
      SNode toRemove = before.getLink();
      SNode after = toRemove.getLink();
      before.setLink(after);
      return (T)toRemove.getData();
    }
  }

  public T replace(int position, T newEntry) {
    SNode node = getNodeAt(position);
    T data = (T)node.getData();
    node.setData(newEntry);
    return data;
  }
  public T getEntry(int position) {
    return (T)getNodeAt(position).getData();
  }

  public SNode getNodeAt(int position) {
    SNode current = front;
    for (int count = 1; count < position; count++) {
      current = current.getLink();
    }
    return current;
  }

  public void clear() {
    front = null;
    back = null;
  }

  public boolean contains(T anEntry) {
    SNode current = front;
    while (current != null) {
      if ((T)current.getData() == anEntry)
        return true;
      current = current.getLink();
    }
    return false;
  }
  public int getLength() {
    int length = 0;
    SNode current = front;
    while (current != null) {
      length++;
      current = current.getLink();
    }
    return length;
  }
  public T[] toArray() {
    T[] arr = (T[])new Object[getLength()];
    int pos = 0;
    SNode current = front;
    while (pos < getLength()) {
      arr[pos] = (T)current.getData();
      current = current.getLink();
      pos++;
    }
    return arr;
  }
  public boolean isEmpty() {
    return front == null;
  }
}