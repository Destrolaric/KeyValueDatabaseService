package com.destrolaric.keyvaluestorage.repository;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@PropertySource("classpath:config.properties")
public class DumpFileDAO {

    @Value("${file.path}")
    private String dumpfile;

    @PostConstruct
    public void createFile() {
        try {
            File dump = new File(dumpfile);
            if (dump.createNewFile()) {
                System.out.println("File created: " + dump.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void dumpToFile(List<?> objects) {
        try (
                OutputStream file = new FileOutputStream(dumpfile);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            output.writeObject(objects);
        }
        catch(IOException ex){
            logger.log(Level.SEVERE, "Cannot perform output.", ex);
        }
    }

    public List<TimedKeyValue> getFromFile() {
        try (
                InputStream file = new FileInputStream(dumpfile);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
            //deserialize the List
            return (List<TimedKeyValue>) input.readObject();
        } catch (ClassNotFoundException ex) {
            logger.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot perform input.", ex);
        }

        return null;
    }
    private static final Logger logger =
            Logger.getLogger(DumpFileDAO.class.getPackage().getName())
            ;
}