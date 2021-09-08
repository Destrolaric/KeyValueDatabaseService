package com.destrolaric.keyvaluestorage.service;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class DatabaseService {
    @Autowired
    TimedKeyValueRepository timedKeyValueRepository;

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
        timedKeyValueRepository.findAll();
        return 300;
    }

    public int loadRequest() {
        return 300;
    }
}
