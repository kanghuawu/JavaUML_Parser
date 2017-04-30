### How to run java parser for class diagram

1. Install Java 8

2. Install graphviz (My UML generator is [plantuml](http://plantuml.com/graphviz-dot))

```
# for linux(ubuntu):
sudo apt-get install graphviz

# for mac:
brew install graphviz 
```

3. Run following command

```
java -jar umlparser.jar <source folder> <output file name>

# for example:
java -jar umlparser.jar ./folder/to/source/folder ./folder/to/dest/output.png 
```