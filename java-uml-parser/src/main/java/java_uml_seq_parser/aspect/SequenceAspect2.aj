package java_uml_seq_parser.aspect;

import java_uml_seq_parser.core.SequenceParser;

public aspect SequenceAspect2 {


	/**
	 * SequenceParser.parse()
	 */
	pointcut callParse() : call(* java_uml_seq_parser.core.SequenceParser.parse());
 
    before() : callParse() {
        System.out.println("- (.aj) before parse");
    }
 
    after() : callParse()  {
        System.out.println("- (.aj) after parse");
    }
    
    after() returning (Object returnObject) : callParse() {
    	System.out.println("- (.aj) after parse return: [" + returnObject.toString() + "]");
    }
    
    /**
	 * SequenceParser.parseWithArgument()
	 */
	pointcut callParseWithArgument(SequenceParser caller, String argumentStr) : 
		call(* java_uml_seq_parser.core.SequenceParser.parseWithArgument(String))
		&& args(argumentStr) && target(caller);
	 
    before(SequenceParser caller, String argumentStr) : 
    	callParseWithArgument(caller, argumentStr) {
        System.out.println("- (.aj) before parseWithArgument. "
        		+ "Caller[" + caller.toString() + "], argument: [" + argumentStr + "]");
    }
 
    after(SequenceParser caller, String argumentStr) : 
    	callParseWithArgument(caller, argumentStr) {
        System.out.println("- (.aj) after parseWithArgument. "
        		+ "Caller[" + caller.toString() + "], argument: [" + argumentStr + "]");
    }
    
    after(SequenceParser caller, String argumentStr) returning (Object returnObject) : 
    	callParseWithArgument(caller, argumentStr) {
    	System.out.println("- (.aj) after parseWithArgument. "
    			+ "Caller[" + caller.toString() + "], argument: [" + argumentStr + "], "
    			+ "return: [" + returnObject.toString() + "]");
    }
     
}
