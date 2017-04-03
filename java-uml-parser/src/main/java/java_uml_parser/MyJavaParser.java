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
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.body.ConstructorDeclaration;

public class MyJavaParser {
	
	
	private CompilationUnit cu;
	private HashMap<String, String> associations = new HashMap<String, String>();
	private HashSet<String> depedencies = new HashSet<String>();
	private static final String TYPE_STRING_ARRAY = "String[]";
	private static final String TYPE_STRING = "String";
	
	public MyJavaParser(String directory) {
		
		try(FileInputStream in = new FileInputStream(directory)){
			this.cu = JavaParser.parse(in);
			if(this.cu != null){
				findPrivateFieldAndGetterSetter();
				findAssociations();
				findDepedencies();
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public String getName(){
		return cu.getType(0).getNameAsString();
	}
	
	public boolean isInterface(){
		if(((ClassOrInterfaceDeclaration) cu.getType(0)).isInterface()){
			return true;
		}else{
			return false;
		}
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
	
	/* *
	 * Find other classes or interfaces declared in field and store them into use
	 */
	private void findAssociations(){
		for (FieldDeclaration field : cu.getType(0).getFields() ){
			// find out whether this object uses other objects and their cardinalities
			List<ClassOrInterfaceType> type = field.getVariable(0).getNodesByType(ClassOrInterfaceType.class);
			if(type.isEmpty() || type.get(0).toString().equals(TYPE_STRING)){
				continue;
			}else if(type.size() >= 2){
				storeMultiplicities(type.get(1).toString(), "*");
				field.remove();
			}else if(type.size() == 1){
				storeMultiplicities(type.get(0).toString(), "");
				field.remove();
			}
		}
	}
	
	private void storeMultiplicities(String type, String count){
		if(count.equals("*") || !associations.containsKey(type)){
			associations.put(type, count);
		}
	}
	
	public HashMap<String, String> getAssociations(){
		return associations;
	}
	private void findDepedencies(){
		if(!((ClassOrInterfaceDeclaration) cu.getType(0)).isInterface()){
		
			// find in constructors' parameters
			for (ConstructorDeclaration constructor : cu.getNodesByType(ConstructorDeclaration.class)){
				for(Parameter parameter : constructor.getParameters()) {
					if(isStringType(parameter.getType())){
						continue;
					}
					depedencies.add(parameter.getType().toString());
				}
				
			}
			
			// find in methods' parameters
			for (MethodDeclaration method : cu.getType(0).getMethods() ) {
				if(method.getModifiers().contains(PUBLIC) ){
					for(Parameter parameter : method.getParameters()) {
						if(isStringType(parameter.getType())){
							continue;
						}
						depedencies.add(parameter.getType().toString());
						method.remove();;
					}
			    }
			}
			// find in expression statements
			for(VariableDeclarationExpr varDec : cu.getNodesByType(VariableDeclarationExpr.class)) {
				if(isStringType(varDec.getCommonType())){
					continue;
				}
				depedencies.add(varDec.getCommonType().toString());
			}
			
//			depedencies.remove(TYPE_STRING);
//			depedencies.remove(TYPE_STRING_ARRAY);
			
		}
	}
	
	private boolean isStringType(Type type){
		if(type.toString().equals(TYPE_STRING) || type.toString().equals(TYPE_STRING_ARRAY)){
			return true;
		}else{
			return false;
		}
	}
	
	public HashSet<String> getDepedencies(){
		return depedencies;
	}
	
	public String getParsedResult(){
		StringBuilder result = new StringBuilder();
		ClassOrInterfaceDeclaration type = (ClassOrInterfaceDeclaration) cu.getType(0);
		String type_name = type.getNameAsString();

		for(ClassOrInterfaceType declaration : type.getExtendedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|-- ");
			result.append(type_name);
			result.append("\n");
		}

		for(ClassOrInterfaceType declaration : type.getImplementedTypes()) {
			result.append(declaration.getNameAsString());
			result.append(" <|.. ");
			result.append(type_name);
			result.append("\n");
		}
		
		if(type.isInterface()){
			result.append("interface ");
		}else{
			result.append("class ");
		}
		result.append(type_name);
		result.append("\n");

		for (FieldDeclaration field : cu.getTypes().get(0).getFields() ) {
			if(field.getModifiers().contains(PUBLIC)){
				result.append(type_name + " : + ");
		    }else if(field.getModifiers().contains(PRIVATE)){
		    	result.append(type_name + " : - ");
		    }else{
		    	continue;
		    }
			
			if(field.getNodesByType(ObjectCreationExpr.class).size() != 0){
				field.getNodesByType(ObjectCreationExpr.class).remove(0);
			}
			
			result.append(field.getVariable(0).getName() + " : " + field.getCommonType() + "\n");
		}
		
		for(ConstructorDeclaration constructor : cu.getType(0).getNodesByType(ConstructorDeclaration.class)){
			result.append(type_name + " : + " + constructor.getName() + "(");
			result.append(retrieveParameterAndType(constructor));
			result.append(")\n");
		}
		
		for (MethodDeclaration method : cu.getType(0).getMethods() ) {
			if(method.getModifiers().contains(PUBLIC)){
				result.append(type_name + " : " );
				result.append("+ ");
				result.append(method.getName() + "(");
				result.append(retrieveParameterAndType(method));
				result.append(") : " + method.getType() + "\n");
		    }
		}
		
		return result.toString();
	}
	
	private String retrieveParameterAndType(BodyDeclaration type){
		StringBuilder result = new StringBuilder();
		for(Parameter par : type.getNodesByType(Parameter.class)){
			result.append(par.getName());
			result.append(" : ");
			result.append(par.getType());
		}
		return result.toString();
	}
	
	public static String getParsedAssiciations(final List<MyJavaParser> totalObjects){
		StringBuilder relation = new StringBuilder();
		int size = totalObjects.size();
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j < size; j ++){
				MyJavaParser objA = totalObjects.get(i);
				MyJavaParser objB = totalObjects.get(j);
				if(!objA.getAssociations().containsKey(objB.getName()) && !objB.getAssociations().containsKey(objA.getName())){
					continue;
				}
				else{
					relation.append(objA.getName());
					relation.append(multiplicityHelper(objB.getAssociations(), objA.getName()));
					relation.append(" -- ");
					relation.append(multiplicityHelper(objA.getAssociations(), objB.getName()));
					relation.append(objB.getName());
					relation.append("\n");
				}
			}
		}
		return relation.toString();
	}
	private static String multiplicityHelper(HashMap<String, String> use, String name){
		if(use.get(name) == "*"){
			return "\"" + use.get(name) + "\" ";
		}
		return "";
	}
	
	public static String getParsedDepedencies(List<MyJavaParser> totalObjects, HashSet<String> interfaces){
		StringBuilder result = new StringBuilder();
		for(MyJavaParser object : totalObjects){
			for(String dependency : object.getDepedencies()){
				if(interfaces.contains(dependency)){
					result.append(object.getName());
					result.append(" ..> ");
					result.append(dependency);
					result.append("\n");
				}
			}
		}
		return result.toString();
	}
}
