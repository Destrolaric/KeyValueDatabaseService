package com.destrolaric.keyvaluestorage;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import com.destrolaric.keyvaluestorage.repository.TimedKeyValueRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TimedKeyValueRepositoryTest {

  @Autowired
  TimedKeyValueRepository timedKeyValueRepository;

  @Test
  public void testSearchContentByKeyWhenNothing() {
    Assert.assertNull(timedKeyValueRepository.findContentByKey("sda"));
  }

  @Test
  public void testSearchContentByKey() {
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("dogo");
    timedKeyValue.setTtl(200);
    timedKeyValue.setContent("M");
    timedKeyValueRepository.save(timedKeyValue);
    Assert.assertEquals("M", timedKeyValueRepository.findContentByKey("dogo"));
  }

  @Test

  public void testUpdateContentByKey() {
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("dogo");
    timedKeyValue.setTtl(200);
    timedKeyValue.setContent("M");
    timedKeyValueRepository.save(timedKeyValue);
    timedKeyValueRepository.updateContentByKey("dogo", 100, "Brush");
    Assert.assertEquals("Brush", timedKeyValueRepository.findContentByKey("dogo"));
  }

  @Test
  public void testDeleteByKey() {
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("dogo");
    timedKeyValue.setTtl(200);
    timedKeyValue.setContent("M");
    timedKeyValueRepository.save(timedKeyValue);
    timedKeyValueRepository.deleteByKey("dogo");
    Assert.assertNull(timedKeyValueRepository.findContentByKey("dogo"));
  }

  @Test
  public void deleteAllRows() {
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("dogo");
    timedKeyValue.setTtl(200);
    timedKeyValue.setContent("M");
    timedKeyValueRepository.save(timedKeyValue);
    timedKeyValue.setKey("dogo2");
    timedKeyValueRepository.save(timedKeyValue);
    timedKeyValueRepository.deleteAllRows();
    Assert.assertNull(timedKeyValueRepository.findContentByKey("dogo"));
  }

  @Test
  public void testCleaningOfExpiredRows() throws InterruptedException {
    TimedKeyValue timedKeyValue = new TimedKeyValue();
    timedKeyValue.setKey("dogo");
    timedKeyValue.setTtl(1);
    timedKeyValue.setContent("M");
    timedKeyValueRepository.save(timedKeyValue);
    Thread.sleep(1001);
    timedKeyValueRepository.deleteAllExpiredRows();
    Assert.assertNull(timedKeyValueRepository.findContentByKey("dogo"));
  }
}
