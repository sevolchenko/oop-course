@echo off

set CD=%~dp0
set javafxHome=%CD%lib\javafx-sdk-17.0.0.1\lib
set mainPath=%CD%src\ru\vsu\cs\checkers
set logPath=%CD%lib\logback

set utils=-d bin %mainPath%\utils\*.java
set graph=-d bin %mainPath%\structures\graph\*.java
set pieces=-d bin %mainPath%\piece\*.java
set service=-d bin %mainPath%\service\*.java
set game=-d bin %mainPath%\game\*.java
set requests=-d bin %mainPath%\requests\*.java
set commandProviders=-d bin %mainPath%\commandProviders\*.java
set javafx=-d bin %mainPath%\display\javafx\*.java
set console=-d bin %mainPath%\display\console\*.java
set Main=-d bin %mainPath%\*.java

javac --module-path "%javafxHome%" --upgrade-module-path "%logPath%" --add-modules ALL-MODULE-PATH %utils% %graph% %pieces% %service% %game% %requests% %commandProviders% %javafx% %console% %Main%