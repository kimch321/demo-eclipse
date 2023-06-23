package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepositoty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepositoty repository;

    public String testService() {
        // TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("my first todo item").build();
        // TodoEntity 저장
        repository.save(entity);
        // TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
    
    public List<TodoEntity> create(final TodoEntity entity) {
    	// Validations
    	validate(entity);
    	
    	repository.save(entity);
    	
    	log.info("Entity Id : {} is saved", entity.getId());
    	
    	return repository.findByUserId(entity.getUserId());
    }
    
    // 리펙토링한 메서드
    private void validate(final TodoEntity entity) {
    	if(entity == null) {
    		log.warn("Entity cannot be null.");
    		throw new RuntimeException("Entty cannot be null.");
    	}
    	
    	if(entity.getUserId() == null) {
    		log.warn("Unkown User.");
    		throw new RuntimeException("Unknown user.");
    	}
    }
    
    public List<TodoEntity> retrieve(final String userId) {
    	return repository.findByUserId(userId);
    }
}