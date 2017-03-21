package java_uml_parser;

import static com.github.javaparser.ast.Modifier.PRIVATE;
import static com.github.javaparser.ast.Modifier.PUBLIC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class MyJavaParser {
	

	private String className;
	private CompilationUnit cu;
	private List<ClassOrInterfaceType> use = new ArrayList<ClassOrInterfaceType>();
	private List<ClassOrInterfaceType> useMany = new ArrayList<ClassOrInterfaceType>();
	private List<ClassOrInterfaceType> useInMethod = new ArrayList<ClassOrInterfaceType>();
	
	
	public MyJavaParser(String directory) {
		
		try{
			FileInputStream in = new FileInputStream(directory);
			this.cu = JavaParser.parse(in);
			if (in != null) {
				in.close();
		    }
		}catch (IOException e){
			System.out.println(e.getMessage());
		}

		parseClassType();
		parseAttributes();
		parseMethods();

	}
	
	private void parseClassType(){
		
		
	}
	
	private void parseAttributes(){
		
		// find getter and setter
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ){
			MethodDeclaration getter = hasGetter( field);
			MethodDeclaration setter = hasSetter( field);
			if(getter != null && setter != null) {
				getter.remove();
				setter.remove();
				field.setModifiers(PUBLIC.toEnumSet());
			}
			
			List<ClassOrInterfaceType> obj = field.getVariable(0).getNodesByType(ClassOrInterfaceType.class);
			if(obj.size() == 2){
				useMany.add(obj.get(1));
			}else if(obj.size() == 1){
				use.add(obj.get(1));
			}
//			System.out.println();
		}
	}
	
	private void parseMethods(){
		
		
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		this.className = cu.getTypes().get(0).getNameAsString();
		ClassOrInterfaceDeclaration myclass = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);

		for(ClassOrInterfaceType declaration : myclass.getExtendedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|-- ");
			result.append(this.className);
			result.append("\n");
		}

		for(ClassOrInterfaceType declaration : myclass.getImplementedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|.. ");
			result.append(this.className);
			result.append("\n");
		}
		
		if(myclass.isInterface()){
			result.append("interface ");
		}else{
			result.append("class ");
			
		}
		result.append(this.className);
		result.append("\n");
		
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ) {
			if(field.getModifiers().contains(PUBLIC)){
				result.append(this.className + " : + ");
		    }else if(field.getModifiers().contains(PRIVATE)){
		    	result.append(this.className + " : - ");
		    }else{
		    	continue;
		    }
			result.append(field.getCommonType() + " " + field.getVariables().get(0) + "\n");
		}
		
		for (MethodDeclaration method : cu.getTypes().get(0).getMethods() ) {
			result.append(this.className + " : " );
			if(method.getModifiers().contains(PUBLIC)){
				result.append("+ ");
		    }
			result.append(method.getDeclarationAsString(false, false) + "\n");
		}
		return result.toString();
	}
	
	private MethodDeclaration hasGetter(FieldDeclaration field){
		for( MethodDeclaration method : cu.getTypes().get(0).getMethods() ){
			List<ReturnStmt> ret = method.getNodesByType(ReturnStmt.class);
			if(ret.size() == 1 && ret.get(0).toString().indexOf(field.getVariable(0).toString()) != -1 && method.getNameAsString().indexOf("get") == 0) {
				return method;
			}
		}
		return null;
	}
	
	private MethodDeclaration hasSetter(FieldDeclaration field){
		for( MethodDeclaration method : cu.getTypes().get(0).getMethods() ){
			List<ExpressionStmt> expression = method.getNodesByType(ExpressionStmt.class);
			if(expression.size() == 1 && expression.get(0).toString().indexOf(field.getVariable(0).toString()) != -1 && method.getNameAsString().indexOf("set") == 0) {
				return method;
			}
		}
		return null;

	}
	
}
