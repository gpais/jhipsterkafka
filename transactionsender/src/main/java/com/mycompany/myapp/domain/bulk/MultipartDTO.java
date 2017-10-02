package com.mycompany.myapp.domain.bulk;

import org.springframework.web.multipart.MultipartFile;

public class MultipartDTO{

    private MultipartFile transactionsFile;

	public MultipartFile getTransactionsFile() {
		return transactionsFile;
	}

	public void setTransactionsFile(MultipartFile transactionsFile) {
		this.transactionsFile = transactionsFile;
	} 

}