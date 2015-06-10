/**
 * Created by Cale Gibson (͡° ͜ʖ͡°)
 * on 02/03/15.
 */
public class DictEntry<K, V> {

    //Private member variables
    private K k;
    private V v;

    /**
     * Constructor
     *
     * @param key Key entry
     * @param value Value entry
     */
    public DictEntry(K key, V value)
    {
        k = key;
        v = value;
    }

    //Getter for key
    public K key()
    {
        return k;
    }

    //Getter for value
    public V value()
    {
        return v;
    }

    //Method to change value variable
    public void changeValue(V newVal)
    {
        v = newVal;
    }


}
