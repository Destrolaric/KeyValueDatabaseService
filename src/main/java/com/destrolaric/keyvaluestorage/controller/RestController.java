package com.destrolaric.keyvaluestorage.controller;

import com.destrolaric.keyvaluestorage.service.DatabaseService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.web.bind.annotation.RestController
public class RestController {

  @Autowired
  DatabaseService databaseService;

  @GetMapping(value = "/get")
  public ResponseEntity<String> getRequest(@RequestParam(value = "key") String key) {
    String res = databaseService.getRequest(key);
    if (res != null) {
      return new ResponseEntity<String>(res, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Nothing found!", HttpStatus.NO_CONTENT);
    }
  }

  @PostMapping("/set")
  public ResponseEntity<?> setRequest(
      @RequestParam String key,
      @RequestParam String data,
      @RequestParam(required = false, defaultValue = "100") Integer ttl) {
    return ResponseEntity.status(databaseService.setRequest(key, data, ttl)).build();
  }

  @DeleteMapping("/remove")
  public ResponseEntity<?> removeRequest(@RequestParam(value = "key") String key) {
    String res = databaseService.removeRequest(key);
    if (res != null) {
      return new ResponseEntity<String>(res, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Nothing found!", HttpStatus.NOT_FOUND);
    }
  }

  @PatchMapping("/dump")
  public ResponseEntity<Object> dumpRequest() throws IOException {
    return ResponseEntity.status(databaseService.dumpRequest()).build();
  }

  @PatchMapping("/load")
  public ResponseEntity<Object> loadRequest() throws IOException, ClassNotFoundException {
    return ResponseEntity.status(databaseService.loadRequest()).build();
  }
}