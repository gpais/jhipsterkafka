package com.mycompany.myapp.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class ParseUtils {
	
	public static Stream<String>  parse(MultipartFile file) throws IOException{
		ByteArrayInputStream stream = new   ByteArrayInputStream(file.getBytes());
		String myString = IOUtils.toString(stream, "UTF-8");
		myString.split("[\\r\\n]+");
		return Arrays.asList(myString).stream();
	}

}
