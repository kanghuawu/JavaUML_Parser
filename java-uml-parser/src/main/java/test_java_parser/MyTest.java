package test_java_parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;



public class MyTest {
	public static void main(String[] args) throws FileNotFoundException{
		
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/"
    			+ "cmpe202-java-uml-parser/java-uml-parser/src/main/resources/test.java";
        FileInputStream in = new FileInputStream(dir);

        // parse the file
        CompilationUnit cu = JavaParser.parse(in);

        // prints the resulting compilation unit to default system output
//        System.out.println(cu.getNodesByType(ConstructorDeclaration.class).get(0).getDeclarationAsString(false, false));
        int n = 0;
        for(TypeDeclaration typeDec : cu.getTypes()){
//        	System.out.println(n++);
//        	System.out.println(typeDec.getNameAsString());
        	//field
        	System.out.println("field");
        	List<FieldDeclaration> fields = typeDec.getFields();
        	for(FieldDeclaration field : fields){
//        		System.out.println(field.getNodesByType(ClassOrInterfaceType.class));
//        		System.out.println(field.getElementType().getNodesByType(ExpressionStmt.class));
//        		System.out.println(field.getNodesByType(WildcardType.class));
        	}
        	//method
        	System.out.println("method");
        	List<MethodDeclaration> methods = typeDec.getMethods();
        	for(MethodDeclaration method : methods){
//        		System.out.println(method.getDeclarationAsString(true, true, false)); //public, String
        		System.out.println(method.getNodesByType(FieldAccessExpr.class));
        		try{
//        			System.out.println(method.getParameter(0).getType());
//        			System.out.println(method.getNodesByType(Parameter.class).get(0).getName());
//        			System.out.println(method.getNodesByType(Parameter.class).get(0).getType());
        		}catch(IndexOutOfBoundsException e){
        			System.out.println("out of bound");
        		}
        	}
        }
        
        
	}
}
