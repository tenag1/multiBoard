package kr.domain.manage.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.domain.manage.dao.MemberDAO;
import kr.domain.manage.vo.MemberRoleVO;
import kr.domain.manage.vo.MemberVO;
import kr.domain.manage.vo.PagingVO;

@Transactional
@Service("MemberService")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	//회원가입을 처리하면서 비밀번호를 암호화하여 저장한다.
	@Override
	public void insert(MemberVO memberVO) {
		//만약 닉네임을 입력하지 않을 경우 이름을 넣는다.
		logger.info("insert(memberVO): "+memberVO);
		if(memberVO.getNick().equals("") || memberVO.getNick() == null || memberVO.getNick().trim().length()<0) {
			memberVO.setNick(memberVO.getName());
		}
		memberDAO.insert(memberVO);
		
		//방금 저장된 memberVO의 idx를 가져옴
		int idx = memberDAO.selectById(memberVO.getId()).getIdx();
		
		if(memberVO.getId().equals("admin")) {
			memberDAO.insertRole(new MemberRoleVO(0, idx, "ROLE_ADMIN"));
		}
		memberDAO.insertRole(new MemberRoleVO(0, idx, "ROLE_USER"));
	}

	@Override
	public int selectCount() {
		return memberDAO.selectCount();
	}


	@Override
	public MemberVO selectByIdx(int idx) {
		return memberDAO.selectByIdx(idx);
	}

	//유저정보 페이징 
	@Override
	public PagingVO<MemberVO> selectList(int currentPage, int pageSize, int blockSize) {
		int totalCount = memberDAO.selectCount();
		PagingVO<MemberVO> pagingVO = new PagingVO<MemberVO>(totalCount, currentPage, pageSize, blockSize);
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("endNo", pagingVO.getEndNo());
		map.put("startNo", pagingVO.getStartNo());
		List<MemberVO> list = memberDAO.selectList(map);
		
		for(MemberVO vo : list) {
			//높은 권한만 넣음
			vo.setPassword("");
			MemberRoleVO role = memberDAO.selectRoleByIdx(vo.getIdx());
			vo.setMemberLoleVO(role);
		}
		pagingVO.setList(list);
		logger.info("selectList 리턴 값: "+pagingVO);
		logger.info("selectList 리턴 값-List0-memberLoleVO: "+pagingVO.getList().get(0).getMemberLoleVO());
		logger.info("selectList 리턴 값-memberLoleVO-getRole: "+pagingVO.getList().get(0).getMemberLoleVO().getRole());
		return pagingVO;
	}

	@Override
	public void update(MemberVO memberVO) {
		memberDAO.update(memberVO);
	}


	@Override
	public void updateCertify(int idx) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateLeaveDate(int idx) {
		memberDAO.updateLeaveDate(idx);
	}
	
	@Override
	public void delete() {
		memberDAO.delete();
	}
	

	@Override
	public void updateNoLeave(int idx) {
		memberDAO.updateNoLeave(idx);
	}
	
	@Override
	public List<String> selectRoles() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int idCheck(String id) {
		return memberDAO.selectById(id) == null? 0 : 1;
	}

	@Override
	public MemberVO selectById(String id) {
		return memberDAO.selectById(id);
	}

	@Override
	public String idFind(String email, String birth) {
		String id = "";
		
		MemberVO vo = memberDAO.selectByEmail(email);
		if(vo != null) {
			logger.info("idFind - birth: "+birth);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			logger.info("idFind - voBirth: "+sdf.format(vo.getBirth()));
			if(birth.equals(sdf.format(vo.getBirth()))) {
				id = vo.getId();
			}
		}
		return id;
	}

	@Override
	public int emailCheck(String email) {
		return memberDAO.selectByEmail(email) == null? 0 : 1;
	}

	@Override
	public int pwdFind(String email, String id) {
		int flag = 0;
		MemberVO dbVO = memberDAO.selectById(id);
		
		if(dbVO != null) {
			if(dbVO.getEmail().equals(email)) {
				//sb는 임시 비밀번호
				StringBuffer pwd = new StringBuffer();
				String[] str = "a!b@cde#$f%g^h&i*zjky(lm)_nop?qr~stuvwx".split("");
				
				Random random = new Random();
				
				for(int i = 0; i<10; i++) {
					pwd.append(str[random.nextInt(str.length)]);
				}
				
				String content = "<h1>임시 비밀번호 발급</h1><p>확인 후 반드시 비밀번호를 변경하시기 바랍니다.</p>"
						+ "<br/><h2>"+dbVO.getName()+"님 </h2><p>임시 비밀번호: "+pwd.toString()+"</p>";
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mailService.sendMail(email, "임시 비밀번호 발급해드립니다.", content);
		
					}
				}).start();
								
				//인코딩 후 업데이트
				dbVO.setPassword(bcryptPasswordEncoder.encode(pwd.toString()));
				memberDAO.updatePwd(dbVO);
				flag = 1;
			}
		}
		
		return flag;
	}
}
