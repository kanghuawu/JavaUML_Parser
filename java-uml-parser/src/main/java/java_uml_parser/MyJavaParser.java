package java_uml_parser;

import static com.github.javaparser.ast.Modifier.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class MyJavaParser {
	

	private static final String TYPE_STRING_ARRAY = "String[]";
	private static final String TYPE_STRING = "String";
	private CompilationUnit cu;
	private HashMap<String, String> use = new HashMap<String, String>();
	private HashSet<String> useInMethod = new HashSet<String>();

	public MyJavaParser(String directory) {

		try (FileInputStream in = new FileInputStream(directory);) {
			this.cu = JavaParser.parse(in);
			if (this.cu != null) {
				findPrivateFieldAndGetterSetter();
				findClassOrInterfacetInField();
				findOtherObjectInMethod();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return cu.getType(0).getNameAsString();
	}
	
	/**
	 * Find field's getter and setter. If both are found, 
	 * 1. remove getter and setter 2. set attribute to public
	 */
	private void findPrivateFieldAndGetterSetter(){
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ){
			
			MethodDeclaration getter = retrieveGetter( field);
			MethodDeclaration setter = retrieveSetter( field);
			if(getter != null && setter != null) {
				getter.remove();
				setter.remove();
				field.setModifiers(PUBLIC.toEnumSet());
			}
		}
	}
	
	private MethodDeclaration retrieveGetter(final FieldDeclaration field){
		for( MethodDeclaration method : cu.getTypes().get(0).getMethods() ){
			List<ReturnStmt> ret = method.getNodesByType(ReturnStmt.class);
			if(ret.size() == 1 && ret.get(0).toString().indexOf(field.getVariable(0).toString()) != -1 && method.getNameAsString().indexOf("get") == 0) {
				return method;
			}
		}
		return null;
	}
	private MethodDeclaration retrieveSetter(final FieldDeclaration field){
		for( MethodDeclaration method : cu.getTypes().get(0).getMethods() ){
			List<ExpressionStmt> expression = method.getNodesByType(ExpressionStmt.class);
			if(expression.size() == 1 && expression.get(0).toString().indexOf(field.getVariable(0).toString()) != -1 && method.getNameAsString().indexOf("set") == 0) {
				return method;
			}
		}
		return null;
	}
	
	/** 
	 * Find other classes or interfaces declared in field and store them into use
	 */
	private void findClassOrInterfacetInField(){
		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ){
			// find out whether this object uses other objects and their cardinalities
			List<ClassOrInterfaceType> obj = field.getVariable(0).getNodesByType(ClassOrInterfaceType.class);
			
			if(obj.size() == 2 && !obj.get(0).toString().equals(TYPE_STRING)){
				field.remove();
				storeObjectCardinality(obj.get(1).toString(), "*");
			}else if(obj.size() == 1 && !obj.get(0).toString().equals(TYPE_STRING)){
				field.remove();
				storeObjectCardinality(obj.get(0).toString(), "");
			}
		}
	}
	
	private void storeObjectCardinality(String type, String count){
		if(count.equals("*") || !use.containsKey(type)){
			use.put(type, count);
		}
	}
	
	public HashMap<String, String> getUse(){
		return use;
	}
	private void findOtherObjectInMethod(){
		// find in constructor
		for (ConstructorDeclaration constructor : cu.getNodesByType(ConstructorDeclaration.class)){
			for(Parameter parameter : constructor.getParameters()) {
				useInMethod.add(parameter.getType().toString());
//				constructor.remove();
//				System.out.println("constructor removed");
			}
			
		}
		// find in method parameters
		for (MethodDeclaration method : cu.getTypes().get(0).getMethods() ) {
			if(method.getModifiers().contains(PUBLIC) ){
				for(Parameter parameter : method.getParameters()) {
					useInMethod.add(parameter.getType().toString());
					method.remove();
					System.out.println("method removed");
				}
		    }
		}
		// find in expression statements
		for(ExpressionStmt expression : cu.getNodesByType(ExpressionStmt.class)) {
			for(ClassOrInterfaceType type : expression.getNodesByType(ClassOrInterfaceType.class)){
				useInMethod.add(type.toString());
				expression.remove();
				System.out.println("expression removed");
			}
		}
		
		useInMethod.remove(TYPE_STRING);
		useInMethod.remove(TYPE_STRING_ARRAY);
//		System.out.println(useInMethod);
	}
	
	public HashSet<String> getUseInMethod(){
		return useInMethod;
	}
	
	public String getParsedResult(){
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
		
		for(String use : useInMethod){
			result.append(this.getName());
			result.append(" ..> ");
			result.append(use);
			result.append("\n");
		}
		
		return result.toString();
	}

	public static String findUseRelation(final List<MyJavaParser> totalObjects){
		StringBuilder relation = new StringBuilder();
	
		int size = totalObjects.size();
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j < size; j ++){
				MyJavaParser objA = totalObjects.get(i);
				MyJavaParser objB = totalObjects.get(j);
				if(!objA.getUse().containsKey(objB.getName()) && !objB.getUse().containsKey(objA.getName())) continue;
				else{
					
					relation.append(objA.getName());
					System.out.println(objB.getUse());
					System.out.println(objA.getUse());
					if(!objB.getUse().isEmpty() && !objB.getUse().get(objA.getName()).isEmpty() ){
						relation.append("\"");
						relation.append(objB.getUse().get(objA.getName()));
						relation.append("\"");
					}
					relation.append(" -- ");
					if(!objA.getUse().isEmpty() && !objA.getUse().get(objB.getName()).isEmpty() ){
						relation.append("\"");
						relation.append(objA.getUse().get(objB.getName()));
						relation.append("\"");
					}
					relation.append(objB.getName());
					relation.append("\n");
				}
	
			}
		}
		return relation.toString();
	}
}
