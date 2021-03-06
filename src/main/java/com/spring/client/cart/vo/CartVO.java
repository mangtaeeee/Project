package com.spring.client.cart.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartVO {
	private int cart_num = 0;		// 카트 번호
	private String cart_regdate;	// 카트 등록일
	private int cart_goodsCount = 0;// 상품 개수
	private String pro_num = "";	// 상품 번호
	private String m_id = "";		// 회원 아이디
	
	private String pro_size = ""; 	// 상품 사이즈
	private String pro_color = ""; 	// 상품 색상
	
	private String pr_name = ""; 	// 상품명
	private String pr_price = ""; 	// 상품 가격
	private String pro_colorThumb = ""; 	// 썸네일 이미지
}
