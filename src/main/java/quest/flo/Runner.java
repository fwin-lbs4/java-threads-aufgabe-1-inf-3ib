package quest.flo;

import java.io.IOException;
import java.util.Date;

public class Runner extends Thread {
    Logger logger;
    private boolean running = true;

    public Runner(String name, Logger logger) {
        this.setName(name);
        this.logger = logger;
    }

    public void run() throws RuntimeException {
        try {
            while (this.running) {
                System.out.println(this.getName() + " wrote to Log");
                this.logger.writeLine("Message from " + this.getName(), new Date(), this.getName());

                Thread.sleep(1000);
            }
            this.logger.writeLine("Shutting down Thread " + this.getName(), new Date(), this.getName());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        this.running = false;
    }
}
