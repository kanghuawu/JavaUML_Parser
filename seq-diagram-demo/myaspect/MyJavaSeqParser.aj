package myaspect;
//package java_uml_seq_parser;
import java.io.File;
import java.util.Stack;
import org.aspectj.lang.reflect.CodeSignature;
import net.sourceforge.plantuml.SourceStringReader;


import java.io.FileOutputStream;
import java.io.OutputStream;


public aspect MyJavaSeqParser {
	
	private StringBuilder re;
	private Stack<String> stack;
	private String pngDir;
	
	pointcut mainMethodArg(String[] args) : !within(MyJavaSeqParser) && execution(* *.main(..)) && args(args);
	
	pointcut mainMethod() :  !within(MyJavaSeqParser) && execution(* *.main(..)) ;
	
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
	
	before(String[] args) : mainMethodArg(args) {
		System.out.println(args[0]);
		if (args.length == 1 && new File(args[0]).exists()){
			pngDir = args[0] + "/output.png";
		}else{
			pngDir = "/Users/bondk/Downloads/output.png";
		}
		System.out.println(pngDir);

		re = new StringBuilder();
		stack = new Stack<String>();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		stack.push(current_class);
		re = new StringBuilder("@startuml\n");
		re.append("participant " + current_class + "\n");
		re.append("activate " + current_class + "\n");
	}
	
	after(String[] args) : mainMethodArg(args) {
		String last_class = stack.pop();
		String current_class = getClassName(thisJoinPoint.getSignature().getDeclaringTypeName());
		if(!last_class.equals(current_class)) System.out.println("stack error!!!");
		re.append("deactivate " + current_class + "\n");
		re.append("@enduml");
		//System.out.println(re.toString());
		
		//String pngDir = "/Users/bondk/Downloads/seq-diagram-demo-package/output/uml-sequence-v1.png";
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
//		System.out.println(str);
		if(str.indexOf("@") != -1) return str.substring(str.indexOf(".") + 1, str.indexOf("@"));
		else return str.substring(str.indexOf(".") + 1);
	}
}
