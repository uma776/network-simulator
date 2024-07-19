public class LinkedEventList implements FutureEventList{
    private Node head;
    private int size = 0;

    public int simTime = 0;

    public LinkedEventList(){
        this.head = null;
        this.size = 0;
    }

    public Event removeFirst(){
        if(head == null){   //if list is empty, return null(default value of a node)
            return null;
        }

        simTime = head.getValue().getArrivalTime();   // update simTime everytime an event is handled

        Event temp = head.getValue();
        if(head.next == null){ //head is only node in the list
            head = null;
        }
        else{   //remove head and set next node to head
            head = head.next;
            head.prev = null;
        }

        this.size--;

        return temp;
    }

    public boolean remove(Event e){
        if(head == null){ //if list is empty
            return false;
        }

        Node first = head;   //if list is not empty
        boolean found = false;

        while(first != null){    //go through list to find e
            if (first.getValue() == e) {  //if e found, then break and first = node with value of e
                found = true;
                break;
            }
            first = first.next;
        }

        if(found){
            if(first == head){
                head.prev = null;
                head = first.next;
            }
            else{   //still works if first is tail
                first.prev.next = first.next;
            }
            this.size--;
        }

        return found;
    }

    public void insert(Event e) {
        Node newNode = new Node(e);
        e.setInsertionTime(getSimulationTime());

        if (head == null) {  //newNode is first in list
            head = newNode;
        }
        else{   //newNode is not first in list
            //newNode goes before head so no need to search rest of list
            if (e.getArrivalTime() < head.getValue().getArrivalTime()) {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            else{  //newNode goes after head so need to search list
                Node before = head;
                Node after = before.next;

                //find node before and after according to arrival times
                while ((after != null) && (after.getValue().getArrivalTime() < e.getArrivalTime())) {
                    before = after;
                    after = after.next;
                }

                // insert newNode between node after and node before
                newNode.next = after;
                newNode.prev = before;
                before.next = newNode;
                if (after != null) {  //only do this if newNode is not being inserted at end
                    after.prev = newNode;
                }
            }
        }

        this.size++;
    }

    public int size(){
        return this.size;
    }

    public int capacity(){
        return this.size;
    }

    public int getSimulationTime(){
        return simTime;
    }

}
