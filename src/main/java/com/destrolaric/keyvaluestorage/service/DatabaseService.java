package com.destrolaric.keyvaluestorage.service;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.destrolaric.keyvaluestorage.repository.DumpFileRepository;
import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

  @Autowired
  TimedKeyValueRepository timedKeyValueRepository;

  @Autowired
  DumpFileRepository dumpFileDAO;

  public String getRequest(String key) {
    return timedKeyValueRepository.findContentByKey(key);
  }

  public String removeRequest(String key) {
    String res = timedKeyValueRepository.findContentByKey(key);
    timedKeyValueRepository.deleteByKey(key);
    return res;
  }


  public int setRequest(String key, String data, Integer ttl) {
    if (timedKeyValueRepository.findContentByKey(key) != null) {
      timedKeyValueRepository.updateContentByKey(key, ttl, data);
      return 200;
    } else {
      TimedKeyValue timedKeyValue = new TimedKeyValue();
      timedKeyValue.setKey(key);
      timedKeyValue.setContent(data);
      timedKeyValue.setTtl(ttl);
      timedKeyValueRepository.save(timedKeyValue);
      return 201;
    }
  }

  public int dumpRequest() throws IOException {
    List<TimedKeyValue> list = (List<TimedKeyValue>) timedKeyValueRepository.findAll();
    dumpFileDAO.dumpToFile(list);
    return 200;
  }

  public int loadRequest() throws IOException, ClassNotFoundException {
    List<TimedKeyValue> list = dumpFileDAO.getFromFile();
    timedKeyValueRepository.deleteAll();
    timedKeyValueRepository.saveAll(list);
    return 200;
  }
}
