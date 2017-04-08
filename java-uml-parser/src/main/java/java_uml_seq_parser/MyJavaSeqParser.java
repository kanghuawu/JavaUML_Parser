package java_uml_seq_parser;

import java_uml_seq_parser.core.SequenceParser;
import java_uml_seq_parser.core.SequenceParserImpl;

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
