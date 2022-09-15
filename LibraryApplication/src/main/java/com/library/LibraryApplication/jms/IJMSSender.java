package com.library.LibraryApplication.jms;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IJMSSender {
	public void sendJMSMessage (WrapperObject text) throws JsonProcessingException;
}
