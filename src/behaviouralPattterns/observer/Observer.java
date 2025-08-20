package behaviouralPattterns.observer;

/*
 * ===============================================================================================================\
 * Version 2: Observer pattern
 * ===============================================================================================================
 * */

import java.util.ArrayList;
import java.util.List;

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
 * When to avoid: 1) We have too many observers(e.g. celebrity goes live with around 10M followers and sending out
 * a live notification to this many followers at a time can take our system down). In such cases we can use
 * event-queues, pub-sub to handle the traffic.
 *
 * 2) Tight control over notification timings is required. when making a purchase on Amazon, the system must trigger
 * services in a specific sequence — billing → notification → seller → delivery. If any one of these steps fails, it
 * must retry before moving on to the next. Such strict sequencing and error-handling requirements are not suitable
 * for the Observer pattern, which is designed for loosely coupled, asynchronous notifications without guaranteed
 * ordering.
 *      So, use message brokers to publish events in this case.
 *
 * KIM: Observer pattern works really well with small number of observers, but to scale, we need to move to event
 * driven architecture.
 *
 * Real life use-case: On snapchat user has around 100 friends and when he sends a snap then all these 100 friends
 * gets notified. But in case of celebrity we should move to event-driven architecture
 *
 * TODO: write pros-cons from LLD videos side by side in a tabular format
 */

// Step 1: Interface to notify trigger notifications.
interface Subscriber{
    void update(String videoTitle);
}

// Step 2: Concrete observer
class EmailSubscriber implements Subscriber{
    private String email;
    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("Email sent to " + email + ": New video uploaded - " + videoTitle);
    }
}

class MobileSubscriber implements Subscriber{
    private String username;
    public MobileSubscriber(String username) {
        this.username = username;
    }

    @Override
    public void update(String videoTitle) {
        System.out.println("In app notification for " + username + ": New video uploaded - " + videoTitle);
    }
}

// Step 3: Interface Subject
interface Channel{
    void subscribe(Subscriber subscriber);
    void unsubscribe(Subscriber subscriber);
    void notifySubscribers(String videoTitle);
}

// Step 4: Concrete Subject
class YoutubeChannel implements Channel{
    private String channelName;
    private List<Subscriber> subscribers = new ArrayList<>();

    public YoutubeChannel(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String videoTitle) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(this.channelName);
        }
    }

    public void uploadVideo(String videoTitle) {
        System.out.println(channelName + " Video uploaded - " + videoTitle);
        notifySubscribers(videoTitle);
    }
}

public class Observer{
    public static void main(String[] args) {
        YoutubeChannel tuf = new YoutubeChannel("takeuforward");
        tuf.subscribe(new EmailSubscriber("raj"));
        tuf.subscribe(new MobileSubscriber("rahul"));
        tuf.uploadVideo("observer-pattern");
    }
}

/*
 * ===============================================================================================================\
 * Version 1: W/O Observer pattern
 *
 * Problems: 1) Violating SRP (method has an additional responsibility of sending notifications)
 * 2) We haven't given user to turn on/off the notification bell option.
 * ===============================================================================================================
 * */
/**
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
*/