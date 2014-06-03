package edu.zju.bme.clever.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/singleFileUpload", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult uploadSingleFile(MultipartFile file) {
		FileUploadResult result = new FileUploadResult();
		return result;
	}

	public class FileUploadResult {
		
	}
	
	public class FileUploadResultConstant{
		public static final String EXISTED = "EXISTED";
	}
}
