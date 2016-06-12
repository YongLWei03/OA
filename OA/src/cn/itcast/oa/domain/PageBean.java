package cn.itcast.oa.domain;

import java.util.List;

public class PageBean { // �����൱��DAO/VO
	// ָ���Ļ���ҳ�����
	private int currentPage;// ��ǰҳ
	private int pageSize;// ÿҳ��ʾ��������¼
	// ��ѯ���ݿ�
	private List records;// ��ҳ�Ļظ��б�
	private int recordCount;// �ܼ�¼��
	// ����
	private int pageCount;// ��ҳ��
	private int beginPageIndex;// ҳ���б�Ŀ�ʼ����
	private int endPageIndex;// ҳ���б�Ľ�������
	
    
	public PageBean(int currentPage, int pageSize, List records,int recordCount) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.records = records;
		this.recordCount = recordCount;
		//������ҳ��
		 pageCount   =(recordCount+pageSize-1)/pageSize;
		 
		//TODO ����benginPageIndex,endPageIndex
		 //�ܵ�ҳ����������10ҳ����ȫ����ʾ
		 if(pageCount<=pageSize){
			 beginPageIndex=1;
			 endPageIndex=pageCount;
			 //��ҳ������10ҳ������ʾ��ǰҳ�����Ĺ�10��ҳ��
		 }else{
			 beginPageIndex=currentPage-(pageSize/2-1);
			 endPageIndex=currentPage+pageSize/2;
			 //��ǰ��ҳ�벻��4��ʱ������ʾǰ10��ҳ��
			 if(beginPageIndex<1){
				 beginPageIndex=1;
				 endPageIndex=pageSize;
			 }
			 //�������ҳ�벻��5����������ʾ��10��ҳ��
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
