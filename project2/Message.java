public class Message extends Event {
    private String message;   //differentiates btw request/response
    private int srcAddress;
    private int destAddress;

    private int distance;
    private Host destinationHost;

    public Message (){    // default constructor, never used
        this.destinationHost = null;
        this.message = null;
        this.srcAddress = -1;
        this.destAddress = -1;
    }

    public Message(int sourceAddress, int destAddress, String message){
        this.srcAddress = sourceAddress;
        this.message = message;
        this.destinationHost = null;  //set by setNextHop when sendToNeighbor is called
        this.destAddress = destAddress;  //set by setNextHop
    }

    public void setInsertionTime(int currentTime){
        this.insertionTime = currentTime;
        this.arrivalTime = currentTime + this.distance; //orders messages into linkedEventList
    }

    public void cancel(){
        System.out.println("Message's cancel() called");
    }

    @Override
    public void handle(){
        this.destinationHost.receive(this);
    }

    public String getMessage(){
        return this.message;
    }

    public int getSrcAddress(){
        return this.srcAddress;
    }

    public int getDestAddress(){
        return this.destAddress;
    }


    public void setNextHop(Host destHost, int distance){
        //switch source address and neighbor address, used in project 3
        this.destinationHost = destHost;
        this.distance = distance;
    }
}
