package java_uml_seq_parser.core;

public class SequenceParserImpl implements SequenceParser {

	@Override
	public String parse() {
		System.out.println("Do Parse...");
		return "returned-string-from-parse()";
	}

	@Override
	public String parseWithArgument(String s) {
		System.out.println("Do parseWithArgument...");
		return "returned-string-from-parseWithArgument(" + s + ")";
	}

}
