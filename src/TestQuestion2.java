import java.util.SortedMap;
import java.util.TreeMap;
import question_2.AVLTreeMap;

/**
 * A class that tests the {@link AVLTreeMap} implementation.
 */
public class TestQuestion2 {

  public static void main(String[] args) {
    SortedMap<Integer, String> avlTreeMap = new AVLTreeMap<>();

    // add some key-value mappings
    avlTreeMap.put(3, "three");
    avlTreeMap.put(1, "one");
    avlTreeMap.put(4, "four");
    avlTreeMap.put(2, "two");

    // print the sorted order of the map
    System.out.println("Sorted order of AVLTreeMap:");
    for (Integer key : avlTreeMap.keySet()) {
      System.out.println(key + " -> " + avlTreeMap.get(key));
    }

    // test other methods
    System.out.println("First key in AVLTreeMap: " + avlTreeMap.firstKey());
    System.out.println("Last key in AVLTreeMap: " + avlTreeMap.lastKey());
    System.out.println("Value for key 2: " + avlTreeMap.get(2));
    System.out.println("Value for key 5: " + avlTreeMap.getOrDefault(5, "default"));
    System.out.println("Remove key 3: " + avlTreeMap.remove(3));
    System.out.println("Size of AVLTreeMap: " + avlTreeMap.size());

    // compare with TreeMap implementation
    SortedMap<Integer, String> treeMap = new TreeMap<>();
    treeMap.put(3, "three");
    treeMap.put(1, "one");
    treeMap.put(4, "four");
    treeMap.put(2, "two");
    System.out.println("Sorted order of TreeMap:");
    for (Integer key : treeMap.keySet()) {
      System.out.println(key + " -> " + treeMap.get(key));
    }
    System.out.println("Are AVLTreeMap and TreeMap equal? " + avlTreeMap.equals(treeMap));
  }
}
