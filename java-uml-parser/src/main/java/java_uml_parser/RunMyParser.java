package java_uml_parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import net.sourceforge.plantuml.SourceStringReader;

public class RunMyParser {
	public static void main(String[] args) throws IOException{
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/peronsal_project"
				+ "/cmpe202-personal-project/java-uml-parser/src/main/resources/uml-parser-test-5";
		
		JavaFileFinder fileDir = new JavaFileFinder(directory);
		
		List<MyJavaParser> totalObjects = new ArrayList<MyJavaParser>();
		StringBuilder sb = new StringBuilder();
		HashSet<String> interfaces = new HashSet<String>(); 
		for(String javaFile : fileDir.getJavaFiles()){
			MyJavaParser javaParser = new MyJavaParser(javaFile);
			if(javaParser.isInterface()) interfaces.add(javaParser.getName());
			totalObjects.add(javaParser);
			sb.append(javaParser.getParsedResult());
		}
		
		sb.append(MyJavaParser.getParsedAssiciations(totalObjects));
		sb.append(MyJavaParser.getParsedDepedencies(totalObjects, interfaces));
		sb.insert(0, "@startuml\n");
//		sb.append("skinparam classAttributeIconSize 0\n");  // modifiers format Public(+) and Private(-)
		sb.append("@enduml\n");
		System.out.println(sb.toString());

		
//		String pngDir = "/Users/bondk/Dropbox/SJSU/CMPE202/peronsal_project"
//				+ "/cmpe202-personal-project/java-uml-parser/src/main/resources/"
//				+ directory.substring(directory.lastIndexOf("/") +  1) + ".png";
//		OutputStream png = new FileOutputStream(pngDir);
//		SourceStringReader reader = new SourceStringReader(sb.toString());
//		// Write the first image to "png"
//		String desc = reader.generateImage(png);
//		System.out.println(desc);
//		// Return a null string if no generation
	}
	
	
}
