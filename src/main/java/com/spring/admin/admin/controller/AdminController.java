package com.spring.admin.admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.admin.admin.service.AdminService;
import com.spring.admin.admin.vo.AdminVO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
@Controller
@RequestMapping("/admin/*")
@Log4j
@AllArgsConstructor // 생성자 주입
public class AdminController {
	public AdminService adminService;
	@Setter(onMethod_ = @Autowired)
	private HttpSession session;

	/****************************************************
	 * 로그인 폼
	 ****************************************************/
	@GetMapping("adminLoginForm")
	public String loginTest() {
		log.info("loginTest 호출 성공");

		return "admin/adminLoginForm";
	}

	/****************************************************
	 * 로그인 기능
	 ****************************************************/
	@RequestMapping("adminLogin")
	public String adminLogin(AdminVO avo, Model model) {
		log.info(avo);
		AdminVO adminVO = adminService.adminLogin(avo);
		log.info(adminVO);
		if(adminVO != null) { // 로그인 성공
			session.setAttribute("ad_id", adminVO.getAd_id());
			return "redirect:/nboard/main";
		} else {
			model.addAttribute("msg", "아이디, 비밀번호를 확인하세요.");
			return "admin/adminLoginForm";
		}
	}

	/****************************************************
	 * 관리자 마이페이지 구현
	 ****************************************************/
	@GetMapping("adminInfo")
	public String adminInfo(HttpSession session, Model model) {
		if(session.getAttribute("ad_id")!=null) {
		log.info("adminInfo 호출 성공");

		AdminVO adminInfo = adminService.readAdminInfo((String) session.getAttribute("ad_id"));
		log.info("ad_id : "+(String) session.getAttribute("ad_id"));
		
		model.addAttribute("adminInfo",adminInfo);

		return "admin/adminInfo";
		} else return "error";
		
	}
	/****************************************************
	 * 정보 수정 폼
	 ****************************************************/
	@RequestMapping("adminUpdateForm")
	public String adminUpdateForm(HttpSession session, Model model) {
		if(session.getAttribute("ad_id")!=null) {
		log.info("adminUpdateForm 호출 성공");
		
		model.addAttribute("adminUpdateInfo", adminService.readAdminInfo((String)session.getAttribute("ad_id")));

		return "admin/adminUpdateForm";
		} else return "error";
	}
	
	/****************************************************
	 * 정보 수정 기능
	 ****************************************************/
	@RequestMapping("adminUpdate")
	public String adminUpdate(AdminVO avo, RedirectAttributes ras) {
		if(session.getAttribute("ad_id")!=null) {
		log.info("adminUpdate 호출 성공");
		
		adminService.adminUpdate(avo);
		ras.addFlashAttribute("data",avo);
		
		return "redirect:/admin/adminInfo";
		} else return "error";
	}
	
	/****************************************************
	 * 로그아웃 기능
	 ****************************************************/
	@RequestMapping("adminLogout")
	public String adminLogout(HttpSession session) {
		log.info("adminLogout 호출 성공");
		
		session.invalidate();
		return "redirect:/admin/adminLoginForm";
	}
	

}