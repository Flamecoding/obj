package util;

/**
 * JSP에서 데이터를 page navigation 형식으로 구성하기 위해 사용되는 클래스이다.
 */
public class PageUtility {
	int firstPageCount = 0; // 목록에서 보여줄 첫페이지 링크 번호 1 2 3 4 5 (1)
	int lastPageCount = 0; // 목록에서 보여줄 마지막 페이지 링크+1 1 2 3 4 5 (6)

	int nextPageCount = 0; // 다음 페이지
	int beforePageCount = 0; // 이전 페이지
	int currentPageCount = 0; // 현재 페이지

	int pagePercount = 5; // 목록에서 보여줄 페이지 개수 1 2 3 4 5
	int beforeTenpage = 0; // 이전 pagePercount 페이지
	int nextTenpage = 0; // 다음 pagePercount 페이지

	int totalRowCount = 0; // 총 row 개수
	int totalPageCount = 0; // 총 페이지 수
	int displayRowCount = 0; // 한 페이지당 보여줄 개수
	
	//String imagePath = "resources/images/";
	String search;

	public PageUtility(int displayRowCount, int totalRowCount, int currentPageCount) throws Exception {
		super();
		this.displayRowCount = displayRowCount;
		this.totalRowCount = totalRowCount;
		this.currentPageCount = currentPageCount;
		if (totalRowCount % displayRowCount == 0)
			this.totalPageCount = totalRowCount / displayRowCount;
		else
			this.totalPageCount = totalRowCount / displayRowCount + 1;
	}

	public String getCurrentPageCount() {
		return String.valueOf(currentPageCount);
	}

	/**
	 * 목록에 출력할 page bar를 구성한다.
	 * 
	 * @return java.lang.String
	 */
	public String getPageBar() {
		// ex) pagePercount: 5 이고 7인 경우의 계산
		StringBuffer sb = new StringBuffer();
		// currentpagecount: 3인 경우 1 2 3 4 5 등에서 표시할 첫 페이지 (1)
		// currentpagecount: 7인 경우 6 7 8 9 10 등에서 표시할 첫 페이지 (6)
		firstPageCount = ((currentPageCount - 1) / pagePercount * pagePercount) + 1;
		// currentpagecount: 3인 경우 1 2 3 4 5 등에서 표시할 다음 페이지 (6)
		// currentpagecount: 7인 경우 6 7 8 9 10 등에서 표시할 첫 페이지 (11)
		lastPageCount = firstPageCount + pagePercount;

		// currentpagecount: 3인 경우 -4
		// currentpagecount: 7인 경우 1
		beforeTenpage = firstPageCount - pagePercount;
		beforeTenpage = beforeTenpage < 1 ? 1 : beforeTenpage;

		nextTenpage = lastPageCount; // (6 / 11)
		if (nextTenpage > totalPageCount)// 만약 총 페이지 카운트가 4 / 9이면
			nextTenpage = (((totalPageCount - 1) / pagePercount) + 1) * pagePercount;// 5 / 10

		if (lastPageCount > totalPageCount)
			lastPageCount = totalPageCount + 1;

		System.out.println("firstPageCount:" + firstPageCount);
		System.out.println("lastPageCount:" + lastPageCount);
		System.out.println("beforeTenpage:" + beforeTenpage);
		System.out.println("nextTenpage:" + nextTenpage);

		/*
		 * <ul class="pagination">
			  <li class="disabled"><a href="#">&laquo;</a></li>
			  <li class="active"><a href="#">1</a></li>
			  <li><a href="#">2</a></li>
			  <li><a href="#">3</a></li>
			  <li><a href="#">4</a></li>
			  <li><a href="#">5</a></li>
			  <li><a href="#">&raquo;</a></li>
			</ul>
		 */
		
		// 맨 첫페이지 link 처리
//		if (currentPageCount > pagePercount) {
//			// 현재 표시하는 페이지가 pagePercount보다 크면 첫페이지로 갈수 있으므로 첫페이지로 링크
//			//sb.append("<a href='javascript:pagelist(" + 1 + ")'></a>");
//			sb.append("<li class='active'><a href='javascript:pagelist(" + 1 + ")'></a></li>");
//		} else {
//			// 총 페이지가 pagePerCount보다 작거나 현재 표시 페이지가 pagePerCount 내에 있으므로 첫페이지로 갈 필요 없으므로 이미지만 표시
//			//sb.append("<img src=\"" + imagePath + "btn_paging_first.gif\" border='0'  align=absmiddle>&nbsp;&nbsp;");
//			sb.append("<li><a href='javascript:pagelist(" + 1 + ")'></a></li>");
//		}

		// 이전 pagePercount 페이지
		if (((currentPageCount - 1) / pagePercount * pagePercount) + 1 > beforeTenpage) {
			// 현재 표시 페이지가 beforetenpage 보다 크면 이전 페이지로 링크
			//sb.append("<a href='javascript:pagelist(" + beforeTenpage + ")'></a>&nbsp;&nbsp;");
			sb.append("<li class='disabled'><a href='javascript:pagelist(" + beforeTenpage + ")'>&laquo;</a></li>");
		} else {
			// 현재 표시 페이지가 beforetenpage 보다 작으면 이미지만 표시
			//sb.append("<img src=\"" + imagePath + "btn_paging_prev.gif\" border='0'   align=absmiddle>&nbsp;&nbsp;");
			sb.append("<li class='disabled'><a href='#'>&laquo;</a></li>");
		}

		// pagePercount 만큼 link
		for (int i = firstPageCount; i < lastPageCount; i++) {
			if (i <= totalPageCount) {
				if (i == currentPageCount) {
					//sb.append("<b>" + i + "</b>");
					sb.append("<li><a href='javascript:pagelist(" + i + ")'>" + i + "</a></li>");
				} else {
					//sb.append("<a href='javascript:pagelist(" + i + ")'>" + i + "</a>");
					sb.append("<li><a href='javascript:pagelist(" + i + ")'>" + i + "</a></li>");
				}
				if (i != lastPageCount - 1) {
					//sb.append(" . ");
				}
			}
		}

		// 다음 pagePercount 페이지 link
		if (nextTenpage < ((totalPageCount - 1) / pagePercount + 1) * pagePercount) {
			//sb.append("&nbsp;&nbsp;<a href='javascript:pagelist(" + (nextTenpage) + ")'></a>");
			sb.append("<li><a href='javascript:pagelist(" + (nextTenpage) + ")'>&raquo;</a></li>");
		} else {
			//sb.append("&nbsp;&nbsp;<img src=\"" + imagePath + "btn_paging_next.gif\" border='0' hspace='3' align=absmiddle>");
			sb.append("<li><a href='#'>&raquo;</a></li>");
		}
				
		// 맨 끝 페이지 link
//		if (currentPageCount < totalPageCount) {
//			sb.append("&nbsp;&nbsp;<a href='javascript:pagelist(" + (totalPageCount) + ")'></a>");
//		}
//			//sb.append("&nbsp;&nbsp;<img src=\"" + imagePath + "btn_paging_end.gif\" border='0' align=absmiddle>");

		return sb.toString();
	}

	public String getTotalPageCount() {
		return String.valueOf(totalPageCount);
	}
}