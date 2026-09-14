@echo off
echo ========================================
echo   Recompilar y Redesplegar Servidor REST
echo ========================================
echo.

cd /d "%~dp0"

REM Verificar si Maven esta disponible
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven no encontrado!
    echo Por favor instala Maven o agrega Maven al PATH.
    pause
    exit /b 1
)

echo Paso 1: Limpiando proyecto...
call mvn clean

echo.
echo Paso 2: Compilando proyecto...
call mvn install -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo!
    pause
    exit /b 1
)

echo.
echo ========================================
echo Compilacion exitosa!
echo ========================================
echo.
echo IMPORTANTE: Ahora debes redesplegar el WAR en Payara:
echo.
echo 1. Abre la consola de administracion de Payara:
echo    http://localhost:4848
echo.
echo 2. Ve a Applications
echo.
echo 3. Busca "WSEurekaBank_Restfull_Java_G5"
echo.
echo 4. Haz clic en "Undeploy" para desplegar la version anterior
echo.
echo 5. Luego haz clic en "Deploy" y selecciona el archivo:
echo    target\WSEurekaBank_Restfull_Java_G5-1.0-SNAPSHOT.war
echo.
echo O usa el comando asadmin:
echo    asadmin undeploy WSEurekaBank_Restfull_Java_G5
echo    asadmin deploy target\WSEurekaBank_Restfull_Java_G5-1.0-SNAPSHOT.war
echo.
echo Presiona cualquier tecla para cerrar...
pause >nul



