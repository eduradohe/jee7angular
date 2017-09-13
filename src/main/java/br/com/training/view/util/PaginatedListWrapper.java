package br.com.training.view.util;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaginatedListWrapper<T> {
	
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalResults;
	
	private String sortFields;
	private String sortDirections;
	@XmlElement
	private List<T> list;
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}
	public String getSortFields() {
		return sortFields;
	}
	public void setSortFields(String sortFields) {
		this.sortFields = sortFields;
	}
	public String getSortDirections() {
		return sortDirections;
	}
	public void setSortDirections(String sortDirections) {
		this.sortDirections = sortDirections;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
