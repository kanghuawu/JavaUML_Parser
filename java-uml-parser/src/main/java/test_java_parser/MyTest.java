package test_java_parser;

import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

public class MyTest {
	public static void main(String[] args){
		CompilationUnit compilationUnit = JavaParser.parse("public class A {  }");
		Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
		compilationUnit.getNodesByType(FieldDeclaration.class).stream().filter(f -> f.getModifiers().contains("public") && !f.getModifiers().contains("static")).forEach(f -> System.out.println("Check field at line " + f.getBegin().get().line));
	}
}
