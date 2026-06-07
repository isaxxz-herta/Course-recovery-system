@echo off
echo === CRS Fresh Start - Clean Installation ===

echo Cleaning old data and compiled files...
if exist "crs_data.bin" del "crs_data.bin"
if exist "target" rmdir /s /q "target"

echo Creating target directory...
mkdir "target\classes"

echo Compiling Java files...
javac -cp "src/main/java" -d "target/classes" src/main/java/com/crs/main/CRSApplication.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo === FRESH DEFAULT ACCOUNTS ===
echo Administrator: admin / admin123
echo Academic Officer: officer / officer123  
echo Student 1: student1 / student123
echo Student 2: student2 / student123
echo.
echo === NEW FEATURES ===
echo - Quick Login buttons in GUI
echo - Return to Login option in all interfaces
echo - Switch between users without restarting
echo.
echo Starting CRS Application in GUI Mode...
echo (GUI will open automatically - no need to choose interface)
echo.
java -cp "target/classes" com.crs.main.CRSApplication

pause