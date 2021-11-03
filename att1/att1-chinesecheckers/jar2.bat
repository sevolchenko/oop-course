@echo off

set CD=%~dp0
set mainPath=ru\vsu\cs\checkers
set binPath=%CD%bin

set utils=%mainPath%\utils\*.class
set graph=%mainPath%\structures\graph\*.class
set pieces=%mainPath%\piece\*.class
set game=%mainPath%\game\*.class
set requests=%mainPath%\requests\*.class
set commandProviders=%mainPath%\commandProviders\*.class
set javafx=%mainPath%\display\javafx\*.class
set console=%mainPath%\display\console\*.class
set Main=%mainPath%\*.class

cd %binPath%
jar cvfe ../ChineseCheckers.jar ru.vsu.cs.checkers.Main %utils% %graph% %pieces% %game% %requests% %commandProviders% %javafx% %console% %Main%