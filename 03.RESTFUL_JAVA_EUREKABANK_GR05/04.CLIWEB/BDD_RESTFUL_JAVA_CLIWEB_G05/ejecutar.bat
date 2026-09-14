@echo off
echo ========================================
echo   EurekaBank - Cliente Web REST
echo   Ejecutando con Tomcat en puerto 8001
echo ========================================
echo.

REM Obtener la ruta del directorio actual
cd /d "%~dp0"

REM Verificar si Maven esta disponible
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven no encontrado!
    echo Por favor instala Maven o agrega Maven al PATH.
    pause
    exit /b 1
)

REM Verificar si existe el archivo pom.xml
if not exist "pom.xml" (
    echo ERROR: No se encuentra el archivo pom.xml
    echo Asegurate de estar en el directorio correcto del proyecto.
    pause
    exit /b 1
)

REM Verificar si existe pom.xml en el proyecto del servidor REST
set SERVER_PATH=..\01.SERVIDOR\WSEurekaBank_Restfull_Java_G5
if exist "%SERVER_PATH%\pom.xml" (
    echo ========================================
    echo Compilando servidor REST...
    echo ========================================
    echo.
    cd /d "%SERVER_PATH%"
    echo Ejecutando: mvn clean install
    call mvn clean install -DskipTests
    if %ERRORLEVEL% NEQ 0 (
        echo.
        echo ERROR: La compilacion del servidor fallo!
        echo Continuando de todas formas...
        echo.
    ) else (
        echo.
        echo Servidor REST compilado exitosamente!
        echo.
    )
    cd /d "%~dp0"
) else (
    echo No se encontro el proyecto del servidor REST en: %SERVER_PATH%
    echo Continuando sin compilar...
    echo.
)

echo ========================================
echo Compilando cliente web...
echo ========================================
echo.

REM Intentar limpiar, pero continuar si falla (puede fallar si Tomcat esta corriendo)
echo Intentando limpiar proyecto...
call mvn clean -DskipTests >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ADVERTENCIA: No se pudo limpiar completamente (Tomcat puede estar corriendo)
    echo Continuando con la compilacion...
    echo.
)

echo Compilando proyecto...
call mvn package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion del cliente web fallo!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Iniciando Tomcat en puerto 8001...
echo ========================================
echo.
echo El cliente web estara disponible en:
echo http://localhost:8001/eurekabank
echo.
echo IMPORTANTE:
echo - Asegurate de que el servidor REST este ejecutandose en:
echo   http://127.0.0.1:8080/WSEurekaBank_Restfull_Java_G5
echo.
echo Presiona Ctrl+C para detener el servidor cuando termines.
echo.

call mvn tomcat7:run

pause

