@echo off
echo === CRS - Console Mode ===

echo Creating target directory...
if not exist "target\classes" mkdir "target\classes"

echo Compiling Java files...
javac -cp "src/main/java" -d "target/classes" src/main/java/com/crs/main/CRSApplication.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo === DEFAULT LOGIN ACCOUNTS ===
echo Admin: admin / admin123
echo Officer: officer / officer123  
echo Student: student1 / student123 or student2 / student123
echo.
echo Starting CRS Application in Console Mode...
echo.
java -cp "target/classes" com.crs.main.CRSApplication console

pause