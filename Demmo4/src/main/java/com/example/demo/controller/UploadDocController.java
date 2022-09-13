package com.example.demo.controller;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.UploadDoc;
import com.example.demo.repositore.UploadDocRepo;
import com.google.common.net.HttpHeaders;


@Controller
public class UploadDocController {

	@Autowired
	UploadDocRepo UploadDocRepo;
	
	@Value("${document.upload.path}")
	private String uploadDocPath1;
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String show(Model model) {
		model.addAttribute("mm",UploadDocRepo.findAll());
		return "upload";
	}
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String save(RedirectAttributes redirectAttributes,
			@RequestParam(value = "dscertificate1", required = false) MultipartFile dscertificate) throws IOException {
		System.out.println("save coming.........");		
		System.out.println(dscertificate);
		UploadDoc doc=new UploadDoc();
		String originalFilename =StringUtils.cleanPath(dscertificate.getOriginalFilename()).trim();		
		doc.setUploadDocPath(originalFilename);
		doc.setData(dscertificate.getBytes());
		doc.setContentType(dscertificate.getContentType());
		Path path=Paths.get(uploadDocPath1);
		if(!Files.exists(path)) { //Create FIles in the directory
			Files.createDirectories(path);
		}
		InputStream inputStream=dscertificate.getInputStream();
		
		java.nio.file.Path path2=path.resolve(originalFilename);
		Files.copy(inputStream, path2,StandardCopyOption.REPLACE_EXISTING);
		UploadDocRepo.save(doc);
		redirectAttributes.addFlashAttribute("status", "Data Saved successfully");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/download/{id}",method = RequestMethod.GET)
	public void download(@PathVariable int id,HttpServletResponse httpServletResponse) throws IOException {
		UploadDoc uploadDoc=UploadDocRepo.findById(id).get();
		String pathname=uploadDocPath1+"/" +uploadDoc.getUploadDocPath();
		System.out.println(pathname);
//		File file=new File(pathname);
//		String mimeType=URLConnection.guessContentTypeFromName(file.getName());
//		httpServletResponse.setContentType(mimeType);
//		httpServletResponse.setHeader("Content-Dispostion", String.format("attachment; filename=\"" + file.getName()+"\""));
//		httpServletResponse.setContentLength((int) file.length());
//		InputStream inputStream=new BufferedInputStream(new FileInputStream(file));
//		FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());
		
		File file = new File(pathname);
		if(!file.exists()){
		String errorMessage = "Sorry. The file you are looking for does not exist";
		OutputStream outputStream = httpServletResponse.getOutputStream();
		outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
		outputStream.close();
		return ;
		}
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
		mimeType = "application/octet-stream";
		}

		httpServletResponse.setContentType(mimeType);
		httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
		httpServletResponse.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());
	}
	
	
	

	/*
	 * //Upload Only png and Excel File But Not Pdf 
	 * @RequestMapping(value = "/",method = RequestMethod.GET) public String
	 * show(Model model) { model.addAttribute("mm",UploadDocRepo.findAll()); return
	 * "upload"; }
	 * 
	 * @RequestMapping(value = "/upload",method = RequestMethod.POST) public String
	 * save(RedirectAttributes redirectAttributes,
	 * 
	 * @RequestParam(value = "dscertificate1", required = false) MultipartFile
	 * dscertificate) throws IOException {
	 * System.out.println("save coming.........");
	 * System.out.println(dscertificate); UploadDoc doc=new UploadDoc(); String
	 * originalFilename = dscertificate.getOriginalFilename();
	 * doc.setUploadDocPath(originalFilename);
	 * doc.setData(dscertificate.getBytes());
	 * doc.setContentType(dscertificate.getContentType());
	 * 
	 * UploadDocRepo.save(doc); redirectAttributes.addFlashAttribute("status",
	 * "Data Saved successfully"); return "redirect:/"; }
	 * 
	 * 
	 * @RequestMapping(value = "/download/{id}", method = RequestMethod.GET) public
	 * ResponseEntity<ByteArrayResource> download(@PathVariable int id,
	 * HttpServletResponse httpServletResponse) { UploadDoc uploadDoc =
	 * UploadDocRepo.findById(id).get(); return
	 * ResponseEntity.ok().contentType(MediaType.parseMediaType(uploadDoc.
	 * getContentType())) .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment:filename=\"" + uploadDoc.getUploadDocPath() + "\"") .body(new
	 * ByteArrayResource(uploadDoc.getData())); }
	 */
}
