
@Controller
@RequestMapping(value="/review/*")
@Log4j
@RequiredArgsConstructor
public class ReviewController {
	
	
	private final ReviewService reviewService;
	
	private final HttpSession session;
	
	/**********
	 * 리뷰게시판 구현
	 * **********/
	@GetMapping("reviewList")
	public String reviewList(@ModelAttribute("data")ReviewVO rvo, Model model) {
		log.info("reviewList 호출 성공");
		//전체 레코드 조회
		List<ReviewVO> reviewList = reviewService.reviewList(rvo);
		model.addAttribute("reviewList",reviewList);
		
		// 전체 레코드 수 구현
		int total = reviewService.reviewListCnt(rvo);
		// 페이징 처리
		model.addAttribute("pageMaker", new PageDTO(rvo, total));
		// 출력되는 글번호 제어
		int count = total - (rvo.getPageNum() - 1 ) * rvo.getAmount();
		model.addAttribute("count", count);
		return "review/reviewList";
	}
	
	/**************************************************
	 * 글쓰기 폼 출력 -- 수정 버튼 클릭시 아이디가 같지않으면 다시 뒤로 돌려보내는거 해야함
	 * *************************************************/
	@GetMapping(value="/writeForm")
	public String writeForm(MemberVO mvo,Model model) {
		log.info("write 호출 성공");
		
		MemberVO vo= (MemberVO) session.getAttribute("login");
		mvo.setM_id(vo.getM_id());
		mvo.setM_name(vo.getM_name());
		
		model.addAttribute("writeForm",vo);
		
		return "review/writeForm";
	}
	
	/**************************************************
	 * 글쓰기 처리
	 * *****************************************/
	@PostMapping("reviewInsert")
	public String reviewInsert(@ModelAttribute ReviewVO rvo) throws Exception{
		log.info("ReviewInsert 호출 성공");
		
		int result = 0;
		String url = ""; 
		
		result = reviewService.reviewInsert(rvo);
		if(result == 1) {
			url = "/review/reviewList";
		} else {
			url = "/review/writeForm";
		}
		
		return "redirect:" + url;
	}
	
	/*******************************************************
	 * 글 상세 보기 구현
	 * ****************************************************/
	@GetMapping(value="/reviewDetail")
	public String reviewDetail(@ModelAttribute("data") ReviewVO rvo, Model model) {
		log.info("reviewDetail 호출 성공");
		

		
		ReviewVO detail = reviewService.reviewDetail(rvo);
		model.addAttribute("detail",detail);
		
		return "review/reviewDetail";
	}
	/***************************************************
	 * 글 수정 폼 출력
	 * ***************/
	@GetMapping("updateForm")
	public String updateForm(@ModelAttribute("data") ReviewVO rvo, Model model) {
		log.info("updateForm 호출 성공");
		
		ReviewVO updateData = reviewService.updateForm(rvo);
		model.addAttribute("updateData",updateData);
		
		return "review/updateForm";
	}
	/***********************************************************
	 * 글수정 처리
	 * **************/
	@PostMapping
	public String reviewUpdate(@ModelAttribute ReviewVO rvo , RedirectAttributes ras) throws Exception{
		log.info("reviewUpdate 호출 성공");
		
		int result = 0;
		String url = "";
		result = reviewService.reviewUpdate(rvo);
		ras.addFlashAttribute("data", rvo);
		
		if(result == 1) {
			url = "/review/reviewDetail";
		} else if (result == 0 ) {
			url = "/review/updateForm";		
		}
		return "redirect:" + url;
	}
	/**************************************************************
	 * 글 삭제 처리
	 * ************/
	@PostMapping("reviewDelete")
	public String reviewDelete(@ModelAttribute("data") ReviewVO rvo) throws Exception{
		reviewService.reviewDelete(rvo);
		
		return "redirect:/review/reviewList";
	}
}
