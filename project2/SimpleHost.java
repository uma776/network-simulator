public class SimpleHost extends Host {


    private int intervalTimerId;
    private int finalTimerId;

    private int destAdd;
    private int interval;

    private int startRtt;

    private int finalRtt;


    protected void receive(Message msg){
        //figure out is msg received is ping request or ping response and output according
        //create new message of opposite type

        switch(msg.getMessage()){
            case "req":{   //request
                System.out.println("[" + getCurrentTime() + "ts] Host " + msg.getDestAddress() + ": Ping request from host " + msg.getSrcAddress());

                Message res = new Message(msg.getDestAddress(), msg.getSrcAddress(), "res");
                sendToNeighbor(res);  //sets destination host and distance and inserts message into list(which also setsInsertionTime)
                break;
            }
            case "res":{   //response
                finalRtt = getCurrentTime() - startRtt;   //endRtt - startRtt
                System.out.println("[" + getCurrentTime() + "ts] Host " + msg.getDestAddress() + ": Ping response from host " + msg.getSrcAddress() + " (RTT = " + finalRtt + "ts)");
                break;
            }
            default:{
                System.out.println("Something wrong in SimpleHost receive()");
                break;
            }
        }
    }

    protected void timerExpired(int eventId){  // create new timer and output accordingly
        if(eventId == intervalTimerId){   //interval timer expired
            this.intervalTimerId = newTimer(this.interval);
            this.startRtt = getCurrentTime();

            Message req = new Message(this.getHostAddress(), this.destAdd, "req");
            sendToNeighbor(req);

            System.out.println("[" + getCurrentTime() + "ts] Host " + this.getHostAddress() + ": Sent ping to host " + this.destAdd);
        }
        else if(eventId == finalTimerId){   //final timer expired
            System.out.println("[" + getCurrentTime() + "ts] Host " + getHostAddress() + ": Stopped sending pings");
            timerCancelled(this.intervalTimerId);  //cancelled extra interval timer
        }
    }

    protected void timerCancelled(int eventId){
        cancelTimer(eventId);  //calls method in host class which removes timer
    }

    public void sendPings(int destArr, int interval, int duration){
        int intervalId = newTimer(interval);  //create a delay timer to delay ping message; iteration timer
        int finalId = newTimer(duration); //create final timer
        setTimerIds(intervalId, finalId);
        setParametersToRepeatTimer(destArr, interval);
    }

    private void setTimerIds(int intervalId, int finalId){
        this.intervalTimerId = intervalId;
        this.finalTimerId = finalId;
    }

    private void setParametersToRepeatTimer(int destinationAddress, int interval){
        this.destAdd = destinationAddress;
        this.interval = interval;
    }

}
