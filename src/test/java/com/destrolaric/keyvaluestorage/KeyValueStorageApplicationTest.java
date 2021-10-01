package com.destrolaric.keyvaluestorage;

import static org.assertj.core.api.Assertions.assertThat;

import com.destrolaric.keyvaluestorage.controller.RestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KeyValueStorageApplicationTest {

  @Autowired
  private RestController restController;

  @Test
  void contextLoads() {
    assertThat(restController).isNotNull();
  }

}
