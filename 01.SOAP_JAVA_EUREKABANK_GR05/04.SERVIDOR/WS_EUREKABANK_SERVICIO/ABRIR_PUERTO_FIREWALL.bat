@echo off
echo ==========================================
echo Abriendo puerto 8080 en el Firewall de Windows
echo ==========================================
echo.

REM Agregar regla de firewall para permitir el puerto 8080 TCP
netsh advfirewall firewall add rule name="EurekaBank SOAP Service" dir=in action=allow protocol=TCP localport=8080

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Puerto 8080 abierto exitosamente en el firewall
    echo ✓ El servidor ahora deberia ser accesible desde la red
    echo.
) else (
    echo.
    echo ✗ Error al abrir el puerto. Ejecuta como Administrador.
    echo.
)

pause



