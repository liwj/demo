package com.techlon.base.dao;

import java.util.List;

import com.techlon.base.model.DemoModel;

public interface DemoDao {
	List<DemoModel> findAll();
}
