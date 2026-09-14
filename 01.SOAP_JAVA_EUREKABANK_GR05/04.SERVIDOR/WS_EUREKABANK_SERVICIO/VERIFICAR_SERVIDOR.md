# Verificación del Servidor SOAP

## Problema: EHOSTUNREACH (No route to host)

Este error significa que el dispositivo Android no puede alcanzar el servidor en `127.0.0.1:8080`.

## Soluciones

### Opción 1: Verificar que el servidor esté corriendo

1. **Si usas el EurekaServicePublisher (standalone):**
   - Ejecuta la clase `EurekaServicePublisher.java`
   - Debe mostrar: "✓ Servicio publicado exitosamente en 127.0.0.1"
   - Verifica que la IP de tu PC sea realmente `127.0.0.1`

2. **Si usas un servidor de aplicaciones (Tomcat, GlassFish, etc.):**
   - Asegúrate de que el servidor esté corriendo
   - Verifica que el WAR esté desplegado

### Opción 2: Verificar la IP de tu PC

Ejecuta en tu PC (donde corre el servidor):

**Windows:**
```cmd
ipconfig
```

**Linux/Mac:**
```bash
ifconfig
# o
ip addr
```

Busca la IP en la red local (generalmente empieza con 192.168.x.x o 10.x.x.x).

### Opción 3: Configurar el servidor para escuchar en todas las interfaces

Si el servidor está corriendo en un servidor de aplicaciones, necesita escuchar en `0.0.0.0` (todas las interfaces) en lugar de solo `localhost`.

**Para Tomcat:**
- Edita `server.xml` y busca el elemento `<Connector>`
- Asegúrate de que tenga: `address="0.0.0.0"` o simplemente no tenga el atributo `address`

**Para GlassFish:**
- Configura el listener de red para escuchar en todas las interfaces

### Opción 4: Verificar firewall

1. **Windows Firewall:**
   - Ve a "Configuración de Firewall de Windows"
   - Permite el puerto 8080 para conexiones entrantes

2. **Firewall del router:**
   - Asegúrate de que no esté bloqueando conexiones en la red local

### Opción 5: Probar desde el navegador

Abre en tu PC (donde corre el servidor):
```
http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

Si no carga, el servidor no está accesible desde la red.

### Opción 6: Usar el EurekaServicePublisher (Recomendado)

Si tienes problemas con el servidor de aplicaciones, usa el método standalone:

1. Ejecuta `EurekaServicePublisher.java` como aplicación Java
2. Asegúrate de que tu PC tenga la IP `127.0.0.1`
3. Si tu PC tiene otra IP, edita `EurekaServicePublisher.java` y cambia la IP

## Verificación rápida

1. **En tu PC (servidor):**
   ```cmd
   netstat -an | findstr :8080
   ```
   Debe mostrar que está escuchando en `0.0.0.0:8080` o `127.0.0.1:8080`

2. **Desde el dispositivo Android:**
   - Verifica que esté en la misma red WiFi
   - La IP del dispositivo es `127.0.0.1` (según el log)
   - El servidor debe estar en `127.0.0.1`

## Solución temporal: Cambiar IP en Android

Si el servidor está en otra IP, cambia la IP en la app Android:

```kotlin
val soapService = EurekaSoapService(this)
soapService.setServerIp("NUEVA_IP_AQUI") // La IP real de tu servidor
```



