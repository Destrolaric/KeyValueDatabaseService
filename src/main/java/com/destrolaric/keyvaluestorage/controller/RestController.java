package com.destrolaric.keyvaluestorage.controller;

import com.destrolaric.keyvaluestorage.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    DatabaseService databaseService;

    @GetMapping("/get")
    public ResponseEntity<String> getRequest(@RequestParam(value = "key") String key) {
        String res = databaseService.getRequest(key);
        if (res != null){
            return new ResponseEntity<String>(res, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Nothing found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/set")
    public ResponseEntity<?> setRequest(
            @RequestParam String key,
            @RequestParam String data,
            @RequestParam(required = false) Integer ttl) {
        databaseService.setRequest(key, data, ttl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/remove")
    public String removeRequest(@RequestHeader(value = "key") String key) {
        return databaseService.removeRequest(key);
    }

    @GetMapping("/dump")
    public int dumpRequest() {
        return databaseService.dumpRequest();
    }

    @GetMapping("/load")
    public int loadRequest() {
        return databaseService.loadRequest();
    }

}
