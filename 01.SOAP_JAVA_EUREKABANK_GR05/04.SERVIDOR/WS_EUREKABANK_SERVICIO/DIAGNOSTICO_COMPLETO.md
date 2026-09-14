# Diagnóstico Completo del Problema de Conexión

## Error: EHOSTUNREACH (No route to host)

Este error significa que el dispositivo Android no puede encontrar una ruta de red hacia el servidor.

## Checklist de Verificación

### 1. ¿El servidor está corriendo?

**En tu PC (donde corre el servidor):**
- Debe estar ejecutándose `EurekaServicePublisher.java`
- Debe mostrar: "✓ Servicio publicado exitosamente"
- **Si no está corriendo, ejecútalo primero**

### 2. Verificar desde tu PC

Abre en el navegador de tu PC:
```
http://localhost:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

**Si NO carga:**
- El servidor no está corriendo
- Ejecuta `EurekaServicePublisher.java`

**Si SÍ carga:**
- El servidor está funcionando localmente
- Continúa con el siguiente paso

### 3. Verificar desde la IP de red

Abre en el navegador de tu PC:
```
http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

**Si NO carga:**
- El servidor solo escucha en localhost
- Necesitas verificar la configuración del servidor

**Si SÍ carga:**
- El servidor es accesible desde la red
- El problema puede ser el dispositivo Android o la red

### 4. Verificar desde el dispositivo Android

**En el navegador del dispositivo Android**, abre:
```
http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

**Si NO carga:**
- Problema de red entre el dispositivo y el servidor
- Verifica que ambos estén en la misma red WiFi
- Verifica el firewall (aunque ya lo abriste)

**Si SÍ carga:**
- El servidor es accesible
- El problema está en la app Android
- Verifica la configuración de la app

### 5. Verificar la red

**En tu PC, ejecuta:**
```cmd
ipconfig
```

Verifica que la IP sea realmente `127.0.0.1`

**En el dispositivo Android:**
- Configuración → WiFi → (Tu red) → Ver detalles
- Verifica que la IP del dispositivo sea `192.168.100.x` (misma red)

### 6. Verificar el firewall (otra vez)

Aunque ya abriste el puerto, verifica:

1. Abre "Firewall de Windows con seguridad avanzada" (`wf.msc`)
2. Reglas de entrada → Busca "EurekaBank SOAP Service"
3. Verifica que esté **Habilitada**
4. Doble click → Pestaña "Avanzado"
5. Verifica que "Perfiles" incluya tu perfil actual (Privada/Pública)

### 7. Probar con ping

**En el dispositivo Android**, si tienes acceso a terminal/adb:
```bash
ping 127.0.0.1
```

**O desde tu PC hacia el dispositivo:**
```cmd
ping 127.0.0.1
```

Si el ping funciona, la red está bien. Si no, hay un problema de red.

## Soluciones Alternativas

### Solución 1: Usar la IP del dispositivo Android

Si el problema persiste, prueba cambiar la IP en la app Android a la IP real de tu PC (verifica con `ipconfig`).

### Solución 2: Verificar que el servidor esté escuchando

Ejecuta en tu PC:
```cmd
netstat -an | findstr :8080
```

Debe mostrar:
```
TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING
```

Si muestra `127.0.0.1:8080`, el servidor solo escucha en localhost.

### Solución 3: Desactivar temporalmente el firewall

**Solo para pruebas:**
1. Panel de Control → Firewall de Windows
2. Activar o desactivar Firewall de Windows
3. Desactivar temporalmente para redes privadas
4. Prueba la app Android
5. **IMPORTANTE:** Vuelve a activarlo después

### Solución 4: Verificar antivirus

Algunos antivirus tienen firewall propio que puede bloquear conexiones. Verifica la configuración de tu antivirus.

## Información Necesaria

Para diagnosticar mejor, necesito saber:

1. ¿El servidor está corriendo? (¿Ves el mensaje de éxito?)
2. ¿Puedes acceder desde el navegador de tu PC a `http://localhost:8080/...`?
3. ¿Puedes acceder desde el navegador de tu PC a `http://127.0.0.1:8080/...`?
4. ¿Puedes acceder desde el navegador del dispositivo Android a `http://127.0.0.1:8080/...`?
5. ¿Ambos dispositivos están en la misma red WiFi?



