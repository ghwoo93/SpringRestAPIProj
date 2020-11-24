package com.kosmo.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosmo.rest.service.ContactsDAO;
import com.kosmo.rest.service.ContactsDTO;
import com.kosmo.rest.service.UsersDTO;

@RestController
public class RestAPIController {
	
	@GetMapping(value = "/users",produces = "text/html; charset=UTF-8")
	public String getQueryParameterb(@RequestParam String name) {
		return String.format("GET:쿼리파라미터 사용-%s",name);
	}
	
	@GetMapping(value = "/users/{name}",produces = "text/html; charset=UTF-8")
	public String getURIParameter(@PathVariable String name) {
		return String.format("GET:URI파라미터 사용-%s",name);
	}
	@PostMapping(value = "/users",produces = "text/html; charset=UTF-8")
	public String postQueryParameter(@RequestParam String name) {
		return String.format("POST:쿼리파라미터 사용-%s",name);
	}
	@PostMapping(value = "/users/{name}",produces = "text/html; charset=UTF-8")
	public String postURIParameter(@PathVariable String name) {
		return String.format("POST:URI파라미터 사용-%s",name);
	}
	@GetMapping("/json/users")
	public List<UsersDTO> getJsonUsers(){
		List<UsersDTO> list = new Vector<UsersDTO>();
		list.add(new UsersDTO("가길동", "30", "가산동"));
		list.add(new UsersDTO("나길동", "20", "나산동"));
		return list;
	}
	@GetMapping("/json/users/{userid}")
	public UsersDTO getJsonUser(@PathVariable String userid) {
		if(userid.equalsIgnoreCase("KIM"))
			return new UsersDTO("권보경", "50", "영통");
		else
			return new UsersDTO("이아랑", "50", "화서");
	}
	@PostMapping("/json/users/one")
	public UsersDTO getJsonOne(@RequestBody UsersDTO user) {
		System.out.println("클라이언트로 부터 받은 JSON:"+user.toString());
		return user;
	}
	@PostMapping("/json/users")
	public List<UsersDTO> getJsonAll(@RequestBody List<UsersDTO> users){
		return users;
	}
	@PutMapping("/json/users/{userid}")
	public UsersDTO updateUser(@RequestBody UsersDTO user,@PathVariable String userid) {
		if(userid.equalsIgnoreCase("KIM")) {
			user.setAddr("Wasonton DC");
			user.setAge("80");
			user.setName("바이든");
		}
		return user;
	}
	@DeleteMapping("/json/users/{userid}")
	public UsersDTO deleteUser(@RequestBody UsersDTO user,@PathVariable String userid) {
		return user;
	}
	@PostMapping("/users/upload")
	public UsersDTO fileupload(
			@RequestParam Map map,@RequestPart MultipartFile upload,HttpServletRequest req) 
												throws IllegalStateException, IOException {
		String path = req.getSession().getServletContext().getRealPath("/upload");
		File file = new File(path+File.separator+upload.getOriginalFilename());
		upload.transferTo(file);
		return new UsersDTO(map.get("name").toString(), map.get("age").toString(), map.get("addr").toString());
	}
	
	@Resource(name="contactsDAO")
	private ContactsDAO contacts;
	
	@CrossOrigin
	@PostMapping(value="/contact",produces = "text/html; charset=UTF-8")
	public String create(
			@RequestParam Map map,@RequestPart MultipartFile photo,HttpServletRequest req) 
					throws IllegalStateException, IOException {
		String path = req.getSession().getServletContext().getRealPath("/upload");
		File file = new File(path+File.separator+photo.getOriginalFilename());
		photo.transferTo(file);
		map.put("photo", photo.getOriginalFilename());
		return contacts.insert(map)==1?"입력성공":"입력실패";
	}
	
	@CrossOrigin
	@GetMapping("/contacts")
	public List<ContactsDTO> selectList(){
		return contacts.selectList();
	}
	
	@CrossOrigin
	@GetMapping("/contacts/{contact_id}")
	public ContactsDTO selectOne(@PathVariable String contact_id) {
		return contacts.selectOne(contact_id);
	}
	
	@CrossOrigin
	@PostMapping(value="/contacts/{contact_id}",produces = "text/html; charset=UTF-8")
	public String update(
			@PathVariable String contact_id,
			@RequestParam Map map,
			@RequestPart MultipartFile photo,
			HttpServletRequest req) 
					throws IllegalStateException, IOException {
		String path = req.getSession().getServletContext().getRealPath("/upload");
		File file = new File(path+File.separator+photo.getOriginalFilename());
		photo.transferTo(file);
		map.put("photo", photo.getOriginalFilename());
		map.put("contact_id", contact_id);
		return contacts.update(map)==1?"수정성공":"수정실패";
	}
	
	@CrossOrigin
	@DeleteMapping(value="/contact/{contact_id}",produces = "text/html; charset=UTF-8")
	public String delete(@PathVariable String contact_id) {
		return contacts.delete(contact_id)==1?"삭제성공":"삭제실패";
	}
	
	
}
