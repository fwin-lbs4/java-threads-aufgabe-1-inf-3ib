package quest.flo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    File file;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:mm");

    public Logger(File file, String initialMessage) throws IOException {
        this.file = file;
        if (initialMessage != null) {
            this.writeLine(initialMessage, new Date(), "Logger");
        }
    }

    public synchronized void writeLine(String msg, Date time, String origin) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true))) {
            writer.write(this.formatter.format(time) + "|" + origin + "|" + msg);
            writer.newLine();
        }
    }

    public void shutdown() throws IOException {
        this.writeLine("Shutting down Program!", new Date(), "Logger");
    }
}
