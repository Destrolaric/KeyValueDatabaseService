package com.destrolaric.keyvaluestorage.repository;

import com.destrolaric.keyvaluestorage.model.TimedKeyValue;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface TimedKeyValueRepository extends CrudRepository<TimedKeyValue, Integer> {

    @Query("select p.content from TimedKeyValue p where p.key = :key")
    String findContentByKey(@Param("key") String key);

    @Modifying
    @Query("update TimedKeyValue t set t.content = :content,t.ttl = :ttl,t.date=CURRENT_TIMESTAMP where t.key = :key")
    @Transactional
    int updateContentByKey(@Param("key") String key, @Param("ttl") int ttl, @Param("content") String content);

    @Modifying
    @Query("delete from TimedKeyValue t where DATEADD('second', t.ttl, t.date) < CURRENT_TIMESTAMP")
    @Transactional
    void deleteAllExpiredRows();

    @Modifying
    @Query("delete from TimedKeyValue")
    @Transactional
    void deleteAllRows();
}
