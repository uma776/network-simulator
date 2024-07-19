import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream fileIStream = new FileInputStream("simulation1.txt");
        Scanner inputFS = new Scanner(fileIStream);
        LinkedEventList eList = new LinkedEventList();
        SimpleHost[] addresses = new SimpleHost[10];   //arraylist for addresses; easier to access later

        int tempAddress;
        tempAddress = inputFS.nextInt();     //reading in values work
        SimpleHost mainHost = new SimpleHost();
        mainHost.setHostParameters(tempAddress, eList);
        addresses[mainHost.getHostAddress()] = mainHost;   //add address at its index in arraylist

        tempAddress = inputFS.nextInt();
        //read in hosts(addresses) and distance btw them
        while((inputFS.hasNextLine()) && (tempAddress != -1)) {    //read line by line and until sentinel val(-1)
            SimpleHost tempHost = new SimpleHost();
            tempHost.setHostParameters(tempAddress, eList);
            addresses[tempHost.getHostAddress()] = tempHost;

            int distance = inputFS.nextInt();    //read-in 2nd int(distance)
            tempHost.addNeighbor(mainHost, distance);    //connect tempHost to mainHost and vice versa
            mainHost.addNeighbor(tempHost, distance);

            tempAddress = inputFS.nextInt();
        }

        //read in sender, receiver, timer start, timer end
        //populate LinkedEventList with initial timers
        while(inputFS.hasNextLine()){   //if next line exists
            int sender, receiver, iterTime, endTime;    //read in values of commands
            sender = inputFS.nextInt();
            receiver = inputFS.nextInt();
            iterTime = inputFS.nextInt();
            endTime = inputFS.nextInt();

            addresses[sender].sendPings(receiver, iterTime, endTime);  //sendPings creates timers

        }

        while(eList.size() > 0){   //handles all events in linkedEventList
            eList.removeFirst().handle();
        }
    }
}
