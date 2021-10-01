package com.destrolaric.keyvaluestorage;

import com.destrolaric.keyvaluestorage.components.CleanerDAO;
import com.destrolaric.keyvaluestorage.repository.DumpFileRepository;
import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import com.destrolaric.keyvaluestorage.service.DatabaseService;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DatabaseServiceTest {

  @Autowired
  DatabaseService databaseService;

  @Autowired
  private MockMvc mockMvc;

  //This class needs to be mocked due to i
  @MockBean
  CleanerDAO cleanerDAO;

  @MockBean
  TimedKeyValueRepository timedKeyValueRepository;

  @MockBean
  DumpFileRepository dumpFileDAO;

  @Test
  public void getSomeExistingValue() {
    Mockito.when(timedKeyValueRepository.findContentByKey("bob")).thenReturn("Is the best man");
    String res = databaseService.getRequest("bob");
    Assert.assertEquals("Is the best man", res);
  }

  @Test
  public void getSomeNonExistingValue() {
    Mockito.when(timedKeyValueRepository.findContentByKey("bob")).thenReturn(null);
    String res = databaseService.getRequest("bob");
    Assert.assertNull(res);
  }

  @Test
  public void removeNonExistingKey() {
    Mockito.when(timedKeyValueRepository.findContentByKey("sad")).thenReturn(null);
    String res = databaseService.removeRequest("sad");
    Assert.assertNull(res);
  }

  @Test
  public void removeExistingKey() {
    Mockito.when(timedKeyValueRepository.findContentByKey("sad")).thenReturn("content");
    String res = databaseService.removeRequest("sad");
    Assert.assertEquals("content", res);
  }

  @Test
  public void setSomeNonExistingValue() {
    Mockito.when(timedKeyValueRepository.findContentByKey("data")).thenReturn(null);
    int res = databaseService.setRequest("data", "smth", 23);
    Assert.assertEquals(201, res);
  }

  @Test
  public void setSomeExistingValue() {
    Mockito.when(timedKeyValueRepository.findContentByKey("data")).thenReturn("content");
    int res = databaseService.setRequest("data", "sdasd", 32);
    Assert.assertEquals(200, res);
  }

  @Test
  public void dumpRequestSuccess() throws IOException {
    int res = databaseService.dumpRequest();
    Assert.assertEquals(200, res);
  }


  @Test(expected = IOException.class)
  public void dumpRequestFailedBecauseFileSystem() throws IOException {

    Mockito.doThrow(new IOException()).when(dumpFileDAO).dumpToFile(null);
    Mockito.when(timedKeyValueRepository.findAll()).thenReturn(null);
    databaseService.dumpRequest();
  }

  @Test
  public void loadRequestSuccess() throws IOException, ClassNotFoundException {
    int res = databaseService.loadRequest();
    Assert.assertEquals(200, res);
  }

  @Test(expected = IOException.class)
  public void loadRequestFailedBecauseFileSystem() throws IOException, ClassNotFoundException {
    Mockito.doThrow(new IOException()).when(dumpFileDAO).getFromFile();
    databaseService.loadRequest();
  }

}
