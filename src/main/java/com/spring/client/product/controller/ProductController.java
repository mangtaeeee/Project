package com.spring.client.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.client.product.service.ProductService;
import com.spring.client.product.vo.ProductVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/product/*")
@EnableAspectJAutoProxy
public class ProductController {
	
	@Setter(onMethod_ = @Autowired)
	private ProductService productService;
	
	
	@RequestMapping(value="mainPage", method=RequestMethod.GET)
	public String mainPage(@ModelAttribute("data") ProductVO cpvo, Model model) {
		log.info("MainpageList 호출성공");
		
		List<ProductVO> listDate = productService.productListDate(cpvo);
		model.addAttribute("listDate",listDate);
		
		List<ProductVO> listSales = productService.productListSales(cpvo);
		model.addAttribute("listSales",listSales);
		
		return "product/mainPage";
	}
	@RequestMapping(value="/subPage", method=RequestMethod.GET)
	public String subPage(@ModelAttribute("data") ProductVO cpvo, Model model) {
		log.info("subPage 호출 성공");
		ProductVO detail = productService.productDetail(cpvo);
		model.addAttribute("detail",detail);
		
		List<String> listSize=productService.listSize(cpvo);
		model.addAttribute("listSize",listSize);
		
		List<String> listColor=null;
		listColor = productService.listColor(cpvo);
		model.addAttribute("listColor", listColor);
		
		
		return "product/subPage";
	}
	@GetMapping("/order/{order}")
	public String orderPage(@PathVariable("order") String order, ProductVO pvo, Model model ) {
		log.info("orderPage 호출 성공");
		
		pvo.setOrder(order);
		List<ProductVO> listData = productService.orderPage(pvo);
		pvo.setOrder(order);
		model.addAttribute("listData", listData);
		
		return "product/orderPage";
	}
	@GetMapping("/cate/{pr_categoryl}")
	public String categoryPage(@PathVariable("pr_categoryl") String pr_categoryl, ProductVO pvo, Model model ) {
		log.info("categoryPage 호출 성공");
		
		pvo.setPr_categoryl(pr_categoryl);
		List<ProductVO> listData = productService.categoryPage(pvo);
		model.addAttribute("listData", listData);
		
		return "product/categoryPage";
	}
	@GetMapping("/cate/{pr_categoryl}/{pr_categorys}")
	public String categorysPage(@PathVariable("pr_categorys") String pr_categorys,@PathVariable("pr_categoryl") String pr_categoryl, ProductVO pvo, Model model ) {
		log.info("categoryPage 호출 성공");
		
		pvo.setPr_categoryl(pr_categoryl);
		pvo.setPr_categorys(pr_categorys);
		List<ProductVO> listData = productService.categorysPage(pvo);
		model.addAttribute("listData", listData);
		
		return "product/categoryPage";
	}
	@RequestMapping(value="listPage", method=RequestMethod.GET)
	public String listPage(@ModelAttribute("data") ProductVO cpvo, Model model) {
		log.info("ListPage 호출성공");
		
		List<ProductVO> listPage = productService.listPage(cpvo);
		model.addAttribute("listPage",listPage);
				
		return "product/listPage";
	}

}
