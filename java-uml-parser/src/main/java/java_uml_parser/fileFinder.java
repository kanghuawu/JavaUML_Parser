package java_uml_parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class fileFinder {

	private String directory;
	private List<String> javaFiles;
	
	public fileFinder(String directory){
		this.directory = directory;
		findJavaFiles();
	}
	
	public List<String> getJavaFiles(){
		List<String> javaDir = new ArrayList<String>();
		for(String fi : this.javaFiles){
			javaDir.add(this.directory + "/" + fi);
		}
		return javaDir;
	}
	
	private void findJavaFiles(){
		List<String> javaFiles = new ArrayList<String>();
		File dir = new File(this.directory);

		for (File file : dir.listFiles()) {
		    if (file.getName().endsWith((".java"))) {
		    	javaFiles.add(file.getName());
		    }
		  }
		this.javaFiles = javaFiles;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Directory: " + this.directory + "\n");
		sb.append("Java files: ");
		
		for(String fi : javaFiles){
			sb.append(fi);
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}
	
}
