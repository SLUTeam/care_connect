package com.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.itextpdf.text.pdf.PdfWriter;



@Component
public class PDFGeneratorUtil {

	public static byte[] generatePDF(String string, Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}
    
//	public static byte[] generatePDF(String templateName, Map<String, Object> parameters) throws IOException {
//        
//	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//	    templateResolver.setPrefix("templates/");
//	    templateResolver.setSuffix(".html");
//	    templateResolver.setTemplateMode(TemplateMode.HTML);
//	    templateResolver.setCharacterEncoding("UTF-8");
//	        
//	    TemplateEngine templateEngine = new TemplateEngine();
//	    templateEngine.setTemplateResolver(templateResolver);
//	        
//	    String html = templateEngine.process(templateName, new Context(Locale.getDefault(), parameters));
//	        
//	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        
//	    PdfWriter writer = new PdfWriter(outputStream);
//	    PdfDocument pdf = new PdfDocument(writer);
//	  
//  HtmlConverter.convertToPdf(html, pdf);
//  
//	      byte[] pdfBytes = outputStream.toByteArray();
//	    outputStream.close();
//	        
//	    return pdfBytes;
	}


