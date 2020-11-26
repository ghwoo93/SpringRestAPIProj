package com.kosmo.rest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ContactsDAO {
	//SqlSessionTemplate객체 주입]
	@Resource(name="template")
	private SqlSessionTemplate template;

	public int insert(Map map) {		
		return template.insert("contactsInsert", map);
	}

	public List<ContactsDTO> selectList() {		
		return template.selectList("contactsSelectList");
	}
	public ContactsDTO selectOne(String contact_id) {		
		return template.selectOne("contactsSelectOne",contact_id);
	}

	public int update(Map map) {		
		return template.update("contactsUpdate",map);
	}

	public int delete(String contact_id) {
		
		return template.delete("contactsDelete",contact_id);
	}
	
}
