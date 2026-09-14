@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
cd /d "%SCRIPT_DIR%"

echo ===============================================================
echo   Cliente Consola REST - EurekaBank (Jonathan García, Richard Gualotuña, José Proaño)
echo ===============================================================

call mvn compile exec:java

if errorlevel 1 (
    echo.
    echo [ERROR] Ocurrio un problema al ejecutar el cliente.
) else (
    echo.
    echo Cliente finalizado correctamente.
)

echo.
pause
endlocal

