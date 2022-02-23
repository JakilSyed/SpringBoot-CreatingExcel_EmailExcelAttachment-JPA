package com.jakil.emailexcel.emailutil;

import java.util.Arrays;

public class EmailAttachmentModel {

	private String mimeType;
	private String filename;
	private byte content[];

	public EmailAttachmentModel() {
	}

	public EmailAttachmentModel(String mimeType, String filename, byte[] content) {
		super();
		this.mimeType = mimeType;
		this.filename = filename;
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "EmailAttachmentModel [mimeType=" + mimeType + ", filename=" + filename + ", content="
				+ Arrays.toString(content) + "]";
	}

}
