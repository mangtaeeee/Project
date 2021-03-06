

@Controller
@RequestMapping("/member/*")
@Log4j
@RequiredArgsConstructor
public class MemberController {
	
	
	private final MemberService memberService;
	
	private final HttpSession session;
	
	/**************
	 * 마이페이지 구현하기
	 * *************/
	@GetMapping
	public String MemberMypage(@ModelAttribute("data") MemberVO mvo,MyorderVO ovo, Model model) {
		log.info("mypage 호출 성공");
		
		MemberVO vo= (MemberVO) session.getAttribute("login");
		mvo.setM_id(vo.getM_id());
		
		MemberVO mypage = memberService.memberMypage(mvo);
		model.addAttribute("mypage", mypage);
		
		ovo.setM_id(mvo.getM_id());
		MyorderVO memberMypageCnt = memberService.memberMypageCnt(ovo);
		
		
		model.addAttribute("memberMypageCnt",memberMypageCnt );
		
		
		
		return "member/mypage";
	}
	/****************
	 * 회원 정보 수정 화면 구현
	 *********************/
	@GetMapping("/updateForm")
	public String updateForm(@ModelAttribute("data") MemberVO mvo, Model model) {
		log.info("updateForm 호출 성공");
		
		MemberVO vo= (MemberVO) session.getAttribute("login");
		mvo.setM_id(vo.getM_id());
		
		MemberVO updateData = memberService.updateForm(mvo);
		model.addAttribute("updateData", updateData);
		
	
		
		return "member/updateForm";
	}
	
	/******************************************
	 * 회원 정보 수정 처리
	 * **************************************/
	@PostMapping("/memberUpdate")
	public String memberUpdate(@ModelAttribute MemberVO mvo, RedirectAttributes ras) {
		log.info("memberUpdate 호출 성공");
		
		int result = 0;
		String url = "";
		
		
		result = memberService.memberUpdate(mvo);
		ras.addFlashAttribute("data", mvo);
		
		System.out.println(result);
		if(result == 1) {
			url="/member/mypage";
		} else if(result == 0) {
			url = "/member/updateForm";
		}
		return "redirect:" + url;
	}
	/********************************************
	 * 회원 탈퇴 처리
	 * *****************************************/
	@PostMapping("/memberDelete")
	public String memberDelete(@ModelAttribute MemberVO mvo, RedirectAttributes ras) {
		log.info("memberDelete 호출 성공");
		
		int result = 0;
		String url = "";
		
		MemberVO vo= (MemberVO) session.getAttribute("login");
		mvo.setM_id(vo.getM_id());
		
		result = memberService.memberDelete(mvo);
		ras.addFlashAttribute("data",mvo); 
		
		System.out.println(result);
		if(result == 1) {
			url="/member/loginForm";
		} else if(result == 0) {
			url = "/member/updateForm";
		}
		return "redirect:" + url;
	}
	
	
	
	/**********************************************
	 * 전체  게시물 관리 출력
	 * **************/
	@GetMapping("/postmanagement")
	public String Postmanagement(@ModelAttribute("data")PostVO pvo, Model model) {
		log.info("postmanagement 호출 성공");


		
		//게시판 리스트 조회
		List<PostVO> postList = memberService.postList(pvo);
		model.addAttribute("postList",postList);
		
		//전체 레코드 수 구현
		int total = memberService.postListCnt(pvo);
		//페이징 처리
		model.addAttribute("pageMaker", new PageDTO(pvo, total));
		//출력 되는 글번호 제어
		int count = total - (pvo.getPageNum() - 1 ) * pvo.getAmount();
		model.addAttribute("count", count);
		return "member/postmanagement";
	}
	
	/*********************************************
	 * 주문내역 조회 출력
	 * *******************************************/
	@GetMapping("/myorderList")
	public String MyorderList(@ModelAttribute("data") MyorderVO mvo, Model model) {
		log.info("myorderList 호출 성공");
		
		MemberVO vo= (MemberVO) session.getAttribute("login");
		mvo.setM_id(vo.getM_id());
		
		if(mvo.getStart_date()=="") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 올해 1.1
			Calendar time = Calendar.getInstance();
			String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
			String edate = sdf.format(time.getTime());
			String sdate = year+"-01-01";

			mvo.setStart_date(sdate);
			mvo.setEnd_date(edate);
				
		}
		//주문 내역 조회
		List<MyorderVO> myorderList = memberService.myorderList(mvo);
		model.addAttribute("myorderList", myorderList);
		
		//전체 레코드 수 구현
		int total = memberService.myorderListCnt(mvo);
		//페이징 처리
		model.addAttribute("pageMaker", new PageDTO(mvo, total));
		//출력되는 글번호 제어
		int count = total - (mvo.getPageNum() - 1) * mvo.getAmount();
		model.addAttribute("count", count);
		return "member/myorderList";
		
	}
	
	//회원가입페이지이동
		@GetMapping(value="/joinmember")
		public String memberInsert() throws Exception {
		log.info("회원가입페이지출력");
			return "member/joinmember";
	}
		//회원가입처리
		@PostMapping(value = "/memberInsert")
		public String memberInsert(MemberVO memberVO) throws Exception {
			log.info("회원가입처리성공");
			
			memberService.memberInsert(memberVO);
			
			return "redirect:/member/loginForm";
		}
		/*로그인페이지출력*/
		@GetMapping(value="/loginForm")
		public String loginForm()  {
			log.info("로그인페이지출력성공");
			return "member/loginForm";
		}
		/*로그인처리*/
		@PostMapping(value="/login")
		public String login(MemberVO mvo, HttpServletRequest req, Model model) {
			log.info("로그인성공");
			String path = null;
			
			HttpSession session = req.getSession();
			MemberVO vo = memberService.login(mvo);
			//String m_id = mvo.getM_id();
			
			if(vo == null) {
				model.addAttribute("msg", "아이디또는 비밀번호가 일치하지 않습니다. 다시 입력해주세요");
				path = "member/loginForm";
			} else {
				session.setAttribute("login", vo);
				path = "redirect:/product/mainPage";
			}
			return path;
		}
		
		/*로그아웃*/
		@GetMapping(value = "/logout")
		public String logout(HttpServletRequest request) {
			log.info("logout 성공");
			
			HttpSession session = request.getSession();

			session.invalidate();
			
			return "redirect:/product/mainPage";
		}

}
