all: clean

clean: 
	find . -name "*.class" -exec rm -rf {} \;

java:
	ajc -1.5 *.java
	
aspects:
	ajc -1.5 *.java *.aj

run:
	java Main