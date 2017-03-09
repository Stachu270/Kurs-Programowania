all: Test.class main.exe

main.exe: main.cpp
	g++ -o main.exe main.cpp

Test.class: Test.java
	javac Test.java

clean:
	rm *.class *.exe