package br.com.anteros.core.email;

public class EmailAttachment {

	private String name;
	private byte[] content;
	private String contentType;
	
	public EmailAttachment(String name, byte[] content, String contentType) {
		this.name = name;
		this.content = content;
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return this.name+", "+this.contentType+", tamanho "+this.content.length;
	}


}
