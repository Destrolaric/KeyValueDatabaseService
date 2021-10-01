package com.destrolaric.keyvaluestorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.destrolaric.keyvaluestorage.repository.DumpFileRepository;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DumpFileRepositoryTest {

  @Autowired
  @Spy
  DumpFileRepository dumpFileRepository;

  private final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows());

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(dumpFileRepository, "dumpfile",
        fileSystem.getPath("").resolve("dump.json"));
  }

  @Test
  public void testFileCreation() {

    Path path = fileSystem.getPath("");
    dumpFileRepository.createFile();
    Assert.assertTrue(Files.exists(path.resolve("dump.json")));
  }

  @Test
  public void testDumpToFile() throws IOException {
    Path path = fileSystem.getPath("");

    List<TimedKeyValue> testValues = new LinkedList<>();
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("test");
    timedKeyValue.setContent("test");
    timedKeyValue.setTtl(100);
    testValues.add(timedKeyValue);
    dumpFileRepository.dumpToFile(testValues);
    String jsonString = Files.readAllLines(path.resolve("dump.json")).get(0);
    Assert.assertEquals(
        "[{\"key\":\"test\",\"content\":\"test\",\"date\":" + timedKeyValue.getDate()
            + ",\"ttl\":100}]", jsonString);
  }

  @Test
  public void testLoadFromFile() throws IOException, ClassNotFoundException {
    List<TimedKeyValue> testValues = new LinkedList<>();
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("test");
    timedKeyValue.setContent("test");
    timedKeyValue.setTtl(100);
    testValues.add(timedKeyValue);
    dumpFileRepository.dumpToFile(testValues);
    TimedKeyValue timedKeyValue1 = dumpFileRepository.getFromFile().get(0);
    assertEquals(timedKeyValue.getKey(), timedKeyValue1.getKey());
    assertEquals(timedKeyValue.getTtl(), timedKeyValue1.getTtl());
    assertEquals(timedKeyValue.getDate(), timedKeyValue1.getDate());
    assertEquals(timedKeyValue.getContent(), timedKeyValue1.getContent());
  }
}
