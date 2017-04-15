package java_uml_seq_parser;


import java.util.Stack;
import org.aspectj.lang.reflect.CodeSignature;
import net.sourceforge.plantuml.SourceStringReader;
import test_java_uml_seq_parser.SequenceParser;

import java.io.FileOutputStream;
import java.io.OutputStream;


public aspect MyJavaSeqParser {
	
	private StringBuilder re;
	private Stack<String> stack;
	private String pngDir;
	
	pointcut mainMethodArg(String[] arg) : !within(MyJavaSeqParser) && execution(* *.main(..)) && args(arg);
	
	pointcut mainMethod() : !within(MyJavaSeqParser) && execution(* *.main(..)) ;
	
	pointcut allMethode() : !within(MyJavaSeqParser) && execution(* *.*(..)) && !execution(* *.main(..)) ;
	
	pointcut voidMethod() : !within(MyJavaSeqParser) && execution(void *.*(..)) && !execution(* *.main(..));
	
	pointcut nonVoidMethod() : !within(MyJavaSeqParser) && execution(!void *.*(..)) && !execution(* *.main(..));
	
	before() : allMethode() {
		String previous_class = stack.peek();
		String current_class = getClassName(thisJoinPoint.getTarget().toString());
		String message = thisJoinPoint.getSignature().getName();
		re.append(previous_class + " -> " + current_class + " : " + message + "\n");
		re.append("activate " + current_class + "\n");
		stack.push(current_class);
	}
	
	after() returning : nonVoidMethod() {
		stack.pop();
		String previous_class = stack.peek();
		String current_class = getClassName(thisJoinPoint.getTarget().toString());
		re.append(current_class + " --> " + previous_class + "\n");
		re.append("deactivate " + current_class + "\n");
	}
	
	after() : voidMethod() {
		String previous_class = stack.pop();
		String current_class = getClassName(thisJoinPoint.getTarget().toString());
		re.append("deactivate " + current_class + "\n");
		
	}
	
	before(String[] arg) : mainMethodArg(arg) {
		pngDir = arg[0];
		re = new StringBuilder();
		stack = new Stack<String>();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		stack.push(current_class);
		re = new StringBuilder("@startuml\n");
		re.append("participant " + current_class + "\n");
		re.append("activate " + current_class + "\n");
	}
	
	after(String[] arg) : mainMethodArg(arg) {
		String last_class = stack.pop();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		if(!last_class.equals(current_class)) System.out.println("stack error!!!");
		re.append("deactivate " + current_class + "\n");
		re.append("@enduml");
		System.out.println(re.toString());
		
//		String pngDir = "/Users/bondk/Dropbox/SJSU/CMPE202/peronsal_project/cmpe202-personal-project"
//				+ "/java-uml-parser/src/main/resources/uml-sequence-test.png";
		try{
			OutputStream png = new FileOutputStream(pngDir);
			SourceStringReader reader = new SourceStringReader(re.toString());
			String desc = reader.generateImage(png);
			System.out.println(desc);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	before(String[] arg) : mainMethodArg(arg) {
//		System.out.println( thisJoinPoint.getArgs()[0]);
////		if(thisJoinPoint.getArgs().length == 1){
////			dir = (String) thisJoinPoint.getArgs()[0];
////			System.out.println(dir);
////		}
//	}
	
	private String getClassName(String str){
		if(str.indexOf("@") != -1) return str.substring(str.indexOf(".") + 1, str.indexOf("@"));
		else return str.substring(str.indexOf(".") + 1);
	}
}
