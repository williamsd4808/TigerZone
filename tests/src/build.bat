javac -cp "../../lib/junit-4.12.jar;../../server" BoardTest.java
javac -cp ".;../../lib/junit-4.12.jar;../../server" -sourcepath ".;../" TestRunner.java
cd ..
java -cp ".;../lib/junit-4.12.jar;../../server;../lib/hamcrest-core-1.3.jar" src.TestRunner
cd src