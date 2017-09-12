package br.com.mobicare.view.services;

public class MobicareApiResponse<T> {
	
	private T content;
	
	public MobicareApiResponse() {
		super();
	}
	
	public MobicareApiResponse( final T content ) {
		this();
		this.setContent(content);
	}
	
	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
}
