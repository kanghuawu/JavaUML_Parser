package java_uml_parser;

import java.io.*;
import net.sourceforge.plantuml.SourceStringReader;

public class RunMyParser {
	public static void main(String[] args) throws IOException{
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-1";
		
		fileFinder fileDir = new fileFinder(directory);
		
		StringBuilder sb = new StringBuilder();
		for(String javaFile : fileDir.getJavaFiles()){
			MyJavaParser javaParser = new MyJavaParser(javaFile);
			sb.append(javaParser.getParsedFile());
		}
		
		sb.insert(0, "@startuml\n");
		sb.append("@enduml\n");
		System.out.println(sb.toString());
		
		
		String pngDir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/output.png";
		OutputStream png = new FileOutputStream(pngDir);
		SourceStringReader reader = new SourceStringReader(sb.toString());
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		System.out.println(desc);
		// Return a null string if no generation

	}
}
