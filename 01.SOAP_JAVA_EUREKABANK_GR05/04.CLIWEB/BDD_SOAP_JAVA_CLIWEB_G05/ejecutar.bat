@echo off
chcp 65001 >nul
echo ========================================
echo   EurekaBank - Cliente Web SOAP
echo   Ejecutando en Tomcat Puerto 8000
echo ========================================
echo.

cd /d "%~dp0"

echo Compilando proyecto...
call mvn clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo
    pause
    exit /b 1
)

echo.
echo ========================================
echo Iniciando Tomcat en puerto 8000...
echo ========================================
echo.
echo La aplicacion estara disponible en:
echo http://localhost:8000/eurekabank
echo.
echo Presiona Ctrl+C para detener el servidor
echo.

call mvn tomcat7:run

pause

