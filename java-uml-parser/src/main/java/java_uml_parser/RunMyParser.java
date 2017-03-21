package java_uml_parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RunMyParser {
	public static void main(String[] args) throws IOException{
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4";
		
		fileFinder fileDir = new fileFinder(directory);
		
		List<MyJavaParser> totalObjects = new ArrayList<MyJavaParser>();
		StringBuilder sb = new StringBuilder();
		
		for(String javaFile : fileDir.getJavaFiles()){
			MyJavaParser javaParser = new MyJavaParser(javaFile);
			totalObjects.add(javaParser);
			sb.append(javaParser.toString());
		}
		
		sb.append(findUseRelation(totalObjects));
		sb.insert(0, "@startuml\n");
		sb.append("skinparam classAttributeIconSize 0\n");
		sb.append("@enduml\n");
//		System.out.println(sb.toString());
		
		
//		String pngDir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
//				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/output.png";
//		OutputStream png = new FileOutputStream(pngDir);
//		SourceStringReader reader = new SourceStringReader(sb.toString());
//		// Write the first image to "png"
//		String desc = reader.generateImage(png);
//		System.out.println(desc);
//		// Return a null string if no generation

	}
	
	private static String findUseRelation(List<MyJavaParser> totalObjects){
		StringBuilder relation = new StringBuilder();

		int size = totalObjects.size();
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j < size; j ++){
				
				MyJavaParser objA = totalObjects.get(i);
				MyJavaParser objB = totalObjects.get(j);
				System.out.println(objA.getUse() + " /// " + objB.getUse());
				if(!objA.getUse().containsKey(objB.getName()) && !objB.getUse().containsKey(objA.getName())) continue;
				else{
					
					relation.append(objA.getName());
					relation.append("--");
					relation.append(objB.getName());
				}
			}
		}
		return relation.toString();
	}
}
