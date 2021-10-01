package com.destrolaric.keyvaluestorage.repository;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class DumpFileRepository {

  @Value("${file.path}")
  private Path dumpfile;

  private File file;

  @PostConstruct
  public void createFile() {
    try {
      if (!Files.exists(dumpfile)) {
        Files.createFile(dumpfile);
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void dumpToFile(List<?> objects) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    String res = objectMapper.writeValueAsString(objects);
    Files.write(dumpfile,res.getBytes(StandardCharsets.UTF_8));
  }

  public List<TimedKeyValue> getFromFile() throws IOException, ClassNotFoundException {
    try {
      String jsonString = Files.readAllLines(dumpfile).get(0);
      ObjectMapper objectMapper = new ObjectMapper();
      return Arrays.asList(objectMapper.readValue(jsonString, TimedKeyValue[].class));
    } catch (IOException ex) {
      throw ex;
    }
  }

}