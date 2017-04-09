package test_java_uml_seq_parser;

import test_java_uml_seq_parser.SequenceParser;
import test_java_uml_seq_parser.SequenceParserImpl;

public class MyJavaSeqParser {

	public static void main(String[] args) {
		final SequenceParser parser = new SequenceParserImpl();
		System.out.println("Demo1: method without argument");
		parser.parse();
		System.out.println();
		
		System.out.println("Demo2: method with argument");
		parser.parseWithArgument("Feifei-is-cute");
	}
}
