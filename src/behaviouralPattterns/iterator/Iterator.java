package behaviouralPattterns.iterator;
/*
====================================================================================================================
 Version 2: Iterator pattern
====================================================================================================================

Behavioural Pattern: Focus on how objects interact and communicate with each other, helping to define the flow of
control in a system ( i.e. who is going to do what in a group of objects). These patterns simplify complex
communication logic b/w objects while promoting loose coupling.

Iterator Pattern: It extracts the traversal behaviour of a collection into a separate design pattern. It traverses
the elements w/o exposing the underlying operation.
                The key idea is to encapsulate all traversal behavior within a dedicated iterator object. This allows
multiple iterators to independently traverse the same collection without interfering with each other. The client
simply uses the iterator and should not write or know the internal logic for iteration.

Typically, client shouldn't be aware of anything e.g. assume YouTube playlist, what is the traversal algorithm, what
kind of data structure they are using to store videos etc. because we do not want the traversal behaviour to be
written by the client, or we do not want it to be inside the YouTube playlist class.
        Whereas in version-1 we were able to get the next video by running a for loop through YouTube playlist
videos, but client should not be knowing this, we were running for loop to iterate over videos in playlist because
we(client) knows that playlist is a list of videos, but client should not be aware of anything internally.
        However, this tightly couples the client with the internal structure. The Iterator Pattern solves this by
providing a unified interface to iterate, regardless of how the collection is implemented internally.

The client should be able to play the next video using an iterator. The traversal logic (e.g., moving to the next
video) should not be hardcoded by the client or buried inside the playlist class.

When to use: 1) You want to traverse a collection w/o exposing its internal structure.
2) You need multiple ways to traverse a collection(e.g. skip copyright videos while iterating)
3) You want a unified way to traverse different types of collections.
4) You want to decouple iteration logic from collection logic.

Pros: SRP & OCP followed
Cons: 1) Adds extra classes / interfaces
2) Client has to manage the loop using hasNext() & next() unless abstracted further.

*/

import java.util.ArrayList;
import java.util.List;

interface PlaylistIterator{
    Boolean hasNext();
    Video next();
}

// iterable interface
interface Playlist{
    // iterator 1
    PlaylistIterator createIterator();
    // // iterator 2
    //PlaylistIterator createCopyrightIterator();
}

class Video{
    String title;

    public Video(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }
}

class YouTubePlaylist implements Playlist{
    private List<Video> videos = new ArrayList<>();

    public void addVideo(Video video){
        videos.add(video);
    }

    @Override
    public PlaylistIterator createIterator() {
        return new YouTubePlaylistIterator(videos);
    }
}

// concrete iterator - traversal algo 1
class YouTubePlaylistIterator implements PlaylistIterator{
    private List<Video> videos;
    private int position;

    public YouTubePlaylistIterator(List<Video> videos){
        this.videos=videos;
        this.position=0;
    }

    @Override
    public Boolean hasNext(){
        return position < videos.size();
    }

    @Override
    public Video next(){
        return hasNext() ? videos.get(position++) : null;
    }
}

public class Iterator {
    public static void main(String[] args) {
        YouTubePlaylist playlist = new YouTubePlaylist();
        playlist.addVideo(new Video("LLD Tutorial"));
        playlist.addVideo(new Video("System Designs Basics"));

        PlaylistIterator iterator = playlist.createIterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next().getTitle());
        }
    }
}



/*
 * ===============================================================================================================\
 * Version 1: W/O Iterator pattern
 * ===============================================================================================================
 * */

/**
import java.util.ArrayList;
import java.util.List;

class Video{
    String title;

    public Video(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }
}

class YouTubePlaylist{
    private List<Video> videos = new ArrayList<>();

    public void addVideo(Video video){
        videos.add(video);
    }

    public List<Video> getVideos(){
        return videos;
    }
}

public class Iterator {
    public static void main(String[] args) {
        YouTubePlaylist playlist = new YouTubePlaylist();
        playlist.addVideo(new Video("LLD Tutorial"));
        playlist.addVideo(new Video("System Designs Basics"));

        for (Video video : playlist.getVideos()){
            System.out.println(video.getTitle());
        }
    }
}
**/