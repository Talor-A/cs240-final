/**
 * ListInterface
 */
public interface ListInterface<T> {
  public void add(T newEntry);
  public void add(int position, T newEntry);
  public T remove(int position);
  public void clear();
  public T replace(int position, T newEntry);
  public T getEntry(int position);
  public T[] toArray();
  public boolean contains(T anEntry);
  public int getLength();
  public boolean isEmpty();
}