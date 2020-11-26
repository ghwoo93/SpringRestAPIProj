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
	/*
	 * GET메소드:쿼리 파라미터 사용
	 * POSTMAN:
	 * GET : http://localhost:8080/rest/users?name=kosmo
	 * produces="text/plain;charset=UTF-8"는 한글 깨짐 방지용
	 * 
	 */
	@GetMapping(value = "/users",produces = "text/html;charset=UTF-8")
	public String getQueryParameter(@RequestParam String name) {
		return String.format("GET:쿼리파라미터 사용-%s", name);
	}//////////////getQueryParameter
	/*
	 * GET메소드:URI 파라미터 사용
	 * POSTMAN:
	 * GET : http://localhost:8080/rest/users/kosmo	 * 
	 * 
	 */
	@GetMapping(value = "/users/{name}",produces = "text/html;charset=UTF-8")
	public String getURIParameter(@PathVariable String name) {
		return String.format("GET:URI파라미터 사용-%s", name);
	}
	/*
	 * POST메소드:쿼리 파라미터 사용
	 * POSTMAN:
	 * 테스트1
	 * POST : http://localhost:8080/rest/users?name=kosmo
	 * 테스트2
	 * POST : http://localhost:8080/rest/users
	 * postman에서 params선택->KEY에 name ,VALUE에 kosmo입력
	 *                   Fiddler로 확인(raw탭)에서 확인해보면
	 *                   요청바디에 name=kosmo이 쓰여있다
	 * produces="text/plain;charset=UTF-8"는 한글 깨짐 방지용
	 * 
	 */
	@PostMapping(value = "/users",produces = "text/html;charset=UTF-8")
	public String postQueryParameter(@RequestParam String name) {
		return String.format("POST:쿼리파라미터 사용-%s", name);
	}//////////////getQueryParameter
	
	/*
	 * POST메소드:URI 파라미터 사용
	 * POSTMAN:
	 * POST : http://localhost:8080/rest/users/kosmo	
	 * Fiddler로 확인해보면 요청바디에 name=kosmo이 쓰여있지 않고 URL에 포함되어 있다
	 * 
	 */
	@PostMapping(value = "/users/{name}",produces = "text/html;charset=UTF-8")
	public String postURIParameter(@PathVariable String name) {
		return String.format("POST:URI파라미터 사용-%s", name);
	}
	/*
	 * GET메소드:JSON 배열 리턴하기
	 * POSTMAN:
	 * GET : http://localhost:8080/rest/json/users
	 * [
		    {
		        "name": "가길동",
		        "age": "20",
		        "addr": "가산동"
		    },
		    {
		        "name": "나길동",
		        "age": "30",
		        "addr": "나산동"
		    }
		]
	 * 
	 * - JSON 형태로 객체를 반환하기 위해서는 jackson-databind 라이브러리를 pom.xml에 추가로 정의해야 함

	  <dependency>	
		<groupId>com.fasterxml.jackson.core</groupId>	
		<artifactId>jackson-databind</artifactId>	
		<version>2.8.4</version>	
	  </dependency>
	 * 
	 * 
	 * 
	 */
	@GetMapping("/json/users")
	public List<UsersDTO> getJsonUsers(){
		List<UsersDTO> users = new Vector<UsersDTO>();
		users.add(new UsersDTO("가길동", "20", "가산동"));
		users.add(new UsersDTO("나길동", "30", "나산동"));
		return users;
	}///////////////////////////////////////////
	/*
	 * GET메소드:JSON 하나 리턴하기
	 * POSTMAN:
	 * GET : http://localhost:8080/rest/json/users/:userid	
	 * 예]http://localhost:8080/rest/json/users/kim
	 */
	@GetMapping("/json/users/{userid}")
	public UsersDTO getJsonUser(@PathVariable String userid) {
		//id로 검색 로칙 추가
		if(userid.equalsIgnoreCase("KIM"))
			return new UsersDTO("김길동", "45", "청담동");
		else if(userid.equalsIgnoreCase("LEE"))
			return new UsersDTO("이길동", "25", "방배동");
		else
			return new UsersDTO("박길동", "35", "서초동");	
	}///////////////////////////////////////////
	/*
	 * POST메소드:JSON으로 하나의 데이타 받아서 받은 결과 그대로 JSON으로 응답 보내기
	 * POSTMAN:
	 * POST : http://localhost:8080/rest/json/users/one
	 * Body선택후 raw선택 그리고 json형태의 데이타 입력
	 * 그리고 반드시  선택 상자에서 JSON(application/json)선택	
	 * 예]http://localhost:8080/rest/json/users/one
	 */
	@PostMapping("/json/users/one")
	public UsersDTO getJsonOne(@RequestBody UsersDTO user) {
		System.out.println("클라이언트로부터 받은 JSON : "+user);
		return user;		
	}//////////////////getJsonOne
	/*
	 * POST메소드:JSON으로 여러개의 데이타 받아서 받은 결과 그대로 JSON배열로 응답 보내기
	 * POSTMAN:
	 * POST : http://localhost:8080/rest/json/users
	 * Body선택후 raw선택 그리고 json배열형태의 데이타 입력
	 * 그리고 반드시  선택 상자에서 JSON(application/json)선택	
	 * 예]http://localhost:8080/rest/json/users
	 */
	@PostMapping("/json/users")
	public List<UsersDTO> getJsonAll(@RequestBody List<UsersDTO> users){
		return users;
	}//////////////////////////////////////////////
	/*
	 * PUT메소드:아이디 받아서 수정후 수정 데이타 보내기
	 * POSTMAN:
	 * PUT : http://localhost:8080/rest/json/users/:userid
	 * Body선택후 raw선택 그리고 수정할 json형태의 데이타 입력
	 * 그리고 반드시  선택 상자에서 JSON(application/json)선택	
	 * 예]http://localhost:8080/rest/json/users/kim 
	
	 */
	@PutMapping("/json/users/{userid}")
	public UsersDTO updateUser(@RequestBody UsersDTO user,@PathVariable String userid) {
		//실제 테이블에서 아이디로 수정후 json으로 보내기
		//가상으로 수정하는 코딩 추가
		if(userid.equalsIgnoreCase("KIM")) {
			user.setAddr("웨싱턴");
			user.setAge("100");
			user.setName("바이든");			
		}
		return user;
	}///////////////
	@DeleteMapping("/json/users/{userid}")
	public UsersDTO deleteuser(@RequestBody UsersDTO user,@PathVariable String userid) {
		//실제 테이블에서 아이디로 삭제후 json으로 삭제한 사용자 보내기
		return user;
	}////////////////
	/*
	 * 파일 업로드
	 *POSTMAN:
	 * POST : http://localhost:8080/rest/users/upload
	 * Body선택후 form-data선택 그리고 key입력후 File선택(text:디폴트)
	 * 그리고 다른 값들은 key에  value에 입력
	 * 받을때는 @RequestBody 말고 @RequestParam으로 받는다
	 * 예]http://localhost:8080/rest/users/upload
	 * 
	 * 
	 */
	@PostMapping("/users/upload")
	public UsersDTO fileupload(@RequestParam Map map,@RequestPart MultipartFile upload,HttpServletRequest req) throws IllegalStateException, IOException {
		//1]서버의 물리적 경로 얻기
		String path = req.getSession().getServletContext().getRealPath("/upload");
		//2]File객체 생성	
		File file = new File(path+File.separator+upload.getOriginalFilename());
		//3]업로드 처리		
		upload.transferTo(file);
		return new UsersDTO(map.get("name").toString(), map.get("age").toString(), map.get("addr").toString());
	}/////////////////////
	//[실제 데이터베이스와  CRUD]
	@Resource(name="contactsDAO")
	private ContactsDAO contacts;
	
	//등록하기]
	@CrossOrigin
	@PostMapping(value = "/contact",produces ="text/html;charset=UTF-8")
	public String create(@RequestParam Map map,
			             @RequestPart MultipartFile photo,
			             HttpServletRequest req) throws IllegalStateException, IOException {
		//1]서버의 물리적 경로 얻기
		String path = req.getSession().getServletContext().getRealPath("/upload");
		//2]File객체 생성	
		File file = new File(path+File.separator+photo.getOriginalFilename());
		//3]업로드 처리		
		photo.transferTo(file);
		//DB에 입력처리
		//@RequestParam Map map에는 type="file"이 설정이 안된다.
		map.put("photo", photo.getOriginalFilename());
		return contacts.insert(map)==1 ? "입력 성공":"입력 실패";
	}/////////////////////create
	//모든 레코드 조회]
	@CrossOrigin
	@GetMapping("/contacts")	
	public List<ContactsDTO> selectList(){
		List<ContactsDTO> contacts = this.contacts.selectList();
		return contacts;
	}////////////selectAll
	//레코드 하나 조회]
	@CrossOrigin
	@GetMapping("/contacts/{contact_id}")
	public ContactsDTO selectOne(@PathVariable String contact_id) {		
		return contacts.selectOne(contact_id);
	}////////////////selectOne
	//레코드 수정하기
	@CrossOrigin
	@PostMapping(value="/contacts/{contact_id}",produces ="text/html;charset=UTF-8")
	public String update(
			@PathVariable String contact_id,
			@RequestParam Map map,
			@RequestPart MultipartFile photo,
			HttpServletRequest req
			) throws IllegalStateException, IOException {
		System.out.println("PUT요청");
		//1]서버의 물리적 경로 얻기
		String path = req.getSession().getServletContext().getRealPath("/upload");
		//2]File객체 생성	
		File file = new File(path+File.separator+photo.getOriginalFilename());
		//3]업로드 처리		
		photo.transferTo(file);
		//DB에 수정처리
		//@RequestParam Map map에는 type="file"이 설정이 안된다.
		map.put("photo", photo.getOriginalFilename());
		map.put("contact_id", contact_id);
		return contacts.update(map)==1 ? "수정 성공":"수정 실패";
		
	}///////////////
	//레코드 삭제하기]
	@CrossOrigin
	@DeleteMapping(value="/contacts/{contact_id}",produces ="text/html;charset=UTF-8")
	public String delete(@PathVariable String contact_id) {		
		return contacts.delete(contact_id)==1?"삭제 성공":"삭제 실패";		
	}
}
