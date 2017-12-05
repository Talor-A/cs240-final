//HAS BEEN MODIFIED

public class SortedList<T extends Comparable<? super T>> implements SortedListInterface<T> {
  SNode front = null;
  SNode back = null;

  @Override
  public void add(T newEntry) {
    SNode newNode =  new SNode();
    newNode.setData(newEntry);

    if(back == null) {
      back = newNode;
      front = newNode;
    } else {

      if (newEntry.compareTo((T)back.getData()) > 0) {
        back.setLink(newNode);
        back = newNode;
      } else if (newEntry.compareTo((T)front.getData()) < 0) {
        newNode.setLink(front);
        front = newNode;
      } else {

        SNode test = front;

        while(test.getLink()!= null && newEntry.compareTo((T)test.getLink().getData()) < 0) {
          test = test.getLink();
        }
        newNode.setLink(test.getLink());
        test.setLink(newNode);
      }
    }
  }
  public SNode getNodeAt(int position) {
    SNode current = front;
    for (int count = 1; count < position; count++) {
      current = current.getLink();
    }
    return current;
  }
  @Override
  public T remove(T anEntry) {
    remove(getPosition(anEntry));
    return anEntry;
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

  @Override
  public boolean isEmpty() {
    return front == null;
  }
  
  @Override
  public int getLength(){
    if (isEmpty()) return 0;
    int count = 1;
    SNode current = front;
    while(current.getLink() != null) {
      count++;
      current = current.getLink();
    }
    return count;
  }
  @Override
  public void clear() {
    front = null;
    back = null;
  }
  @Override
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
  @Override
  public boolean contains(T anEntry) {
    SNode current = front;
    while (current != null) {
      if ((T)current.getData() == anEntry)
        return true;
      current = current.getLink();
    }
    return false;
  }
  @Override
  public T getEntry(int position) {
    return (T)getNodeAt(position).getData();
  }
  @Override
  public int getPosition(T anEntry) {
      SNode current = front;
      int pos = 0;
      while(current != null) {
        if(anEntry == (T)current.getData()){
          return pos;
        } 
        current = current.getLink();
      }
      return -1;
  }
}