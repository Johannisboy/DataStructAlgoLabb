public class SplayWithGet<E extends Comparable<? super E>>
        extends BinarySearchTree<E>
        implements CollectionWithGet<E> {


    /* Rotera 1 steg i vanstervarv, dvs
               x'                 y'
              / \                / \
             A   y'  -->        x'  C
                / \            / \
               B   C          A   B
     */
    private void zig(Entry x) {
        Entry y = x.right;
        y.parent = x.parent;
        x.parent = y;
        x.right = y.left;
        y.left =x ;
    }

    private void zig2(Entry x) {
        Entry y = x.right;
        y.parent = x.parent;
        x.parent = y;
        x.right = y.left;
        y.left = x;

    }

    /* Rotera 1 steg i hogervarv, dvs
                   x'                 y'
                  / \                / \
                 y'  C   -->        A   x'
                / \                    / \
               A   B                  B   C
    */
    private void zag(Entry x) {
        Entry y = x.left;
        E temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left = y.left;
        if (x.left != null)
            x.left.parent = x;
        y.left = y.right;
        y.right = x.right;
        if (y.right != null)
            y.right.parent = y;
        x.right = y;
    }

    /* Rotera 2 steg i hogervarv, dvs
               x'                  z'
              / \                /   \
             y'  D   -->        y'    x'
            / \                / \   / \
           A   z'             A   B C   D
              / \
             B   C
     */
    private void zigzag(Entry x) {
        Entry y = x.left,
                z = x.left.right;
        E e = x.element;
        x.element = z.element;
        z.element = e;
        y.right = z.left;
        if (y.right != null)
            y.right.parent = y;
        z.left = z.right;
        z.right = x.right;
        if (z.right != null)
            z.right.parent = z;
        x.right = z;
        z.parent = x;
    }

    private void zigzag2(Entry x) {
        zig(x.parent);
        zag(x.parent);
    }

    /* Rotera 2 steg i vanstervarv, dvs
               x'                  z'
              / \                /   \
             A   y'   -->       x'    y'
                / \            / \   / \
               z   D          A   B C   D
              / \
             B   C
     */
    private void zagzig(Entry x) {
        Entry y = x.right,
                z = x.right.left;
        E e = x.element;
        x.element = z.element;
        z.element = e;
        y.left = z.right;
        if (y.left != null)
            y.left.parent = y;
        z.right = z.left;
        z.left = x.left;
        if (z.left != null)
            z.left.parent = z;
        x.left = z;
        z.parent = x;
    }

    private void zigzig(Entry x) {
        zig(x.parent.parent);
        zig(x.parent);
    }

    private void zagzag(Entry x) {
        zag(x.parent.parent);
        zag(x.parent);
    }

    /**
     * Given the element, if it's not added, adds it to the map or else it find it and sorts it.
     * @param e The dummy element to compare to.
     * @return The e
     */
    @Override
    public E get(E e) {
        if (!this.contains(e)) {
            this.add(e);
            return e;
        } else {
            Entry t = find(e, root);
            E compElem = t.element;     // t ändras efter sort så måste spara elementet för return.
            sort(t);
            return t == null ? null : compElem;
        }
    }

    /**
     * Sorts the entry according to the splay rules
     * @param t The entry to be sorted
     */
    public void sort(Entry t) {
        E compElem = t.element;
        while (root.element != compElem) {
           if (t.parent != root) {
               if (t.parent.parent.right != null) {
                   if (t.equals(t.parent.parent.right.right)) {
                       zigzig(t);
                       t = t.parent;
                   } else if (t.equals(t.parent.parent.right.left)) {
                       zagzig(t.parent.parent);
                       t = t.parent;
                   }
                   else if (t.equals(t.parent.parent.left.left)) {
                       zagzag(t);
                       t = t.parent;
                   }
                   else if (t.equals(t.parent.parent.left.right)) {
                       zigzag(t.parent.parent);
                       t = t.parent;
                   }
               } else if (t.parent.parent.left != null) {
                   if (t.equals(t.parent.parent.left.left)) {
                       zagzag(t);
                       t = t.parent;
                   }
                   else if (t.equals(t.parent.parent.left.right)) {
                       zigzag(t.parent.parent);
                       t = t.parent;
                   }
                   else if (t.equals(t.parent.parent.right.right)) {
                       zigzig(t);
                       t = t.parent.parent;
                   } else if (t.equals(t.parent.parent.right.left)) {
                       zagzig(t.parent.parent);
                       t = t.parent;
                   }
               }
           } else if (t.equals(t.parent.left))
               zag(t.parent);
           else if (t.equals(t.parent.right))
               zig(t.parent);
        }
    }
}