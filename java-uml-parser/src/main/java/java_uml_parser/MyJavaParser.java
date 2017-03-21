package java_uml_parser;

import static com.github.javaparser.ast.Modifier.PRIVATE;
import static com.github.javaparser.ast.Modifier.PUBLIC;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class MyJavaParser {
	

	private CompilationUnit cu;
	private HashMap<String, String> use = new HashMap<String, String>();
	private HashSet<String> useInMethod = new HashSet<String>();
	
	public MyJavaParser(){
		
	}
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
	
	public String getName(){
	return cu.getType(0).getNameAsString();
}
	
	private void parseAttributes(){
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ){
			// find getter and setter then set according attribute to public
			MethodDeclaration getter = hasGetter( field);
			MethodDeclaration setter = hasSetter( field);
			if(getter != null && setter != null) {
				getter.remove();
				setter.remove();
				field.setModifiers(PUBLIC.toEnumSet());
			}
			
			// find out whether this object uses other objects and their cardinalities
			List<ClassOrInterfaceType> obj = field.getVariable(0).getNodesByType(ClassOrInterfaceType.class);
			if(obj.size() == 2){
				setUse(obj.get(1).toString(), "*");
			}else if(obj.size() == 1){
				setUse(obj.get(0).toString(), "");
			}
		}
	}
	
	private void setUse(String type, String count){
		if(count.equals("*") || !use.containsKey(type)){
			use.put(type, count);
		}
	}
	
	public HashMap<String, String> getUse(){
		return use;
	}
	
	private void parseMethods(){
		for (MethodDeclaration method : cu.getTypes().get(0).getMethods() ) {
			if(method.getModifiers().contains(PUBLIC) ){
				for(Parameter parameter : method.getParameters()) useInMethod.add(parameter.getType().toString());
		    }
		}
	}
	
	private HashSet<String> getUseInMethod(){
		return useInMethod;
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
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		String name = cu.getTypes().get(0).getNameAsString();
		ClassOrInterfaceDeclaration myclass = (ClassOrInterfaceDeclaration) cu.getTypes().get(0);

		for(ClassOrInterfaceType declaration : myclass.getExtendedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|-- ");
			result.append(name);
			result.append("\n");
		}

		for(ClassOrInterfaceType declaration : myclass.getImplementedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|.. ");
			result.append(name);
			result.append("\n");
		}
		
		if(myclass.isInterface()){
			result.append("interface ");
		}else{
			result.append("class ");
		}
		result.append(name);
		result.append("\n");
		
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ) {
			if(field.getModifiers().contains(PUBLIC)){
				result.append(name + " : + ");
		    }else if(field.getModifiers().contains(PRIVATE)){
		    	result.append(name + " : - ");
		    }else{
		    	continue;
		    }
			result.append(field.getCommonType() + " " + field.getVariables().get(0) + "\n");
		}
		
		for (MethodDeclaration method : cu.getTypes().get(0).getMethods() ) {
			result.append(name + " : " );
			if(method.getModifiers().contains(PUBLIC)){
				result.append("+ ");
		    }
			result.append(method.getDeclarationAsString(false, false) + "\n");
		}
		return result.toString();
	}
}
