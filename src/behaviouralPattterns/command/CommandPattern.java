package behaviouralPattterns.command;

/*
 * =================================================================================================================
 * Version 2: Command Pattern
 * =================================================================================================================
 *
 * Command Pattern: It turns a request into a separate object, allowing you to decouple the code that issues request
 * from the code that performs it.(i.e. we are segregating the command out and the invoker out)
 *              This lets us add features like undo, redo, logging and dynamic command execution w/o changing the
 * core business logic
 *
 * Four key components: 1) Client -> 2) Invoker(remote) -> 3) Command -> 4) Receiver
 *
 * Invoker should not care about receivers, it should only know that for this particular button, I will be invoking
 * this particular command.
 *
 * When to use: 1) We need to decouple sender from the receiver
 * 2) We need undo/redo support
 * 3) We need batch operations
 * 4) We want plug-in architecture
 * 5) We want to create macros or composite commands.
 *
 * */

import java.util.Stack;

// Receivers
class Light{
    public void on(){
        System.out.println("Turning light on");
    }

    public void off(){
        System.out.println("Turning light off");
    }
}

class AC{
    public void on(){
        System.out.println("Turning AC on");
    }
    public void off(){
        System.out.println("Turning AC off");
    }
}

interface Command{
    void execute();
    void undo();
}

// Concrete commands
class LightOnCommand implements Command{
    private Light light;

    public LightOnCommand(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
    @Override
    public void undo() {
        light.off();
    }
}

class LightOffCommand implements Command{
    private Light light;
    public LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }
    @Override
    public void undo() {
        light.on();
    }
}

class ACOnCommand implements Command{
    private AC ac;
    public ACOnCommand(AC ac){
        this.ac = ac;
    }
    @Override
    public void execute() {
        ac.on();
    }
    @Override
    public void undo() {
        ac.off();
    }
}

class ACOffCommand implements Command{
    private AC ac;
    public ACOffCommand(AC ac){
        this.ac = ac;
    }
    @Override
    public void execute() {
        ac.off();
    }

    @Override
    public void undo() {
        ac.on();
    }
}

// Invoker
// 4 commands
class RemoteControl{
    private Command[] buttons = new Command[4];
    private Stack<Command> commandHistory = new Stack<>();

    public void setCommand(int slot, Command command){
        buttons[slot] = command;
    }

    public void pressButton(int slot){
        if(buttons[slot] != null){
            buttons[slot].execute();
            commandHistory.push(buttons[slot]);
        }
        else{
            System.out.println("No command assigned to slot " + slot);
        }
    }

    public void pressUndo(){
        if(!commandHistory.isEmpty()){
            Command command = commandHistory.pop();
            command.undo();
        }
        else{
            System.out.println("No commands to undo");
        }
    }
}

public class CommandPattern {

    public static void main(String[] args) {
        Light light = new Light();
        AC ac = new AC();

       Command lightOn = new LightOnCommand(light);
       Command lightOff = new LightOffCommand(light);
       Command acOn = new ACOnCommand(ac);
       Command acOff = new ACOffCommand(ac);

       RemoteControl remote = new RemoteControl();
       remote.setCommand(0, lightOn);
       remote.setCommand(1, lightOff);
       remote.setCommand(2, acOn);
       remote.setCommand(3, acOff);

       remote.pressButton(0); // Light ON
       remote.pressButton(2); // AC ON
       remote.pressButton(1); // Light OFF

       remote.pressUndo(); // Undo Light OFF => Light ON
       remote.pressUndo(); // UNDO AC ON => AC OFF
    }
}
/*
 * =================================================================================================================
 * Version 1: W/o Command Pattern
 * =================================================================================================================
 *
 * We use RemoteController(middleware) because we do not want the client to directly interact with the device or
 * receiver(we don't want the client to create instance of light and then turn off/on)
 *
 * Problems: 1) NativeRemoteControl is tightly coupled with the device/receivers.
 * 2) Commands are tightly coupled to the remote, which makes it non-scalable, so we need to segregate the commands
 * so that if in future we have additional commands(eg pressLightOn, pressLightOff, pressACOff, pressACOn) or
 * devices then we can easily integrate them.
 *
 * */

/**
// Receivers
class Light{
    public void on(){
        System.out.println("Turning light on");
    }

    public void off(){
        System.out.println("Turning light off");
    }
}

class AC{
    public void on(){
        System.out.println("Turning AC on");
    }
    public void off(){
        System.out.println("Turning AC off");
    }
}

class NaiveRemoteControl{
    private Light light;
    private AC ac;
    private String lastAction;

    public NaiveRemoteControl(Light light, AC ac){
        this.light = light;
        this.ac = ac;
    }

    public void pressLightOn(){
        light.on();
        lastAction = "LIGHT_ON";
    }

    public void pressLightOff(){
        light.off();
        lastAction = "LIGHT_OFF";
    }

    public void pressACOn(){
        ac.on();
        lastAction = "AC_ON";
    }

    public void pressACOff(){
        ac.off();
        lastAction = "AC_OFF";
    }

    public void undo(){
        switch (lastAction){
            case "LIGHT_ON": light.off(); lastAction = "LIGHT_OFF"; break;
            case "LIGHT_OFF": light.on(); lastAction = "LIGHT_ON"; break;
            case "AC_ON": ac.on(); lastAction = "AC_ON"; break;
            case "AC_OFF": ac.off(); lastAction = "AC_OFF"; break;
            default: System.out.println("No action to undo"); break;
        }
    }
}

public class CommandPattern {

    public static void main(String[] args) {
        Light light = new Light();
        AC ac = new AC();

        NaiveRemoteControl remote = new NaiveRemoteControl(light, ac);
        remote.pressACOn();
        remote.pressACOff();
        remote.undo();
        remote.pressLightOff();
        remote.undo();
    }
}
*/