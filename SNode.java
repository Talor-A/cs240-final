/**
 * class implementing a single-linked node
 * @author Talor Anderson
 * @version 1.0
 */
 public class SNode<T> {
  T data;
  SNode link;
  
  public SNode() {
    this(null,null);
  }
  public SNode(T d) {
    this(d, null);
  }
  public SNode(T d, SNode l){
    data = d;
    link = l;
  }
  public void setData(T newData)      { data = newData; }
  public void setLink(SNode newLink) { link = newLink; }

  public T getData()      { return data; }
  public SNode getLink() { return link; }
}