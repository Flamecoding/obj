package util;

import java.io.Serializable;


public class PageBean implements Serializable {
	/** 검색 조건  이름, 아이디... */
	private String key;
	
	/** 검색 단어 */
	private String word; 
	
	/** 페이징 처리에 대한 link 정보 */
	private String pageLink;
	
	/** 현재 페이 번호 */
	private int pageNo; 
	
	/** 한 페이지에 보여줄 데이타 개수 */
	private int interval = 5;
	
	/** 페이지의 시작 번호 */
	private int start=0; 
	
	/** 페이지의 끝 번호 */
	private int end = interval ;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		if(key==null) this.key="all";
		else this.key = key;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		if(word==null) this.word = "";
		else this.word = word;
	}
	
	public String getPageLink() {
		return pageLink;
	}
	
	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		if(pageNo>0) this.pageNo = pageNo;
		else this.pageNo=1;
	}
	
	public void setPageNo(String pageNo) {
		try {
			this.pageNo = Integer.parseInt(pageNo);
		} catch (Exception e) {
			this.pageNo = 1;
		}
	}
	
	public String getKey(String k){
		if(k!=null && key.equals(k)) return "selected='selected'";
		else return "";
	}
	
	public PageBean(){
		setKey("all");
		setWord("");
		setPageLink("1");
		setPageNo(1);
	}
	
	public PageBean(String key, String word, String pageLink, int pageNo) {
		setKey(key);
		setWord(word);
		setPageLink(pageLink);
		setPageNo(pageNo);
	}
	
	public PageBean(String key, String word, String pageLink, String pageNo) {
		setKey(key);
		setWord(word);
		setPageLink(pageLink);
		setPageNo(pageNo);
	}
	
	public int getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	public int getStart() {
		if(pageNo>1){
			return start =  (pageNo -1) * interval ;
		}else{
			return   0;
		}
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
			return start+interval - 1;
	}
	
	public void setEnd(int end) {
		this.end =end;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PageBean [key=").append(key).append(", word=")
				.append(word).append(", pageLink=").append(pageLink)
				.append(", pageNo=").append(pageNo).append(", interval=")
				.append(interval).append(", start=").append(start)
				.append(", end=").append(end).append("]");
		return builder.toString();
	}
	
}











