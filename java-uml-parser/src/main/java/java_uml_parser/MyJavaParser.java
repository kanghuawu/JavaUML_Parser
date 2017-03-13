package java_uml_parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static com.github.javaparser.ast.Modifier.*;

import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

public class MyJavaParser {
	

	private String javaDir;
	private String className;
	private FileInputStream in = null;
	private CompilationUnit cu;
	private StringBuilder result;
	
	public MyJavaParser(String directory) {
		this.javaDir = directory;
		
		try{
			this.in = new FileInputStream(directory);
			this.cu = JavaParser.parse(this.in);
			if (this.in != null) {
				this.in.close();
		    }
		}catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		result = new StringBuilder();
		
		
		parseClassType();
		parseAttributes();
		parseMethods();
	}
	
	public StringBuilder getParsedFile(){
		return result;
	}
	
	private void parseClassType(){
		
		this.className = cu.getTypes().get(0).getNameAsString();
		result.append("class ");
		result.append(this.className);
		result.append("\n");
	}
	
	private void parseAttributes(){

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
	}
	
	private void parseMethods(){
		
		for (MethodDeclaration method : cu.getTypes().get(0).getMethods() ) {
			result.append(this.className + " : " );
			if(method.getModifiers().contains(PUBLIC)){
				result.append("+ ");
		    }
			result.append(method.getDeclarationAsString(false, false) + "\n");
		}
	}
	
}
