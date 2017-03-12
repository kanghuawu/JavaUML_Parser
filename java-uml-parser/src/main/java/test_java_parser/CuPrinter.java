package test_java_parser;

import java.io.FileInputStream;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class CuPrinter {

    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream("/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4/ConcreteObserver.java");

        // parse the file
        CompilationUnit cu = JavaParser.parse(in);

        // prints the resulting compilation unit to default system output
        System.out.println(cu.toString());
    }
}

