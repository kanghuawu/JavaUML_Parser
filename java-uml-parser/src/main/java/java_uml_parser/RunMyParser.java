package java_uml_parser;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



public class RunMyParser {
	public static void main(String[] args) throws IOException{
		
		if(args.length != 2){
			System.out.println("Please pass two arguments: java -jar umlparser.jar <source folder> <output file name>" );
			return;
		}
		System.out.println("Input dir...");
		String input_dir = args[0];
		String output_dir = args[1];
		System.out.println("Input directory is " + input_dir);
		System.out.println("Output directory is " + output_dir);
		File in = new File(input_dir);
		File out = new File(output_dir.substring(0, output_dir.lastIndexOf("/")));
		if(!in.exists()) {
			System.out.println(in);
			System.out.println("Input directory does not exist!");
			return;
		}else if (!out.exists()){
			System.out.println(out);
			System.out.println("Output direcotry does not exist!");
			return;
		}
		System.out.println("Finding Java files...");
		JavaFileFinder fileDir = new JavaFileFinder(input_dir);
		
		List<MyJavaParser> totalObjects = new ArrayList<MyJavaParser>();
		StringBuilder sb = new StringBuilder();
		HashSet<String> interfaces = new HashSet<String>();
		System.out.println("Parsing Java files...");
		for(String javaFile : fileDir.getJavaFiles()){
			MyJavaParser javaParser = new MyJavaParser(javaFile);
			if(javaParser.isInterface()) interfaces.add(javaParser.getName());
			totalObjects.add(javaParser);
			sb.append(javaParser.getParsedResult());
		}
		
		sb.append(MyJavaParser.getParsedAssiciations(totalObjects));
		sb.append(MyJavaParser.getParsedDepedencies(totalObjects, interfaces));
		sb.insert(0, "@startuml\n");
		sb.append("skinparam classAttributeIconSize 0\n");  // modifiers format Public(+) and Private(-)
		sb.append("@enduml\n");
//		System.out.println(sb.toString());
		
		OutputStream png = new FileOutputStream(output_dir);
		SourceStringReader reader = new SourceStringReader(sb.toString());
		// Write the first image to "png"
		System.out.println("Generating UML diagrams...");
		String desc = reader.generateImage(png);
		System.out.println(desc);
		// Return a null string if no generation
	}
}
