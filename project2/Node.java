public class Node {
    private final Event value;
    public Node next;
    public Node prev;

    public Node(Event value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
    public Event getValue() {
        return this.value;
    }
}
