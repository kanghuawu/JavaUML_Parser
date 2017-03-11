package java_uml_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import net.sourceforge.plantuml.SourceStringReader;

public class MyJavaParser {
	
	private static String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/cmpe202/umlparser/uml-parser-test-1";
	
	private List<String> findJavaFiles(String directory){
		List<String> javaFiles = new ArrayList<String>();
		File dir = new File(directory);
		for (File file : dir.listFiles()) {
		    if (file.getName().endsWith((".java"))) {
		    	javaFiles.add(directory + "/" + file.getName());
		    }
		  }
		return javaFiles;
	}
	
	
	
	public static void main(String[] args) throws IOException {
//		MyJavaParser par = new MyJavaParser();
//		List<String> java = par.findJavaFiles(directory);
//		for(String name : java){
//			System.out.println(name);
//		}
//        // creates an input stream for the file to be parsed
//		String dir = "/Users/bondk/Downloads/sequenceDiagram.txt";
//        FileInputStream in = new FileInputStream(dir);
//
//        // parse the file
//        CompilationUnit cu = JavaParser.parse(in);
//
//        // prints the resulting compilation unit to default system output
//        System.out.println(in.toString());
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/test.png";
		OutputStream png = new FileOutputStream(dir);
		String source = "@startuml\n";
		source += "nf -> sixty_seven : is\n";
		source += "@enduml\n";

		SourceStringReader reader = new SourceStringReader(source);
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		System.out.println(desc);
		// Return a null string if no generation
		System.out.println("test");
    }
}
