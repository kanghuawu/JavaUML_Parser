package test_java_parser;

import java.io.FileNotFoundException;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

import java_uml_parser.MyJavaParser;

public class MyTest {
	public static void main(String[] args) throws FileNotFoundException{
		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser"
				+ "/java-uml-parser/src/main/resources/uml-parser-test-4";
		MyJavaParser par = new MyJavaParser(directory);
		CompilationUnit compilationUnit = JavaParser.parse("public class A {  }");
		Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
		compilationUnit.getNodesByType(FieldDeclaration.class).stream().filter(f -> f.getModifiers().contains("public") && !f.getModifiers().contains("static")).forEach(f -> System.out.println("Check field at line " + f.getBegin().get().line));
	}
}
