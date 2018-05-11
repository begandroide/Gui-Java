JFLAGS = -g
JC = javac
JVM = java
CP = -cp

default:
	$(JC) $(CP) interfaz.jar ./*.java
run:
	$(JVM) $(CP) interfaz.jar: Main

clean:
	rm *.class
