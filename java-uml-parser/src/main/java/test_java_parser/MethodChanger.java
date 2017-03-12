package test_java_parser;
import java.io.File;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodChanger {

    public static void main(String[] args) throws Exception {
        // parse a file
        CompilationUnit cu = JavaParser.parse(new File("/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4/ConcreteObserver.java"));

        // visit and change the methods names and parameters
        new MethodChangerVisitor().visit(cu, null);

        // prints the changed compilation unit
        System.out.println(cu);
    }

    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodChangerVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            // change the name of the method to upper case
            n.setName(n.getNameAsString().toUpperCase());

            // add a new parameter to the method
            n.addParameter("int", "value");
        }
    }
}

//public class MethodChanger {
//
//    public static void main(String[] args) throws Exception {
//        // creates an input stream for the file to be parsed
//        FileInputStream in = new FileInputStream("/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4/ConcreteObserver.java");
//
//        // parse the file
//        CompilationUnit cu = JavaParser.parse(in);
//
//        // change the methods names and parameters
//        changeMethods(cu);
//
//        // prints the changed compilation unit
//        System.out.println(cu.toString());
//    }
//
//    private static void changeMethods(CompilationUnit cu) {
//        // Go through all the types in the file
//        NodeList<TypeDeclaration<?>> types = cu.getTypes();
//        for (TypeDeclaration<?> type : types) {
//            // Go through all fields, methods, etc. in this type
//            NodeList<BodyDeclaration<?>> members = type.getMembers();
//            for (BodyDeclaration<?> member : members) {
//                if (member instanceof MethodDeclaration) {
//                    MethodDeclaration method = (MethodDeclaration) member;
//                    changeMethod(method);
//                }
//            }
//        }
//    }
//
//    private static void changeMethod(MethodDeclaration n) {
//        // change the name of the method to upper case
//        n.setName(n.getNameAsString().toUpperCase());
//
//        // create the new parameter
//        n.addParameter("int", "value");
//    }
//}