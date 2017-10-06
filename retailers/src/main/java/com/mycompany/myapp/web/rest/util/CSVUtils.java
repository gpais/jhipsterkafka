package com.mycompany.myapp.web.rest.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.mycompany.myapp.domain.RetailersTransactions;

public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';

    


    
	  public static InputStream  write(List<RetailersTransactions> transactions) throws UnsupportedEncodingException {
		   StringBuilder sb = new StringBuilder("");
	        for (RetailersTransactions value : transactions) {
	        	sb.append(value.getRetailerNo()).append(",").append(value.getRefundAmount()).append(",").append(value.getVatOff()).append("\n");
	        }
	        
			 InputStream stream = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8.name()));
	        return stream;

	    }
	  
}
