package support;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWorks {
    private final static String FILE_DIR = "files/";

    private File file;
    private FileWriter fileWriter;

    public FileWorks(String fileName) throws IOException{
        this.file = new File(FILE_DIR + fileName + ".txt");

        if (this.file.exists()){
            this.file.delete();
        }

        this.file.createNewFile();

        this.fileWriter = new FileWriter(file);
    }

    public void writeLineToFile(String line) throws  IOException{
        fileWriter.write(line);
    }

    public File getFile(){
        return this.file;
    }
}
