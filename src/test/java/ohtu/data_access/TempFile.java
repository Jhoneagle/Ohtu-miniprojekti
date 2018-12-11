package ohtu.data_access;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class TempFile {
    protected File tempDatabase;
    protected Database database;

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    public void setUp() {
        try {
            tempDatabase = tempFolder.newFile("test.db");
            String databaseAddress = "jdbc:sqlite:"+tempDatabase.getAbsolutePath();
            
            database = new Database(databaseAddress, true);
        } catch (IOException ex) {
            System.out.println("Failed to use database in tests!");
        }
    }
    
    @After
    public void restore() {
        if (tempDatabase != null) {
            this.tempDatabase.delete();
        }
    }
}
