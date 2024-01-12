package quest.flo;

import sun.misc.Signal;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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

            Signal.handle(new Signal("INT"), signal -> shutdown(logger, new Runner[]{a, b}));
            Signal.handle(new Signal("TERM"), signal -> shutdown(logger, new Runner[]{a, b}));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdown(Logger logger, Runner[] runners) {
        try {
            logger.writeLine("Shutting down Program!", new Date(), "Main");
            for (Runner runner : runners) {
                runner.shutdown();
                runner.join();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}