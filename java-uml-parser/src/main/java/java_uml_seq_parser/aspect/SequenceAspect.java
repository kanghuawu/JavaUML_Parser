package java_uml_seq_parser.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SequenceAspect {

	/**
	 * SequenceParser.parse()
	 */
	@Before("call(* java_uml_seq_parser.core.SequenceParser.parse(..))")
	public void beforeParse(JoinPoint joinPoint) {
		System.out.println(" - (.java) before parse: " + joinPoint.getSignature().getName());
	}
	
	@After("call(* java_uml_seq_parser.core.SequenceParser.parse(..))")
	public void afterParse(JoinPoint joinPoint) {
		System.out.println(" - (.java) after parse: " + joinPoint.getSignature().getName());
	}
	
	@AfterReturning(
			pointcut="call(* java_uml_seq_parser.core.SequenceParser.parse(..))",
			returning="returnedObject")
	public void afterReturningParse(JoinPoint joinPoint, Object returnedObject) {
		System.out.println(" - (.java) after returning from parse: " + joinPoint.getSignature().getName() + 
				", returnedObject: [" + returnedObject.toString() + "]");
	}
	
	/**
	 * SequenceParser.parseWithArgument()
	 */
	@Before("call(* java_uml_seq_parser.core.SequenceParser.parseWithArgument(..))")
	public void beforeParseWithArgument(JoinPoint joinPoint) {
		System.out.println(" - (.java) before parseWithArgument: " + joinPoint.getSignature().getName() + 
				", argument: [" + Arrays.toString(joinPoint.getArgs()) + "]");
	}
	
	@After("call(* java_uml_seq_parser.core.SequenceParser.parseWithArgument(..))")
	public void afterParseWithArgumen(JoinPoint joinPoint) {
		System.out.println(" - (.java) after parseWithArgument: " + joinPoint.getSignature().getName() + 
		", argument: [" + Arrays.toString(joinPoint.getArgs()) + "]");
	}
	
	@AfterReturning(
			pointcut="call(* java_uml_seq_parser.core.SequenceParser.parseWithArgument(..))",
			returning="returnedObject")
	public void afterReturningParseWithArgumen(JoinPoint joinPoint, Object returnedObject) {
		System.out.println(" - (.java) after returning from parseWithArgument: " + joinPoint.getSignature().getName() + 
				", argument: [" + Arrays.toString(joinPoint.getArgs()) + "]" + 
				", returnedObject: [" + returnedObject.toString() + "]");
	}
}
