public class Queue<T> implements QueueInterface<T> {
  Node front;
  Node back;
  public Queue(){
    front = null;
    back = null;
  }
  public void enqueue(T newEntry){
    Node newNode = new Node(newEntry,back);
    back = newNode;
  }

  public T dequeue(){
    if (isEmpty()) {
      throw new Error("list is empty!");
    } 







    
    //=================================
    //NOT IN GITHUB
    else if(front == back) {
      T data = (T)front.getData();
      front = null;
      back = null;
      return data;
    }
    //=================================











    else {
      Node testNode = back;
      boolean stop=false;
      while (!stop) {
        if (testNode.getLink() == front) {
          stop=true;
        } else {
          testNode = testNode.getLink();
        }
      }
      front = testNode;
      testNode.setLink(null);
      return (T)testNode.getData();
    }
  }

  public T getFront(){
    return (T)front.getData();
  }

  public boolean isEmpty(){
    return front == null && back == null;
  }

  public void clear(){
    front = null;
    back = null;
  }

}