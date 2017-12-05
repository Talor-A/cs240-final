/**
 * SortedListInterface
 */
public interface SortedListInterface<T extends Comparable<? super T>> {
  public void add(T newEntry);
  public T remove(T anEntry);
  public int getPosition(T anEntry);
  public T getEntry(int position);
  public boolean contains(T anEntry);
  public T remove(int givenPosition);
  public void clear();
  public T[] toArray();
  public int getLength();
  public boolean isEmpty();
}