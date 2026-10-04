@echo off
echo =======================================
echo Starting Online Voting System...
echo =======================================

:: Set JAVA_HOME to the downloaded JDK 17
set JAVA_HOME=C:\Users\DELL\Downloads\online-voting-system-using-Java-spring-boot-blockchain-main\online-voting-system-using-Java-spring-boot-blockchain-main\voting\jdk17\jdk-17.0.10+7
set PATH=%JAVA_HOME%\bin;%PATH%

:: Run the application using the downloaded Maven
.\maven\apache-maven-3.8.8\bin\mvn.cmd spring-boot:run

pause
