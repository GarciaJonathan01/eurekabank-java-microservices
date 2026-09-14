@echo off
set MAVEN_OPTS=--add-opens=java.base/sun.misc=ALL-UNNAMED
chcp 65001 >nul
title EurekaBank - Cliente Consola SOAP
color 0B

echo.
echo ╔═══════════════════════════════════════════════════════════════╗
echo ║                                                               ║
echo ║          EUREKABANK - CLIENTE CONSOLA SOAP                    ║
echo ║                                                               ║
echo ║              Grupo Monster G09 - Sistema Bancario             ║
echo ║                                                               ║
echo ╚═══════════════════════════════════════════════════════════════╝
echo.

REM Activar soporte para colores ANSI en Windows 10+
reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1 /f >nul 2>&1

REM Verificar si Maven está instalado
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven no está instalado o no está en el PATH
    echo.
    echo Por favor, instala Maven o agrega Maven al PATH del sistema
    echo.
    pause
    exit /b 1
)

echo [INFO] Compilando el proyecto...
echo.

REM Compilar el proyecto
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Error al compilar el proyecto
    echo.
    pause
    exit /b 1
)

echo [INFO] Compilacion exitosa [OK]
echo.
echo [INFO] Iniciando aplicación...
echo.
echo ═══════════════════════════════════════════════════════════════
echo.

REM Ejecutar la aplicación
call mvn exec:exec -q

REM Verificar el código de salida
if %errorlevel% neq 0 (
    echo.
    echo ═══════════════════════════════════════════════════════════════
    echo [ERROR] La aplicación terminó con errores
    echo.
    pause
    exit /b 1
)

echo.
echo ═══════════════════════════════════════════════════════════════
echo [INFO] Aplicación finalizada correctamente
echo.
pause

