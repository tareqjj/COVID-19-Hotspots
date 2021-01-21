package com.springsecurity.auth.repositories;

import com.springsecurity.auth.models.Test;
import com.springsecurity.auth.models.User;
import org.apache.taglibs.standard.extra.spath.Step;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TestRepository extends CrudRepository<Test, Long> {
    List<Test> findAll();
    List<Test> findAllByStatus(String status);
    Test findBySample(Long sample);
    List<Test> findByRecordIdAndStatusIs(Long id, String status);
    List<Test> findByRecordUser(User user);
    List<Test> findAllByResultIsAndEndDateAfter(String result, Date date);
}
