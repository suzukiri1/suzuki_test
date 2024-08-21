package com.example.springtest.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;

import com.example.springtest.model.NameAge2Model;
import com.example.springtest.repository.NameAge2Repository;
import com.example.springtest.service.NameAge2Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NameAge2ServiceImpl implements NameAge2Service {

    static final int NAME_MAX_LENGTH = 30;

    static final int HOBBY_MAX_LENGTH = 255;

    static final int SKILL_MAX_LENGTH = 255;

    private final NameAge2Repository nameAge2Repository;

    @Override
    public List<NameAge2Model> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public List<NameAge2Model> getAllJoined() {
        return nameAge2Repository.getAllJoined();
    }

    @Override
    public Map<String, Object> getById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Map<String, Object> saveNew(NameAge2Model nameAge) {

        String name = nameAge.getName();
        
        int age = nameAge.getAge();

        // TODO
        String remarks = "XYZ";

        String hobby = nameAge.getHobby();

        String skill = nameAge.getSkill();

        Map<String, Object> messageMap = new HashMap<>();

        try {
            int resultNameAge = nameAge2Repository.saveNewNameAge(name, age, remarks);
            
            int resultHobbySkill = nameAge2Repository.saveNewHobbySkill(name, hobby, skill);
            
            messageMap.put("status", HttpStatus.OK);

            messageMap.put("result_nameAge", resultNameAge);

            messageMap.put("result_hobbySkill", resultHobbySkill);

        } catch(IllegalArgumentException | OptimisticLockingFailureException e) {
            messageMap.put("status", HttpStatus.BAD_REQUEST);

            messageMap.put("message", "Create failed.");
            
        }

        return messageMap;

    }

    @Override
    public Map<String, Object> update(NameAge2Model nameAge) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Map<String, Object> delete(NameAge2Model nameAge) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Map<String, Object> checkInputs(NameAge2Model nameAge) {
        
        String name = nameAge.getName();
        
        int age = nameAge.getAge();

        // TODO
        // String remarks = "ABC";

        String hobby = nameAge.getHobby();

        String skill = nameAge.getSkill();
        
        Map<String, Object> messageMap = new HashMap<>();
        
        StringBuilder errorMessage = new StringBuilder();
        
        boolean succeeded = true;
        
        if(name == null || name.isEmpty()) {
            errorMessage.append("Name is empty.\n");

            succeeded = false;

        } else if(name.length() > NAME_MAX_LENGTH) {
            errorMessage.append("Name is too long.\n");
            
            succeeded = false;
        }
        
        if(age < 0) {
            errorMessage.append("Age is negative.\n");
            
            succeeded = false;
            
        }

        if(hobby == null || hobby.isEmpty()) {
            errorMessage.append("Hobby is empty.\n");

            succeeded = false;

        } else if(hobby.length() > HOBBY_MAX_LENGTH) {
            errorMessage.append("Hobby is too long.\n");
            
            succeeded = false;
        }

        if(skill == null || skill.isEmpty()) {
            errorMessage.append("Skill is empty.\n");

            succeeded = false;

        } else if(skill.length() > SKILL_MAX_LENGTH) {
            errorMessage.append("Skill is too long.\n");
            
            succeeded = false;
        }
        
        if(succeeded) {
            messageMap.put("status", HttpStatus.OK);
            
        } else {
            messageMap.put("status", HttpStatus.BAD_REQUEST);
            
            messageMap.put("message", errorMessage.toString());
    
        }

        return messageMap;
        
    }
    
}