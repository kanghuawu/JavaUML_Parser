package java_uml_seq_parser;


import java.util.Stack;
import org.aspectj.lang.reflect.CodeSignature;
import net.sourceforge.plantuml.SourceStringReader;
import java.io.FileOutputStream;
import java.io.OutputStream;


public aspect MyJavaSeqParser {
	
	StringBuilder re;
	Stack<String> stack;
	
	pointcut mainparser() : !within(MyJavaSeqParser) && execution(* *.main(..)) ;
	
	pointcut parse() : !within(MyJavaSeqParser) && execution(* *.*(..)) && !execution(* *.main(..)) ;
	
	before() : parse() {
		String previous_class = stack.peek();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		String message = thisJoinPoint.getSignature().getName();
		re.append(previous_class + " -> " + current_class + " : " + message + "\n");
		re.append("activate " + current_class + "\n");
		stack.push(current_class);
	}
	
	after() : parse() {
		String previous_class = stack.pop();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		
		re.append("deactivate " + current_class + "\n");
		
	}
	
	after() returning : parse() {
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		re.append("deactivate " + current_class + "\n");
	}
	
	before(): mainparser() {
		re = new StringBuilder();
		stack = new Stack<String>();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		stack.push(current_class);
		re = new StringBuilder("@startuml\n");
		re.append("participant " + current_class + "\n");
		re.append("activate " + current_class + "\n");
	}
	
	after(): mainparser() {
		String last_class = stack.pop();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		if(!last_class.equals(current_class)) System.out.println("stack error!!!");
		re.append("deactivate " + current_class + "\n");
		re.append("@enduml");
		System.out.println(re.toString());
		String pngDir = "/Users/bondk/Dropbox/SJSU/CMPE202/peronsal_project/cmpe202-personal-project"
				+ "/java-uml-parser/src/main/resources/uml-sequence-test.png";
		try{
			OutputStream png = new FileOutputStream(pngDir);
			SourceStringReader reader = new SourceStringReader(re.toString());
			String desc = reader.generateImage(png);
			System.out.println(desc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getClassName(String str){
		return str.substring(str.indexOf(".") + 1);
	}
}
