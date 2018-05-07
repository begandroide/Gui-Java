JFLAGS = -g
JC = javac
JVM = java
CP = -cp

default:
	$(JC) $(CP) interface.jar ./*.java
run:
	$(JVM) $(CP) interface.jar: Main

clean:
	rm *.class
