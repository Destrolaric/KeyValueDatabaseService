package com.destrolaric.keyvaluestorage.service;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.destrolaric.keyvaluestorage.repository.DumpFileDAO;
import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
public class DatabaseService {
    @Autowired
    TimedKeyValueRepository timedKeyValueRepository;

    @Autowired
    DumpFileDAO dumpFileDAO;

    public String getRequest(String key) {
        return timedKeyValueRepository.findContentByKey(key);
    }

    public String removeRequest(String key) {
        return key;
    }


    public int setRequest(String key, String data, Integer ttl) {
        if (timedKeyValueRepository.findContentByKey(key) == null) {
            TimedKeyValue timedKeyValue = new TimedKeyValue();
            timedKeyValue.setKey(key);
            timedKeyValue.setContent(data);
            timedKeyValue.setTtl(ttl);
            timedKeyValueRepository.save(timedKeyValue);
            return 0;
        }
        else{
            timedKeyValueRepository.updateContentByKey(key, ttl, data);
            return 0;
        }
    }

    public int dumpRequest() {
        List<TimedKeyValue> list= (List<TimedKeyValue>) timedKeyValueRepository.findAll();
        dumpFileDAO.dumpToFile(list);
        return 0;
    }

    public int loadRequest() {
        List<TimedKeyValue> list = dumpFileDAO.getFromFile();
        timedKeyValueRepository.deleteAll();
        timedKeyValueRepository.saveAll(list);
        return 0;
    }
}
