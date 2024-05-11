//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package app.message;

import app.server.Handler;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private final String sender;
    private final Handler handler;

    public Message(String message, String sender, Handler handler) {
        this.message = message;
        this.sender = sender;
        this.handler = handler;
    }

    public Message(Handler handler) {
        this.message = null;
        this.sender = null;
        this.handler = handler;
    }

    public String getMessage() {
        return this.message;
    }

    public String getSender() {
        return this.sender;
    }

    public String toString() {
        return this.sender != null ? this.sender + ": " + this.message : this.message;
    }

    public Handler getHandler() {
        return this.handler;
    }
}
