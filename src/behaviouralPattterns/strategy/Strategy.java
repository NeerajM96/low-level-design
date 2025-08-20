package behaviouralPattterns.strategy;

/*
 * ==================================================================================================================
 * Version 2: Strategy Pattern
 * ==================================================================================================================
 *
 * Strategy Pattern: It defines what strategy we are going to use i.e. it defines a family of algorithms, puts each
 * of them in a separate(stand-alone) class and makes their object interchangeable.
 *              It is about how we change the behaviour of an object at runtime w/o changing its class.
 *
 * Problem it solves: In version 1, we are using if else to switch b/w strategies but in future if e.g. 10 more
 * strategies comes, then we will have to write 10 more if-else which is not scalable
 *
 * When to use: 1) We have multi-interchange algorithms e.g. HFT firms.
 * 2) We want to follow OCP.
 * 3) We want to avoid if-else or switch.
 * 4) We want to isolate unit-testing behaviour-wise.
 * 5) We want to select behaviour at runtime
 *
 * TODO: After understanding UML section watch class diagram of this video.
 * */

// Step 1: Interface
interface MatchingStrategy {
    void match(String location);
}

// Step 2: Standalone strategy classes
class NearestDriverStrategy implements MatchingStrategy {
    @Override
    public void match(String location) {
        System.out.println("Matching with the Nearest Driver to " + location);
        // Distance based matching logic
    }
}

class SurgePriorityStrategy implements MatchingStrategy {
    @Override
    public void match(String location) {
        System.out.println("Matching using Surge Priority for " + location);
        // prioritize high-surge zones or premium drivers
    }
}

class AirportQueueStrategy implements MatchingStrategy {
    @Override
    public void match(String location) {
        System.out.println("Matching using FIFO Airport Queue for " + location);
        // Match first in line driver for airport pickup
    }
}

// Step 3: Service
class RideMatchingService{
    private MatchingStrategy strategy;
    public RideMatchingService(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(MatchingStrategy strategy) {
        this.strategy = strategy;
    }

    public void matchRider(String location) {
        strategy.match(location);
    }
}

public class Strategy{
    public static void main(String[] args) {
        RideMatchingService service = new RideMatchingService(new SurgePriorityStrategy());
        service.matchRider("Mall");

        RideMatchingService service2 = new RideMatchingService(new AirportQueueStrategy());
        service2.matchRider("Auditorium");
        service2.setStrategy(new NearestDriverStrategy());
        service2.matchRider("Villa");
    }
}

/*
* ==================================================================================================================
* Version 1: W/o Strategy Pattern
* ==================================================================================================================
* */
/**
class RideMatchingService{
    public void matchRider(String riderLocation, String matchingType){
        if(matchingType.equals("NEAREST")){
            // Find nearest drivers
        }
        else if (matchingType.equals("SURGE_QUEUE")){
            // Match rider based on surge logic
        }
        else if (matchingType.equals("AIRPORT_QUEUE")){
            // Use FIFO airport queue logic
        }
    }
}

public class Strategy {
}
*/