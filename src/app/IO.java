package app;

import java.io.PrintWriter;
import java.util.Scanner;

public class IO {
    private final PrintWriter writer;
    private final Scanner scanner;

    public IO() {
        this.writer = new PrintWriter(System.out);
        this.scanner = new Scanner(System.in);
    }

    public void print(Object o) {
        this.writer.print(o);
        this.writer.flush();
    }

    public void println(Object o) {
        this.writer.print(String.valueOf(o) + "\n");
        this.writer.flush();
    }
}
