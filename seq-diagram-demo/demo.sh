
echo "Prepare for compiling"
TARGET_DIR=./target
rm -rf $TARGET_DIR
mkdir $TARGET_DIR
echo "*************************************"

echo "Set classpath"
CLASSPATH=./lib/plantuml-8057.jar:./lib/aspectj-1.8.10.jar:$CLASSPATH
echo "CLASSPATH: $CLASSPATH"
echo "*************************************"

echo "Compiling"
ajc -1.5 -classpath .:$CLASSPATH -d $TARGET_DIR **/*.java **/*.aj
echo "*************************************"

echo "Generate sequence diagram"
if [ "$1" = "" ] ; then 
	OUTPUT_DIR="${PWD}/../output"
else 
	OUTPUT_DIR=$1
fi
echo "Output Directory: " $OUTPUT_DIR
# rm -rf $OUTPUT_DIR
# mkdir $OUTPUT_DIR

cd $TARGET_DIR

java -classpath .:../$CLASSPATH sequence.Main $OUTPUT_DIR
