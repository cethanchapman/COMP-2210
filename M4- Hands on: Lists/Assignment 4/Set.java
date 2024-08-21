import java.util.*;

public interface Set<T> extends Iterable<T>

{

   boolean add(T element);

   boolean remove(T element);

   boolean contains(T element);

   int size();

   boolean isEmpty();

   boolean equals(Set<T> s);

   Set<T> union(Set<T> s);

   Set<T> intersection(Set<T> s);

   Set<T> complement(Set<T> s);

   Iterator<T> iterator();

}