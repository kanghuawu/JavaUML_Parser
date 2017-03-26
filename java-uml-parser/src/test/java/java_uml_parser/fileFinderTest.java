package java_uml_parser;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import static org.hamcrest.MatcherAssert.assertThat;

public class fileFinderTest {

	@Test
	public void test() {
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-1";
		JavaFileFinder ff = new JavaFileFinder(dir);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Directory: ");
		sb.append(dir + "\n");
		sb.append("Java files: ");
		sb.append("A.java, B.java, C.java, D.java");
		assertEquals(sb.toString(), ff.toString());
	}
	
	@Test
	public void test1() {
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-2";
		JavaFileFinder ff = new JavaFileFinder(dir);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Directory: ");
		sb.append(dir + "\n");
		sb.append("Java files: ");
		sb.append("A1.java, A2.java, B1.java, B2.java, C1.java, C2.java, P.java");
		assertEquals(sb.toString(), ff.toString());
	}
	
	@Test
	public void test2() {
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-1";
		JavaFileFinder ff = new JavaFileFinder(dir);
		
		List<String> arr = new ArrayList<String>();
		arr.add(dir + "/A.java");
		arr.add(dir + "/B.java");
		arr.add(dir + "/C.java");
		arr.add(dir + "/D.java");
		assertThat(arr, is(ff.getJavaFiles()));
	}
	
	@Test
	public void test3() {
		String dir = "/Users/bondk/Dropbox/SJSU/CMPE202/00_peronsal_project"
				+ "/cmpe202-java-uml-parser/java-uml-parser/src/main/resources/uml-parser-test-4";
		JavaFileFinder ff = new JavaFileFinder(dir);
		
		List<String> arr = new ArrayList<String>();
		arr.add(dir + "/ConcreteObserver.java");
		arr.add(dir + "/ConcreteSubject.java");
		arr.add(dir + "/Observer.java");
		arr.add(dir + "/Optimist.java");
		arr.add(dir + "/Pessimist.java");
		arr.add(dir + "/Subject.java");
		arr.add(dir + "/TheEconomy.java");
		assertThat(arr, is(ff.getJavaFiles()));
	}
}
