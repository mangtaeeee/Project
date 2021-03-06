package com.spring.client.cart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.client.cart.service.CartService;
import com.spring.client.cart.vo.CartVO;
import com.spring.client.member.vo.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/cart/*")
@Log4j
public class CartController {
	
	@Setter(onMethod_ = @Autowired)
	private CartService cartService;
	@Setter(onMethod_ = @Autowired)
	private HttpSession session;
	
	
	@GetMapping("insertCart")
	@ResponseBody
	public String InsertCart(@ModelAttribute CartVO cvo) {
		log.info("InsertCart 호출 성공");
		
		MemberVO login = (MemberVO) session.getAttribute("login");
		cvo.setM_id(login.getM_id());
		
		int isExist = 0;
		isExist = cartService.isExist(cvo);
		if (isExist == 1) { // 이미 장바구니에 존재하는 상품
			return "existed"; 
		} else {			// 장바구니에 존재하지 않는 상품 
			cartService.insertCart(cvo);
			return "success";
		}
	}
	
	@ResponseBody
	@PostMapping("deleteSelectCart")
	public String deleteSelectCart(@RequestParam(value = "chkBox[]") List<Integer> chkArr) {
		log.info("deleteSelectCart 호출 성공");
		
		int cart_Num = 0;
		for (Integer i : chkArr) {
			cart_Num = i;
			cartService.deleteSelectCart(cart_Num);
		}
		return "success";
	}
	
	@GetMapping("deleteAllCart")
	public String deleteAllCart(@ModelAttribute CartVO cvo) {
		log.info("deleteAllCart 호출 성공");
		
		MemberVO login = (MemberVO) session.getAttribute("login");
		String m_id = login.getM_id();
		
		cartService.deleteAllCart(m_id);
		return "redirect:/cart/cartList";
	}
	
	@ResponseBody
	@PostMapping("modifyCount")
	public String modifyCount(@ModelAttribute CartVO cvo) {
		log.info("modifyCount 호출 성공");
		
		MemberVO login = (MemberVO) session.getAttribute("login");
		cvo.setM_id(login.getM_id());
		
		int result = cartService.modifyCount(cvo);
		if (result == 1) {
			return "success";
		} else {
			return "failure";
		}
	}
	
	@GetMapping("cartList")
	public String cartList(@ModelAttribute CartVO cvo, Model model) {
		log.info("cartList 호출 성공");
		
		MemberVO login = (MemberVO) session.getAttribute("login");
		cvo.setM_id(login.getM_id());
		
		List<CartVO> cartList = cartService.cartList(cvo);
		model.addAttribute("cartList", cartList);
		
		return "cart/cartList";
	}
}
