package structuralPatterns.proxy;

/*
 * ===============================================================================================================\
 * Version 2: Proxy pattern
 * ===============================================================================================================
 *
 * Proxy Pattern: Provides a surrogate or placeholder for another object to control access to it.
 * e.g. CEO is busy so in his place his assistance takes calls, filters emails, manages calendar etc and only
 * involves CEO when necessary.
 *
 * We do not want to overload the service of the Actual class(RealVideoDownloader) with functionalities like
 * caching, access control, filtering etc (because doing so will violate the SRP), so we will transfer the
 * functionalities to the Proxy Layer.
 *
 * */

import java.util.HashMap;
import java.util.Map;

// Step 1: Interface
interface VideoDownloader{
    String downloadVideo(String videoUrl);
}

// Step 2: Actual Downloader
class RealVideoDownloader implements VideoDownloader{
    @Override
    public String downloadVideo(String videoUrl){
        System.out.println("Downloading video from "+videoUrl);
        return "Video content from" + videoUrl;
    }
}

// Step 3: Proxy with cache
class CachedVideoDownloader implements VideoDownloader{
    private RealVideoDownloader realVideoDownloader;
    private static Map<String,String> cache = new HashMap<>();
    public CachedVideoDownloader(){
        this.realVideoDownloader = new RealVideoDownloader();
    }

    @Override
    public String downloadVideo(String videoUrl){
        if(cache.containsKey(videoUrl)){
            System.out.println("Returning cached video for: " + videoUrl);
            return cache.get(videoUrl);
        }
        else{
            System.out.println("Cache miss. Downloading video ...");
            String video = realVideoDownloader.downloadVideo(videoUrl);
            cache.put(videoUrl,video);
            return video;
        }
    }
}

public class Proxy {
    public static void main(String[] args) {
        CachedVideoDownloader cachedVideoDownloader = new CachedVideoDownloader();
        cachedVideoDownloader.downloadVideo("https://www.google.com/proxy-pattern");

        CachedVideoDownloader cachedVideoDownloader2 = new CachedVideoDownloader();
        cachedVideoDownloader.downloadVideo("https://www.google.com/proxy-pattern");


    }
}

/*
* ===============================================================================================================\
* Version 1: W/O Proxy pattern
* ===============================================================================================================
*
* */

/**
class RealVideoDownloader{
    public String downloadVideo(String videoUrl){
        // Issue: no caching, no filtering, no access control
        System.out.print("Downloading video from "+videoUrl);
        return "Video content from" + videoUrl;
    }
}

public class Proxy {
    public static void main(String[] args) {
        RealVideoDownloader realVideoDownloader = new RealVideoDownloader();
        realVideoDownloader.downloadVideo("https://www.google.com/proxy-pattern");

        RealVideoDownloader realVideoDownloader2 = new RealVideoDownloader();
        realVideoDownloader2.downloadVideo("https://www.google.com/proxy-pattern");
    }
}
*/