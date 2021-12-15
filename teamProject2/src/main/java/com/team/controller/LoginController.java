package com.team.controller;
import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RequestParam;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.team.domain.MemberDTO;
import com.team.service.MemberService; 

/** * Handles requests for the application home page. */ 





	@Controller 
	public class LoginController { 
	/* NaverLoginBO */ 
	private NaverLoginBO naverLoginBO; 
	private String apiResult = null;
	
	@Inject MemberService memberService;
	
	@Autowired private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO; 
		} 
	
	//로그인 첫 화면 요청 메소드 
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(Model model, HttpSession session) { 
		/* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session); 
		//https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************& 
		//redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125 
		System.out.println("네이버:" + naverAuthUrl); 
		//네이버 
		model.addAttribute("url", naverAuthUrl);
		return "member/login"; 
	} 
	
	//네이버 로그인 성공시 callback호출 메소드 
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST }) 
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException {
		System.out.println("여기는 callback"); 
		OAuth2AccessToken oauthToken; 
		oauthToken = naverLoginBO.getAccessToken(session, code, state); 
		//1. 로그인 사용자 정보를 읽어온다. 
			apiResult = naverLoginBO.getUserProfile(oauthToken); 
			//String형식의 json데이터 
			/** apiResult json 구조 
			 {"resultcode":"00", 
			 	"message":"success",
			 	"response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"sh@naver.com","name":"\uc2e0\ubc94\ud638"}} **/ 
			
			//2. String형식인 apiResult를 json형태로 바꿈 
			JSONParser parser = new JSONParser(); 
			Object obj = parser.parse(apiResult); 
			JSONObject jsonObj = (JSONObject) obj; 
			
			//3. 데이터 파싱 
			//Top레벨 단계 _response 파싱 
			JSONObject response_obj = (JSONObject)jsonObj.get("response"); 
			//response의 nickname값 파싱 
			String nickname = (String)response_obj.get("nickname"); 
			String memId = (String)response_obj.get("id");
			String age = (String)response_obj.get("age");
			String gender = (String)response_obj.get("gender");
			String email = (String)response_obj.get("email");
			String name = (String)response_obj.get("name");
			String birthday = (String)response_obj.get("birthday");
			String birthyear = (String)response_obj.get("birthyear");
			String mobile = (String)response_obj.get("mobile");
			
			Map<String, Object> userInfo = new HashMap<String, Object>();
			userInfo.put("nickname", nickname);
			userInfo.put("memId", memId);
			
			userInfo.put("age", age);
			userInfo.put("gender", gender);
			userInfo.put("email", email);
			userInfo.put("name", name);
			userInfo.put("birthday", birthyear + "-" + birthday);
			userInfo.put("mobile", mobile);
			
			if (memberService.naverUserCheck(memId) != null) {
				MemberDTO memberDTO = new MemberDTO();
				
				memberDTO.setMemId(memId);
				memberDTO.setMemPw("0000");
				memberDTO.setMemNm(name);
				session.setAttribute("user",memberDTO);
				model.addAttribute("userInfo", userInfo);
				model.addAttribute("result", apiResult);
				return "redirect:/main";
				
			}else {
				
				model.addAttribute("userInfo", userInfo);
				model.addAttribute("result", apiResult);
				return "member/joinNaver";
			
			}
	}
	
	@RequestMapping(value = "/kakaoLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public String kakaoLogin(Model model, HttpSession session, HttpServletRequest re) { 
		String id = (String)re.getParameter("user_id");
			System.out.println(id + "ㅁㄴㅇㅁㄴㅇㄴㅁㄴㅇㅁ");
		
		if (memberService.kakaoUserCheck(id) == null) {
			model.addAttribute("id", id);
			return "member/joinKakao";
		}else {
			MemberDTO memberDTO = new MemberDTO();
			
			memberDTO.setMemId(id);
			memberDTO.setMemPw("0000");
			memberDTO.setMemNm(memberService.kakaoUserCheck(id).getMemNm());
			session.setAttribute("user",memberDTO);
			
			return "redirect:/main";
		}
		
		
	}
	
	

}

