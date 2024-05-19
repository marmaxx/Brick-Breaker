#!/bin/bash
echo "Compiling Java files..."
javac -d bin -sourcepath src src/*.java
echo "Running the program..."
java -cp bin Main
