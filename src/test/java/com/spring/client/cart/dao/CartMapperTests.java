package com.spring.client.cart.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.client.cart.vo.CartVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class CartMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private CartDAO cartDao;
	
//	@Test
//	public void testIsExist() {
//		CartVO cvo = new CartVO();
//		cvo.setM_id("smartmember");
//		cvo.setPro_num("TS_0112_S_SB");
//		
//		int result = cartDao.isExist(cvo);
//		log.info("result : " + result);
//	}
	
	@Test
	public void testInsertCart() {
		CartVO cvo = new CartVO();
		cvo.setCart_goodsCount(1);
		cvo.setM_id("smartmember");
		cvo.setPro_num("RG_IVUR_S_W");
		
		int result = cartDao.insertCart(cvo);
		
		cvo.setCart_goodsCount(2);
		cvo.setM_id("smartmember");
		cvo.setPro_num("RG_IVUR_S_G");
		
		result = cartDao.insertCart(cvo);
		
		cvo.setCart_goodsCount(3);
		cvo.setM_id("smartmember");
		cvo.setPro_num("RG_IVUR_S_B");
		
		result = cartDao.insertCart(cvo);
		
		cvo.setCart_goodsCount(4);
		cvo.setM_id("smartmember");
		cvo.setPro_num("ZT_QNLT_S_B");
		
		result = cartDao.insertCart(cvo);
		
		log.info("result : " + result);
	}
	
//	@Test
//	public void testDeleteEachCart() {
//		CartVO cvo = new CartVO();
//		cvo.setCart_num(14);
//		
//		int result = cartDao.deleteEachCart(cvo);
//		log.info("result : " + result);
//	}
	
//	@Test
//	public void testDeleteAllCart() {
//		CartVO cvo = new CartVO();
//		cvo.setM_id("javauser");
//		
//		int result = cartDao.deleteAllCart(cvo);
//		log.info("result : " + result);
//	}
	
//	@Test
//	public void testModifyCount() {
//		CartVO cvo = new CartVO();
//		cvo.setCart_goodsCount(10);
//		cvo.setM_id("javauser");
//		cvo.setPro_num("TS_0112_M_W");
//		
//		int result = cartDao.modifyCount(cvo);
//		log.info("result : " + result);
//	}
	
	@Test
	public void testCartList() {
		CartVO cvo = new CartVO();
		cvo.setM_id("smartmember");
		
		List<CartVO> list = cartDao.cartList(cvo);
		
		for (CartVO vo : list) {
			log.info(vo);
		}
	}
}
