import java.util.*;

/**
 * Created by Cale Gibson
 * on 02/03/15.
 */
public class AVLTree<K, V> implements AVLTreeInterface {

    //Private variables
    private AVLnode root = null;
    private Comparator comp = null;
    private int _size = 0;
    private ArrayList<DictEntry<K,V>> _iterArrayList;

    /**
     * Default constructor for tree
     *
     * @param inputComparator Comparator type
     */
    public AVLTree(Comparator inputComparator)
    {
        comp = inputComparator;
        root = null;
    }

    /**
     * Check if node is External
     *
     * @param p Position Object
     * @return True if p is external, false otherwise
     */
    @Override
    public boolean external(Position p) {
        AVLnode node = (AVLnode) p;
        if(null == p)
            return true;
        if(node.left() == null && node.right() == null)
            return true;
        else
            return false;
    }

    /**
     * Get left node from parent node
     *
     * @param p Position Object
     * @return The position of the left child of p. If p does not have a left child, returns null.
     */
    @Override
    public Position left(Position p) {
        AVLnode node = (AVLnode) p;
        if(node.left() != null)
            return node.left();
        else
            return null;
    }

    /**
     * Get the right node from parent node
     *
     * @param p
     * @return The position of the right child of p. If p does not have a right child, returns null.
     */
    @Override
    public Position right(Position p) {
        AVLnode node = (AVLnode) p;
        if(node.right() != null)
            return node.right();
        else
            return null;
    }


    /**
     * Search the tree for a specific key
     *
     * @param key The key to search for
     * @param node The tree root
     * @return The node with the corresponding key
     */
    private AVLnode TreeSearch(Object key, AVLnode node)
    {
        if(node == null)
            return null;

        int j = comp.compare(key, node.element().key());

        if(j < 0)
        {
            return TreeSearch(key, node.left());
        }
        else if(j > 0)
        {
            return TreeSearch(key, node.right());
        }
        else
            return node;
    }


    /**
     * Search tree for a specific key
     *
     * @param node Root node
     * @param key The key to search for
     * @return The entry of the found node
     */
    private DictEntry treeFind(AVLnode node, Object key)
    {
        if(node == null)
            return null;

        int j = comp.compare(key, node.element().key());

        if(j < 0)
        {
            return treeFind(node.left(), key);
        }
        else if(j > 0)
        {
            return treeFind(node.right(), key);
        }
        else
            return node.getEntry();

    }

    @Override
    public DictEntry find(Object key) {
        return treeFind(root, key);
    }

    /**
     * Find the min node
     *
     * @param node The root node
     * @return the smalled left node
     */
    private AVLnode findMin(AVLnode node)
    {
        if(node == null)
            return null;
        else if(node.left() == null)
            return node;
        return findMin(node.left());
    }


    /**
     * Remove node from tree
     *
     * @param key Key to be removed
     * @param node The root node
     * @return The removed node
     */
    private AVLnode removeItem(Object key, AVLnode node)
    {
        if(node == null)
            return node;
        if(comp.compare(key, node.element().key()) < 0 )
        {
            node.setLeft(removeItem(key, node.left()));
        }
        else if(comp.compare(key, node.element().key()) > 0)
        {
            node.setRight(removeItem(key, node.right()));
        }
        else if(node.left() != null && node.right() != null)
        {
            node.setEntry(findMin(node.right()).element());
            node.setRight(removeItem(node.element().key(), node.right()));
        }
        else
        {
            node = (node.left() != null) ? node.left() : node.right();
        }
        return node;
    }

    @Override
    public DictEntry remove(Object key) throws AVLTreeException {
        DictEntry d = treeFind(root, key);
        if(d == null)
            throw new AVLTreeException("No such key");
        root = removeItem(key, root);
        _size--;
        return d;
    }

    @Override
    public void modifyValue(Object key, Object valueNew) throws AVLTreeException {
        AVLnode node;
        node = TreeSearch(key, root);
        if(node == null)
            throw new AVLTreeException("No such key");

        node.getEntry().changeValue(valueNew);

    }

    /**
     * Insert node into the tree
     *
     * @param dict The DictEntry to be inserted
     * @param node The root node
     * @return Newly inserted node
     */
    private AVLnode insertItem(DictEntry dict, AVLnode node)
    {
        if(node == null)
        {
            node = new AVLnode(dict, null, null, null);
        }
        else if(comp.compare(dict.key(), node.element().key()) < 0)
        {
            node.setLeft(insertItem(dict, node.left()));
        }
        else if(comp.compare(dict.key(), node.element().key()) > 0)
        {
            node.setRight(insertItem(dict, node.right()));
        }
        else
        {

        }
        return node;
    }

    @Override
    public void insertNew(Object key, Object value) throws AVLTreeException {
        DictEntry d = treeFind(root, key);
        if(d != null)
        {
            throw new AVLTreeException("Key already exists");
        }
        DictEntry entry = new DictEntry(key, value);
        root = insertItem(entry, root);
        _size++;
    }


    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position giveRoot() {
        return root;
    }


    /**
     * Get the tree height
     *
     * @param node The root node
     * @return Tree height
     */
    private int getTreeHeight(AVLnode node)
    {
        if(node == null)
            return -1;
        int left = getTreeHeight(node.left());
        int right = getTreeHeight(node.right());

        if(left > right)
            return left + 1;
        else
            return right + 1;

    }

    @Override
    public int treeHeight() {
        int height = getTreeHeight(root);
        return height;
    }

    /**
     * Tree Iterator helper method
     *
     * @param node Root node
     */
    private void treeIterHelper(AVLnode node)
    {
        if(node != null)
        {
            treeIterHelper(node.left());
            _iterArrayList.add(node.element());
            treeIterHelper(node.right());
        }
    }

    @Override
    public Iterator<DictEntry<K,V>> inOrder() {
        _iterArrayList = new ArrayList<DictEntry<K,V>>();
        treeIterHelper(root);
        Iterator<DictEntry<K,V>> treeIter = _iterArrayList.iterator();
        return treeIter;
    }

    @Override
    public int size() {
        return _size;
    }
}
