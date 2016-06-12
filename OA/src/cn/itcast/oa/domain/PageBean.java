package cn.itcast.oa.domain;

import java.util.List;

public class PageBean { // 作用相当于DAO/VO
	// 指定的或是页面参数
	private int currentPage;// 当前页
	private int pageSize;// 每页显示多少条记录
	// 查询数据库
	private List records;// 本页的回复列表
	private int recordCount;// 总记录数
	// 计算
	private int pageCount;// 总页数
	private int beginPageIndex;// 页码列表的开始索引
	private int endPageIndex;// 页码列表的结束索引
	
    
	public PageBean(int currentPage, int pageSize, List records,int recordCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.records = records;
		this.recordCount = recordCount;
		//计算总页码
		 pageCount   =(recordCount+pageSize-1)/pageSize;
		 
		//TODO 计算benginPageIndex,endPageIndex
		 //总的页面数不多于10页，则全部显示
		 if(pageCount<=pageSize){
			 beginPageIndex=1;
			 endPageIndex=pageCount;
			 //总页数多于10页，则显示当前页附近的共10个页码
		 }else{
			 beginPageIndex=currentPage-(pageSize/2-1);
			 endPageIndex=currentPage+pageSize/2;
			 //当前的页码不足4个时，则显示前10个页码
			 if(beginPageIndex<1){
				 beginPageIndex=1;
				 endPageIndex=pageSize;
			 }
			 //当后面的页码不足5个个，则显示后10个页码
			 if(endPageIndex>pageCount){
				 beginPageIndex=pageCount-(pageSize-1);
				 endPageIndex=pageCount;
			 }
		 }
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
	}



	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}


}
