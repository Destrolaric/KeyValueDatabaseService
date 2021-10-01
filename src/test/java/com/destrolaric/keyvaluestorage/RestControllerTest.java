package com.destrolaric.keyvaluestorage;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.destrolaric.keyvaluestorage.service.DatabaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DatabaseService databaseService;

  @Test
  public void getFromNonExistingKeyReturnsNoContent() throws Exception {
    String result = "Nothing found";
    Mockito.when(databaseService.getRequest("bob")).thenReturn(null);
    mockMvc.perform(get("/get").param("key", "bob")).andDo(print())
        .andExpect(status().isNoContent()).andExpect(content().string(containsString(result)));

  }

  @Test
  public void getFromExistingKeyGivesItsContent() throws Exception {
    String result = "yes";
    Mockito.when(databaseService.getRequest("bob")).thenReturn("yes");
    mockMvc.perform(get("/get").param("key", "bob")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString(result)));
  }

  @Test
  public void postWithNoExistingKey() throws Exception {
    Mockito.when(databaseService.setRequest("doba", "sadsas", 323)).thenReturn(200);
    mockMvc.perform(MockMvcRequestBuilders.post("/set").param("key", "doba").param("data", "sadsas")
        .param("ttl", "323")).andExpect(status().isOk());
  }

  @Test
  public void postWithExistingKey() throws Exception {
    Mockito.when(databaseService.setRequest("doba", "sadsas", 323)).thenReturn(201);
    mockMvc.perform(MockMvcRequestBuilders.post("/set").param("key", "doba").param("data", "sadsas")
        .param("ttl", "323")).andExpect(status().isCreated());
  }

  @Test
  public void postWithoutTTLGiven() throws Exception {
    Mockito.when(databaseService.setRequest("doba", "sadsas", 100)).thenReturn(201);
    mockMvc.perform(
            MockMvcRequestBuilders.post("/set").param("key", "doba").param("data", "sadsas"))
        .andExpect(status().isCreated());
  }

  @Test
  public void removeExistingValue() throws Exception {
    String res = "data";
    Mockito.when(databaseService.removeRequest("value")).thenReturn(res);
    mockMvc.perform(MockMvcRequestBuilders.delete("/remove").param("key", "value")).andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(res)));
  }

  @Test
  public void removeNonExistingValue() throws Exception {
    String res = "Nothing found!";
    Mockito.when(databaseService.removeRequest("value")).thenReturn(null);
    mockMvc.perform(MockMvcRequestBuilders.delete("/remove").param("key", "value")).andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString(res)));
  }

  @Test
  public void doDumpRequest() throws Exception {
    Mockito.when(databaseService.dumpRequest()).thenReturn(200);
    mockMvc.perform(MockMvcRequestBuilders.patch("/dump")).andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  public void doLoadRequest() throws Exception {
    Mockito.when(databaseService.loadRequest()).thenReturn(200);
    mockMvc.perform(MockMvcRequestBuilders.patch("/load")).andDo(print())
        .andExpect(status().isOk());
  }

}
