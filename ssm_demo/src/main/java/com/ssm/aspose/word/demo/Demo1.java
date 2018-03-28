package com.ssm.aspose.word.demo;

import java.net.URL;

import com.aspose.words.BreakType;
import com.aspose.words.DataTable;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.StyleIdentifier;

public class Demo1 {
	public static void main(String[] args) {
		try {
			URL url=Demo1.class.getResource("");
			System.out.println(url.getFile());
			/*
			Document c=new Document(url.getFile()+"self.docx");
			System.out.println(c.getSections().get(1).getText());
			c.getRange().replace("{info2}", "中基幼儿园",true,false);
			*/
			Document c=new Document();
			DocumentBuilder builder = new DocumentBuilder(c);
			// Build the outer table.
			//builder.insertImage("d:/a.png");
			// 插入字段变量
			//builder.insertField("MERGEFIELD name ");
			// 给字段变量赋值
			//c.getMailMerge().execute(new String[]{"name"}, new Object[]{"jinan"});
		/*	Font font = builder.getFont();
	        font.setSize(16);
	        font.setColor(Color.blue);
	        //font.setBold(true);
	        font.setName("Algerian");
	        font.setUnderline(Underline.DOUBLE);
	        builder.write("hello");*/
			
			  builder.insertTableOfContents("\\o \"1-3\" \\h \\z \\u");
		        builder.writeln();
		        // Insert some other fields.
		        builder.write("Page: ");
		        builder.insertField("PAGE");
		        builder.write(" of ");
		        builder.insertField("NUMPAGES");
		        builder.writeln();
		        builder.write("Date: ");
		        builder.insertField("DATE");
		        // Start the actual document content on the second page.
		        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
		        // Build a document with complex structure by applying different heading styles thus creating TOC entries.
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
		        builder.writeln("Heading 1");
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);
		        builder.writeln("Heading 1.1");
		        builder.writeln("Heading 1.2");
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_1);
		        builder.writeln("Heading 2");
		        builder.writeln("Heading 3");
		        // Move to the next page.
		        builder.insertBreak(BreakType.PAGE_BREAK);
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);
		        builder.writeln("Heading 3.1");
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_3);
		        builder.writeln("Heading 3.1.1");
		        builder.writeln("Heading 3.1.2");
		        builder.writeln("Heading 3.1.3");
		        builder.getParagraphFormat().setStyleIdentifier(StyleIdentifier.HEADING_2);
		        builder.writeln("Heading 3.2");
		        builder.writeln("Heading 3.3");
		        System.out.println("Updating all fields in the document.");
		        // Call the method below to update the TOC.
		        c.updateFields();
			c.save("d:/a.docx");
			//c.save(fileName)
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
