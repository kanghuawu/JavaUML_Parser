package test_java_parser;

import java.io.FileNotFoundException;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class MyTest {
	public static void main(String[] args) throws FileNotFoundException{
		
		CompilationUnit compilationUnit = JavaParser.parse("class A { }");
		Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
		System.out.println(classA.toString());
		
//		String directory = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
//				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-3/ClassB.java";
//		MyJavaParser par = new MyJavaParser(directory);
//		StringBuilder sb = par.getParsedFile();
//		System.out.println(sb.toString());
		
//		FileInputStream file = new FileInputStream(directory);
//		CompilationUnit compilationUnit = JavaParser.parse(file);
//		
//		Stream<FieldDeclaration> attributeNodes = compilationUnit.getNodesByType(FieldDeclaration.class).stream().filter(f -> f.getModifiers().contains(Modifier.PUBLIC) || f.getModifiers().contains(Modifier.PRIVATE));
//		attributeNodes.forEach(f -> System.out.println(f.getCommonType() + " " +  f.getVariables().get(0)));
		
//		attributeNodes.forEach(f -> System.out.println(f.getVariables()));
//		for(CompilationUnit cu : java){
////			System.out.println(cu.toString());
//			Stream<ClassOrInterfaceDeclaration> result = cu.getNodesByType(ClassOrInterfaceDeclaration.class).stream()
//					.filter(f -> f.getModifiers().contains(Modifier.PUBLIC) );
//			result.forEach(f -> System.out.println(f.getImplementedTypes().get(0)));
////			System.out.println(cu.getClassByName("A"));
////			new MethodVisitor().visit(cu, null);
//		}
		
		
		
		
		
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
