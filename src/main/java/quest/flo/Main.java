package quest.flo;

import sun.misc.Signal;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File myFile = new File("logs.txt");
            String message = "";
            if (myFile.createNewFile()) {
                message = "Created new Log-File";
            }
            Logger logger = new Logger(myFile, message);
            Runner a = new Runner("A", logger);
            Runner b = new Runner("B", logger);

            a.start();
            b.start();

            Signal.handle(new Signal("INT"), signal -> {
                shutdownThread(a);
                shutdownThread(b);
                shutdown(logger);
            });
            Signal.handle(new Signal("TERM"), signal -> {
                shutdownThread(a);
                shutdownThread(b);
                shutdown(logger);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdownThread(Runner runner) {
        try {
            runner.shutdown();
            runner.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void shutdown(Logger logger) {
        try {
            logger.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}