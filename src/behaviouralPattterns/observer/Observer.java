package behaviouralPattterns.observer;

/*
 * ===============================================================================================================\
 * Version 2: Observer pattern
 * ===============================================================================================================
 * */

/**
 * Observer Pattern: It defines one to many dependency b/w objects so that when one object changes state, all its
 * dependants are notified and updated accordingly
 *
 * Problem it is solving: When a lot of people are observing(subscribers) and, we(YT creator) do a change, they get
 * notified
 *
 * When to use: 1) A change in one object should automatically notify others.
 * 2) You want to decouple the subjects from the observers
 * 3) Dynamic subscription / unsubscription
 *
 * When to avoid: 1)
 */


/*
 * ===============================================================================================================\
 * Version 1: W/O Observer pattern
 *
 * Problems: 1) Violating SRP (method has an additional responsibility of sending notifications)
 * 2) We haven't given user to turn on/off the notification bell option.
 * ===============================================================================================================
 * */
class YouTubeChannel{
    public void uploadNewVideo(String videoTile){
        System.out.println("Uploading: "+ videoTile);

        // Manually notify subscribers
        System.out.println("Sending email to user1@mail.com");
        System.out.println("Pushing in-app notifications to user1@mail.com");

        System.out.println("Sending email to user2@mail.com");
        System.out.println("Pushing in-app notifications to user2@mail.com");
    }
}
public class Observer {
}
