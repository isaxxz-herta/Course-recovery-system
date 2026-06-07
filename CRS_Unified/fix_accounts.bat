@echo off
echo === CRS Account Fix Utility ===
echo This will delete corrupted data and create fresh active accounts

echo Deleting old data file...
if exist "crs_data.bin" del "crs_data.bin"

echo Compiling latest code...
javac -cp "src/main/java" -d "target/classes" src/main/java/com/crs/main/CRSApplication.java

if %ERRORLEVEL% NEQ 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo === FIXED ACCOUNTS ===
echo All accounts are now ACTIVE and ready to use:
echo Administrator: admin / admin123
echo Academic Officer: officer / officer123  
echo Student 1: student1 / student123
echo Student 2: student2 / student123
echo.
echo Starting application with fresh active accounts...
echo.
java -cp "target/classes" com.crs.main.CRSApplication

pause