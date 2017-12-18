package com.test.entity;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
