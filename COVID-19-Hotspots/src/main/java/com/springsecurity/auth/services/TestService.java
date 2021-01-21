package com.springsecurity.auth.services;

import com.springsecurity.auth.models.Test;
import com.springsecurity.auth.models.User;
import com.springsecurity.auth.repositories.RecordRepository;
import com.springsecurity.auth.repositories.TestRepository;
import com.springsecurity.auth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    public TestService(TestRepository testRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
    }

    public Test findTestById(Long id){ return testRepository.findById(id).orElse(null); }
    public Test creatTest(Test test){
        return testRepository.save(test);
    }
    public Test creatTestxx(Test test){
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, 14);
        Date currentDatePlusOne = c.getTime();
        test.setEndDate(currentDatePlusOne);
        return testRepository.save(test);
    }
    public List<Test> allTests(){ return testRepository.findAll(); }
    public List<Test> findTestByStatus(String status){ return testRepository.findAllByStatus(status); }
    public Test findTestBySample(Long sample){ return testRepository.findBySample(sample); }
    public List<Test> findTestByRecordId(Long record_id){ return testRepository.findByRecordIdAndStatusIs(record_id, "Pending"); }
    public List<Test> findTestByUser(Long user_id){
        User user = userRepository.findById(user_id).orElse(null);
        return testRepository.findByRecordUser(user);
    }
    public void setTestResult(Test test, String result) {
        test.setResult(result);
        test.setStatus("Done");
        testRepository.save(test);
    }
    public List<Test> activePoints(){
        return testRepository.findAllByResultIsAndEndDateAfter("positive", new Date());
    }
}
