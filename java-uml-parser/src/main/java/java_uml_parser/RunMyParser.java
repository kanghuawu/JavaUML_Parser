package java_uml_parser;

import java.io.FileNotFoundException;

public class RunMyParser {
	public static void main(String[] args) throws FileNotFoundException{
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-3";
		
		fileFinder fileDir = new fileFinder(directory);
		
		StringBuilder sb = new StringBuilder();
		for(String javaFile : fileDir.getJavaFiles()){
			MyJavaParser javaParser = new MyJavaParser(javaFile);
			sb.append(javaParser.getParsedFile());
		}
		
		System.out.println(sb.toString());
	}
}
