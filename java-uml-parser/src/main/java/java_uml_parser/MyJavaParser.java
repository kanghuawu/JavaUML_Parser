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
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import net.sourceforge.plantuml.SourceStringReader;

public class MyJavaParser {
	
	private String directory;
	private List<String> javaFiles;
	
	
	public MyJavaParser(String directory){
		this.directory = directory;
		javaFiles = this.findJavaFiles();
	}
	
	private List<String> findJavaFiles(
			){
		List<String> javaFiles = new ArrayList<String>();
		File dir = new File(this.directory);
		for (File file : dir.listFiles()) {
		    if (file.getName().endsWith((".java"))) {
		    	javaFiles.add(this.directory + "/" + file.getName());
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
            System.out.println(n.getName());
            super.visit(n, arg);
        }
    }
	
    public void visit(MethodDeclaration n, Void arg) {
        /* here you can access the attributes of the method.
         this method will be called for all methods in this 
         CompilationUnit, including inner class methods */
        System.out.println(n.getName());
        super.visit(n, arg);
    }
	
	public static void main(String[] args) throws IOException {
//		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-1";
//		MyJavaParser par = new MyJavaParser(directory);
//		List<String> java = par.findJavaFiles();
//		for(String name : java){
//			System.out.println(name);
//		}
		
		
		
        // creates an input stream for the file to be parsed
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4/ConcreteObserver.java";
        FileInputStream in = new FileInputStream(dir);

        // parse the file
        CompilationUnit cu = JavaParser.parse(in);

        // prints the resulting compilation unit to default system output
        System.out.println(cu.toString());
		
		
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
