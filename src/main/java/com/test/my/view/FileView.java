package com.test.my.view;

import java.time.LocalDateTime;

public interface FileView {
	
	public Long getId();
	public String getOriginalName();
	public String getSavedName();
	public String getDirPath();
	public String getFileUrl();
	public Integer getSize();
	public LocalDateTime getRegDate();
	public LocalDateTime getUpdDate();
}
