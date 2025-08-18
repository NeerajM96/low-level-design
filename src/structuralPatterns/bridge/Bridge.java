package structuralPatterns.bridge;

/*
 * ===============================================================================================================\
 * Version 2: Bridge pattern
 * ===============================================================================================================
 * Problem it solves: Initially, we had separate classes for each device type (Web, Mobile, TV) for HD quality.
 * If a new video quality is introduced (e.g., 4K), we would need to create three more classes — one for each device.
 * Similarly, if a new device type is added, we’d have to create classes for all existing qualities for that device.
 * This leads to a class explosion and results in tightly coupled components.
 *
 *In a structural design pattern, components should be loosely coupled to ensure scalability and maintainability.
 *
 * Steps: Interface VideoQuality -> Classes for Quality -> Abstract class Player -> Classes for type of VideoPlayer
 *
 * When to use: 1) You have to dimensions (tightly coupled, so decouple them and communicate using Bridge pattern)
 * 2) You want to evolve independently e.g. we can keep on adding number of qualities and number of players.
 * 3) You want to avoid class explosion.
 *
 * */

interface VideoQuality{
    void load(String title);
}

class SDQuality implements VideoQuality{
    public void load(String title) {
        System.out.println("Streaming " + title + " in SD Quality");
    }
}

class HDQuality implements VideoQuality{
    public void load(String title) {
        System.out.println("Streaming " + title + " in HD Quality");
    }
}

class UltraHDQuality implements VideoQuality{
    public void load(String title) {
        System.out.println("Streaming " + title + " in 4K Ultra HD Quality");
    }
}

class K8Quality implements VideoQuality{
    public void load(String title) {
        System.out.println("Streaming " + title + " in 8K Quality");
    }
}

abstract class VideoPlayer {
    protected VideoQuality quality;

    public VideoPlayer(VideoQuality quality) {
        this.quality = quality;
    }
    public abstract void play(String title);
}

class WebPlayer extends VideoPlayer {
    public WebPlayer(VideoQuality quality) {
        super(quality);
    }

    @Override
    public void play(String title) {
        System.out.println("Web Player:");
        quality.load(title);
    }
}

class MobilePlayer extends VideoPlayer {
    public MobilePlayer(VideoQuality quality) {
        super(quality);
    }

    @Override
    public void play(String title) {
        System.out.println("Mobile Player:");
        quality.load(title);
    }
}

public class Bridge{
    public static void main(String[] args) {
        VideoPlayer videoPlayer = new WebPlayer(new HDQuality());
        videoPlayer.play("Video 1");

        VideoPlayer videoPlayer2 = new MobilePlayer(new K8Quality());
        videoPlayer2.play("Video 2");

    }
}

/*
 * ===============================================================================================================\
 * Version 1: W/O Bridge pattern
 * ===============================================================================================================
 *
 * */

/**

interface PlayQuality{
    void play(String title);
}

class WebHDPlayer implements PlayQuality{
    @Override
    public void play(String title) {
        System.out.println("Web Player: Playing " + title + " in HD");
    }
}

class MobileHDPlayer implements PlayQuality{
    @Override
    public void play(String title) {
        System.out.println("Mobile Player: Playing " + title + " in HD");
    }
}

class SmartTVUltraHDPlayer implements PlayQuality{
    @Override
    public void play(String title) {
        System.out.println("Smart TV Player: Playing " + title + " in ultra HD");
    }
}

class Web4KPlayer implements PlayQuality{
    @Override
    public void play(String title) {
        System.out.println("Web Player: Playing " + title + " in 4K");
    }
}

public class Bridge {
}

 */