javac -cp "../../lib/junit-4.12.jar;../../server" BoardTest.java
javac -cp "../../lib/junit-4.12.jar;../../server" EngineTest.java
javac -cp "../../lib/junit-4.12.jar;../../server" BoardUtilitiesTest.java
javac -cp ".;../../lib/junit-4.12.jar;../../server" -sourcepath ".;../" TestRunner.java
cd ..
java -cp ".;../lib/junit-4.12.jar;../server;../lib/hamcrest-core-1.3.jar;../lib/javax.json-1.0.jar" src.TestRunner
cd src