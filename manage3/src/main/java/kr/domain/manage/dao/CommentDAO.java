package kr.domain.manage.dao;

import java.util.List;

import kr.domain.manage.vo.CommentsVO;

public interface CommentDAO {
	public int selectCount(int ref);
	public List<CommentsVO> selectList(int ref);
	public CommentsVO selectByIdx(int idx);
	public void insert(CommentsVO commentsVO);
	public void update(CommentsVO commentsVO);
	public void delete(int idx);
}
