@echo off
echo Expectations for running batch script are:
echo 	- mvn executable is on available on PATH.
echo 	- Batch script is being run from same directory as pom.xml to build app.

echo.

mvn exec:java -Dexec.mainClass="com.test.pubnub_tokengen.TokenGenerator"