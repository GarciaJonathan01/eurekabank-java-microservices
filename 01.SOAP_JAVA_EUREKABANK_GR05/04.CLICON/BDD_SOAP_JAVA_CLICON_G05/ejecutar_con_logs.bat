@echo off
set MAVEN_OPTS=--add-opens=java.base/sun.misc=ALL-UNNAMED
chcp 65001 >nul
title EurekaBank - Cliente Consola SOAP (Modo Detallado)
color 0B

echo.
echo ╔═══════════════════════════════════════════════════════════════╗
echo ║                                                               ║
echo ║     EUREKABANK - CLIENTE CONSOLA SOAP (MODO DETALLADO)     ║
echo ║                                                               ║
echo ║              Grupo Monster G09 - Sistema Bancario            ║
echo ║                                                               ║
echo ╚═══════════════════════════════════════════════════════════════╝
echo.

REM Activar soporte para colores ANSI en Windows 10+
reg add HKCU\Console /v VirtualTerminalLevel /t REG_DWORD /d 1 /f >nul 2>&1

REM Verificar si Maven está instalado
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven no esta instalado o no esta en el PATH
    echo.
    echo Por favor, instala Maven o agrega Maven al PATH del sistema
    echo.
    echo Puedes descargar Maven desde: https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo [INFO] Verificando configuracion del proyecto...
echo.

REM Verificar que existe el pom.xml
if not exist "pom.xml" (
    echo [ERROR] No se encontro el archivo pom.xml
    echo.
    echo Asegúrate de ejecutar este script desde la raíz del proyecto
    echo.
    pause
    exit /b 1
)

echo [OK] Archivo pom.xml encontrado
echo.

echo [INFO] Compilando el proyecto...
echo ═══════════════════════════════════════════════════════════════
echo.

REM Compilar el proyecto (con salida visible)
call mvn clean compile
if %errorlevel% neq 0 (
    echo.
    echo ═══════════════════════════════════════════════════════════════
    echo [ERROR] Error al compilar el proyecto
    echo.
    echo Revisa los errores de compilación arriba
    echo.
    pause
    exit /b 1
)

echo.
echo ═══════════════════════════════════════════════════════════════
echo [OK] Compilacion exitosa
echo.
echo [INFO] Iniciando aplicacion EurekaBank...
echo.
echo ═══════════════════════════════════════════════════════════════
echo.

REM Ejecutar la aplicación
call mvn exec:exec

REM Verificar el código de salida
if %errorlevel% neq 0 (
    echo.
    echo ═══════════════════════════════════════════════════════════════
    echo [ERROR] La aplicacion termino con errores
    echo.
    echo Código de error: %errorlevel%
    echo.
    pause
    exit /b 1
)

echo.
echo ═══════════════════════════════════════════════════════════════
echo [OK] Aplicacion finalizada correctamente
echo.
echo Gracias por usar EurekaBank - Grupo Monster G09
echo.
pause

