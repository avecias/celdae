/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.event;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avecias
 */
interface HelloListener {
    void someoneSaidHello();
}

// Someone who says "Hello"
class Initiater {
    private List<HelloListener> listeners = new ArrayList<HelloListener>();
    public void addListener(HelloListener toAdd) {
        listeners.add(toAdd);
    }
    public void sayHello() {
        System.out.println("Hello!!");

        // Notify everybody that may be interested.
        for (HelloListener hl : listeners) {
            hl.someoneSaidHello();
        }
    }
}

// Someone interested in "Hello" events
class Responder implements HelloListener {
    @Override
    public void someoneSaidHello() {
        System.out.println("Hello there...");
    }
}

class TestListener {
    public static void main(String[] args) throws InterruptedException {
        Initiater initiater = new Initiater();
        Responder responder = new Responder();

        initiater.addListener(responder);
        while (true) {
            Thread.sleep(2 * 1000);
            initiater.sayHello();  // Prints "Hello!!!" and "Hello there..."
        }
    }
}
