package structuralPatterns.flyweight;

/*
* ===============================================================================================================\
* Version 2: Flyweight pattern
* ===============================================================================================================
*
* Flyweight pattern: It is used to minimize memory usage or computational cost by sharing as much data as possible
* with similar objects.
*
* It separates the intrinsic(shared) state from the extrinsic(unique) state, so that shared parts of objects are
* stored only once and resued whenever needed.
*
* Think of it as a data re-use pattern, if many objects are similar, store their common data in one place and share
* it across instances
*
* We use combination of Factory pattern + caching/pooling (sometimes with Singleton)
* Steps: Move intrinsic(shared) properties to a separate class and memoize the object created by this separate
* class by using Factory pattern.
*
* When to use: 1) When need to create a large no. of similar objects
* 2) When memory & performance optimization is crucial(during scaling).
* 3) When the object's intrinsic properties could be shared independently of its extrinsic properties.
*
* Cons: can lead to tight coupling b/w flyweight & client code if not designed carefully
* e.g. If I change something in a similar object and Client-1 was using it then Client-1 changed something, now
* Client-2 will face issues because he is not aware that Client-1 made a change because it is a shared resource.
*
* */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Step 1: Separate out intrinsic(shared) properties to a separate class
class TreeType{
    private String name;
    private String color;
    private String texture;

    public TreeType(String name, String color, String texture){
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw(int x, int y){
        System.out.println("Drawing " + name + " tree at (" + x + "," + y + ")");
    }
}

// Step 2: create Factory to memoize the TreeType
class TreeTypeFactory{
    static Map<String, TreeType> treeTypeMap = new HashMap<>();

    public static TreeType getTreeType(String name, String color, String texture){
        String key = (name + "_" + color + "_" + texture).toLowerCase().trim();
        if(!treeTypeMap.containsKey(key)){
            treeTypeMap.put(key, new TreeType(name, color, texture));
        }
        return treeTypeMap.get(key);

    }
}

class Tree{
    private int x;
    private int y;
    private TreeType treeType;

    public Tree(int x, int y, TreeType treeType) {
        this.x = x;
        this.y = y;
        this.treeType = treeType;
    }

    public void draw(){
        treeType.draw(x, y);
    }
}

class Forest{
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture){
        Tree tree = new Tree(x, y, TreeTypeFactory.getTreeType(name, color, texture));
        trees.add(tree);
    }

    public void draw(){
        for(Tree tree:trees){
            tree.draw();
        }
    }
}

public class Flyweight {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // plant 1 million trees
        for(int i=0;i<100;i++){
            forest.plantTree(i,i,"Oak","Green","Rough");
        }

        forest.draw();
    }

}


/*
* ===============================================================================================================\
* Version 1: W/O Flyweight pattern
* ===============================================================================================================
* */

/**

import java.util.ArrayList;
import java.util.List;

class Tree{
    private int x;
    private int y;
    private String name;
    private String color;
    private String texture;

    public Tree(int x, int y, String name, String color, String texture) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.color = color;
        this.texture = texture;
    }

    public void draw(){
        System.out.println("Drawing tree at (" +x + "," + y + ") with type" + name);
    }
}

class Forest{
    private List<Tree> trees = new ArrayList<>();

    public void plantTree(int x, int y, String name, String color, String texture){
        Tree tree = new Tree(x, y, name, color, texture);
        trees.add(tree);
    }

    public void draw(){
        for(Tree tree:trees){
            tree.draw();
        }
    }
}

public class Flyweight {
    public static void main(String[] args) {
        Forest forest = new Forest();

        // plant 1 million trees
        for(int i=0;i<1000000;i++){
            forest.plantTree(i,i,"Oak","Green","Rough");
        }

        forest.draw();
    }

}
*/