@echo off
echo === Compiling and Running CRS Unified System ===

echo Compiling with Maven...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running CRS Application...
call mvn exec:java

pause