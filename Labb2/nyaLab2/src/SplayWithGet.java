public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {

    private void zig(Entry x) {
        Entry y = x.right;
        E temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.right = y.right;
        if (x.right != null)
            x.right.parent = x;
        y.right = y.left;
        y.left = x.left;
        if (y.left != null)
            y.left.parent = y;
        x.left = y;
    }

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
        // Get children and change element between first and last
        Entry y = x.right;
        Entry z = x.right.right;
        E e = x.element;
        x.element = z.element;
        z.element = e;

        x.right = z.right;
        if (z.right != null) {
            z.right.parent = x;
        }

        y.right = z.left;
        if (z.left != null) {
            z.left.parent = y;
        }

        z.right = y.left;
        if (y.left != null) {
            y.left.parent = z;
        }

        z.left = x.left;
        if (x.left != null) {
            x.left.parent = z;
        }

        y.left = z;
        x.left = y;
    }

    private void zagzag(Entry x) {
        // Get children and change element between first and last
        Entry y = x.left;
        Entry z = x.left.left;
        E e = x.element;
        x.element = z.element;
        z.element = e;

        x.left = z.left;
        if (z.left != null) {
            z.left.parent = x;
        }

        y.left = z.right;
        if (z.right != null) {
            z.right.parent = y;
        }

        z.left = y.right;
        if (y.right != null) {
            y.right.parent = z;
        }

        z.right = x.right;
        if (x.right != null) {
            x.right.parent = z;
        }

        y.right = z;
        x.right = y;
    }

    /**
     * Given the element, if it's not added, adds it to the map or else it find it and sorts it.
     *
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
//            // t ändras efter sort så måste spara elementet för return.
//            E compElem = t.element;
//            sort(t);
            return t == null ? null : t.element;
        }
    }

    /**
     * Sorts the entry according to the splay rules
     *
     * @param t The entry to be sorted
     */
    public void sort(Entry t) {
        E compElem = t.element;
        while (root.element != compElem) {
            if (t.parent != root) {
                if (t.parent.parent.right != null) {
                    if (t.equals(t.parent.parent.right.right)) {
                        zigzig(t.parent.parent);
                        t = t.parent.parent;
                    } else if (t.equals(t.parent.parent.right.left)) {
                        zagzig(t.parent.parent);
                        t = t.parent;
                    } else if (t.equals(t.parent.parent.left.left)) {
                        zagzag(t.parent.parent);
                        t = t.parent.parent;
                    } else if (t.equals(t.parent.parent.left.right)) {
                        zigzag(t.parent.parent);
                        t = t.parent;
                    }
                } else if (t.parent.parent.left != null) {
                    if (t.equals(t.parent.parent.left.left)) {
                        zagzag(t.parent.parent);
                        t = t.parent.parent;
                    } else if (t.equals(t.parent.parent.left.right)) {
                        zigzag(t.parent.parent);
                        t = t.parent;
                    } else if (t.equals(t.parent.parent.right.right)) {
                        zigzig(t.parent.parent);
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

    /**
     * Starter method for find.
     *
     * @param elem Element to search for.
     * @param t    Current entry that's being looked at.
     * @return Entry if found, else null.
     */
    public Entry find(E elem, Entry t) {
        if (t == null) {
            return null;
        } else {
            int jfr = elem.compareTo(t.element);
            if (jfr < 0) {
                return find(elem, t.left, t);
            } else if (jfr > 0) {
                return find(elem, t.right, t);
            } else {
                return t;
            }
        }
    }

    /**
     * Recursive method for finding an element in a tree and splaying the most recent accessed entry.
     *
     * @param elem Element to search for.
     * @param t    Current entry being looked at.
     * @param prev Previous entry.
     * @return Entry if found, else null.
     */
    public Entry find(E elem, Entry t, Entry prev) {
        if (t == null) {
            sort(prev);
            return null;
        } else {
            int jfr = elem.compareTo(t.element);
            if (jfr < 0) {
                return find(elem, t.left, t);
            } else if (jfr > 0) {
                return find(elem, t.right, t);
            } else {
                sort(t);
                return t;
            }
        }
    }
}