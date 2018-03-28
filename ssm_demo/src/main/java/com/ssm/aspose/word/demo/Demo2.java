package com.ssm.aspose.word.demo;

import java.net.URL;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.Node;
import com.aspose.words.PageSetup;
import com.aspose.words.Section;
import com.aspose.words.SectionStart;


public class Demo2 {
	public static void main(String[] args) {
		URL url1=Demo1.class.getResource("");
		URL url2=Demo1.class.getResource("");
		 com.aspose.words.License wordLicense = new com.aspose.words.License();
	      com.aspose.cells.License excelLicense = new com.aspose.cells.License();
	      try {
	        wordLicense.setLicense(Demo2.class.getResourceAsStream("/Aspose.Total.Java.lic"));
	        excelLicense.setLicense(Demo2.class.getResourceAsStream("/Aspose.Total.Java.lic"));
	       
	      } catch (Exception e) {
	       
	      }      
		try {
		
			Document doc1=new Document(url1.getFile()+"self.docx");
			
			
			Document doc2=new Document(url1.getFile()+"self_ztqk.docx");
			Document doc3=new Document(url1.getFile()+"self_level1.docx");
			// 文档添加
			/*for(Section srcSection : doc2.getSections()) {
				Node node=doc1.importNode(srcSection, true,ImportFormatMode.KEEP_SOURCE_FORMATTING);
				doc1.appendChild(node);
			}*/
			Document doc4=new Document(url1.getFile()+"self_level2.docx");
			//Document doc5=new Document(url1.getFile()+"self.docx");
		
			// 文档添加
			/*for(Section srcSection : doc3.getSections()) {
				Node node=doc1.importNode(srcSection, true,ImportFormatMode.KEEP_SOURCE_FORMATTING);
				doc1.appendChild(node);
			}*/
			
			 doc2.getFirstSection().getPageSetup().setSectionStart(SectionStart.NEW_PAGE);
		     doc1.appendDocument(doc2, ImportFormatMode.KEEP_SOURCE_FORMATTING);
		     doc3.getFirstSection().getPageSetup().setSectionStart(SectionStart.CONTINUOUS);
		     doc1.appendDocument(doc3, ImportFormatMode.KEEP_SOURCE_FORMATTING);
			
			doc1.appendDocument(doc4, ImportFormatMode.KEEP_SOURCE_FORMATTING);
			
			//doc1.appendDocument(doc5, ImportFormatMode.USE_DESTINATION_STYLES);
			doc1.save("d:/all.docx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
