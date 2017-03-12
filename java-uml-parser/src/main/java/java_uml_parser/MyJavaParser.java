package java_uml_parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;



public class MyJavaParser {
	

	private String directory;
	private List<CompilationUnit> javaFiles;
	private StringBuilder parsedStr;
	
	public MyJavaParser(String directory) throws FileNotFoundException{
		this.directory = directory;
		javaFiles = this.findJavaFiles();
	}
	
	private List<CompilationUnit> findJavaFiles() throws FileNotFoundException{
		List<CompilationUnit> javaFiles = new ArrayList<CompilationUnit>();
		File dir = new File(this.directory);
		String filename;
		CompilationUnit cu;
		for (File file : dir.listFiles()) {
			
		    if (file.getName().endsWith((".java"))) {
		    	filename = this.directory + "/" + file.getName();
		    	cu = JavaParser.parse(new FileInputStream(filename));
		    	javaFiles.add(cu);
		    }
		  }
		return javaFiles;
	}
	
	private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            /* here you can access the attributes of the method.
             this method will be called for all methods in this 
             CompilationUnit, including inner class methods */
        	System.out.println(n.toString());
            System.out.println(n.getType() + " : " + n.getName());
            super.visit(n, arg);
        }
    }
	
	
	public static void main(String[] args) throws IOException {
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4";
		MyJavaParser par = new MyJavaParser(directory);
		List<CompilationUnit> java = par.findJavaFiles();
		for(CompilationUnit cu : java){
			System.out.println(cu.toString());
			cu.getNodesByType(MethodDeclaration.class).stream().forEach(f -> System.out.println(f.getModifiers()));
//			System.out.println(cu.getClassByName("A"));
//			new MethodVisitor().visit(cu, null);
		}
		
		
		
		
		
//		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/test.png";
//		OutputStream png = new FileOutputStream(dir);
//		String source = "@startuml\n";
//		source += "nf -> sixty_seven : is\n";
//		source += "@enduml\n";
//
//		SourceStringReader reader = new SourceStringReader(source);
//		// Write the first image to "png"
//		String desc = reader.generateImage(png);
//		System.out.println(desc);
//		// Return a null string if no generation
//		System.out.println("test");
    }
}
