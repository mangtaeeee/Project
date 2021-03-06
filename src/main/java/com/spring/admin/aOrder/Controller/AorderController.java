package com.spring.admin.aOrder.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.admin.aOrder.service.AorderService;
import com.spring.admin.aOrder.vo.AorderVO;
import com.spring.common.vo.PageDTO;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/aOrder/*")
@Log4j
@AllArgsConstructor // 생성자 주입
public class AorderController {
	AorderService aOrderservice;
	
	@Setter(onMethod_ = @Autowired)
	private HttpSession session;
	
	/***********************************************************************
	 * 주문 리스트 구현
	 ***********************************************************************/
	@RequestMapping("aOrderList")
	public String aOrderList(@ModelAttribute("data") AorderVO aovo, Model model) {
		if(session.getAttribute("ad_id")!=null) {
			List<AorderVO> aOrderList = aOrderservice.aOrderList(aovo);
			model.addAttribute("aOrderList",aOrderList);
			
			int total = aOrderservice.aOrderListCnt(aovo);
			model.addAttribute("pageMaker", new PageDTO(aovo, total));
			
			int count = total - (aovo.getPageNum() -1)*aovo.getAmount();
			model.addAttribute("count", count);
			
			return "aOrder/aOrderList";
		}
		else return "error";
	}
	
	/***********************************************************************
	 * 주문 상세 구현
	 ***********************************************************************/
	@RequestMapping("aOrderDetail")
	public String aOrderDetail(@ModelAttribute("data") AorderVO aovo, Model model) {
		if(session.getAttribute("ad_id")!=null) {
			List<AorderVO> aOrderProductDetail = aOrderservice.aOrderProductDetail(aovo);
			model.addAttribute("aOrderProductDetail",aOrderProductDetail);
			
			AorderVO aOrderDetail = aOrderservice.aOrderDetail(aovo);
			model.addAttribute("aOrderDetail",aOrderDetail);
			
			return "aOrder/aOrderDetail";
		}
		else return "error";
		
	}
	
	/***********************************************************************
	 * 주문 상태 변경 구현
	 ***********************************************************************/
	@GetMapping("aOrderchangeState")
	public String aOrderchangeState(@ModelAttribute AorderVO aovo, RedirectAttributes ras) {
		if(session.getAttribute("ad_id")!=null) {
			aOrderservice.aOrderChangeState(aovo);
			ras.addFlashAttribute("data",aovo);
			if(aovo.getOrder_state()=="결제 완료") {
				aOrderservice.aOrderChangeStock(aovo);
			}
			
			return "redirect:/aOrder/aOrderDetail";
		}
		else return "error";
	}
	
	/***********************************************************************
	 * 환불할 경우, 환불의 상태로 변경하며 상품의 재고 및 판매량 수정
	 ***********************************************************************/
	@GetMapping("aOrderchangeRefundState")
	public String aOrderchangeRefundState(@ModelAttribute AorderVO aovo, RedirectAttributes ras) {
		if(session.getAttribute("ad_id")!=null) {
			aOrderservice.aOrderChangeState(aovo);
			ras.addFlashAttribute("data",aovo);
			aOrderservice.aOrderTotalPayChange(aovo);
			log.info(aovo.getOrder_state());
			if(aovo.getOrder_state().equals("전체 환불")) {
				aOrderservice.aOrderProCountReset(aovo);
			}
			else {
				aOrderservice.aOrderProCountChange(aovo);
			}
			return "redirect:/aOrder/aOrderList";
		}
		else return "error";
		
	}
	
	/***********************************************************************
	 * 완료로 부터 7일이 지난 주문 일괄 삭제
	 ***********************************************************************/
	@RequestMapping("aOrderAllDelete")
	public String aOrderAllDelete() throws Exception{
		aOrderservice.aOrderAllDelete();
		
		return "redirect:/aOrder/aOrderList";
	}
	
	/***********************************************************************
	 * 반품 옵션 변경 창 구현
	 ***********************************************************************/
	@RequestMapping("aOrderReturnForm")
	public String aOrderReturnForm(@RequestParam(value ="checkProduct") List<Integer> checkProduct, Model model) throws Exception {
		if(session.getAttribute("ad_id")!=null) {
			log.info("checkProduct : " + checkProduct);
			model.addAttribute("checkProduct",checkProduct);
			List<AorderVO> returnProduct = new ArrayList<AorderVO>();
			for(int i:checkProduct) {
				AorderVO a1 = new AorderVO();
				a1.setOd_num(i);
				returnProduct.add(aOrderservice.aOrderReturnDetail(a1));
				log.info(returnProduct);
				model.addAttribute("returnProduct",returnProduct);
			}
			return "aOrder/aOrderReturnForm";
		}
		else return "error";
	}
	
	/***********************************************************************
	 * 옵션 변경 기능 구현(반품)
	 ***********************************************************************/
	@RequestMapping("returnProDetailForm")
	public String returnProDetailForm(@RequestParam(value ="od_num") int od_num, Model model) {
		if(session.getAttribute("ad_id")!=null) {
			log.info("returnProDetailForm  실행완료");
			log.info(od_num);
			AorderVO aovo = new AorderVO();
			aovo.setOd_num(od_num);
			AorderVO a1 = aOrderservice.aOrderReturnDetail(aovo);
			log.info(a1);
			model.addAttribute("detail", a1);
			
			List<String> color = new ArrayList<String>();
			color = aOrderservice.aGetProColor(a1);
			
			model.addAttribute("color",color);
			
			List<String> size = new ArrayList<String>();
			size = aOrderservice.aGetProSize(a1);
			
			model.addAttribute("size",size);
			
			return "aOrder/returnProDetailForm";
		}
		else return "error";
	}
	
	
	/***********************************************************************
	 * 반품 시 재고와 판매량 변경 기능 구현
	 ***********************************************************************/
	@RequestMapping("aOrderOptionChange")
	public String aOrderOptionChange(@RequestBody AorderVO aovo, RedirectAttributes ras, Model model) throws SQLException {
		if(session.getAttribute("ad_id")!=null) {
			log.info("aOrderOptionChange  실행완료");
			log.info(aovo);
			log.info(aovo.getPro_num());
			aOrderservice.aOrderProCountReset(aovo);
			aOrderservice.aOrderOptionChange(aovo);
			ras.addFlashAttribute("data",aovo);
			log.info(ras.getFlashAttributes());
						
			return "redirect:/aOrder/returnProDetailForm?od_num="+aovo.getOd_num();
		}
			
		else return "error";
	}
	


}
