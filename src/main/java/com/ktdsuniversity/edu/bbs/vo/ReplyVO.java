package com.ktdsuniversity.edu.bbs.vo;

public class ReplyVO {
		private int replyId;
		private int boardId;
		private String email;
		private String content;
		private String crtDt;
		private String mdfyDt;
		private int recommendCnt;
		private int parentReplyId;
		public int getReplyId() {
			return replyId;
		}
		public void setReplyId(int replyId) {
			this.replyId = replyId;
		}
		public int getBoardId() {
			return boardId;
		}
		public void setBoardId(int boardId) {
			this.boardId = boardId;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCrtDt() {
			return crtDt;
		}
		public void setCrtDt(String crtDt) {
			this.crtDt = crtDt;
		}
		public String getMdfyDt() {
			return mdfyDt;
		}
		public void setMdfyDt(String mdfyDt) {
			this.mdfyDt = mdfyDt;
		}
		public int getRecommendCnt() {
			return recommendCnt;
		}
		public void setRecommendCnt(int recommendCnt) {
			this.recommendCnt = recommendCnt;
		}
		public int getParentReplyId() {
			return parentReplyId;
		}
		public void setParentReplyId(int parentReplyId) {
			this.parentReplyId = parentReplyId;
		}

}
