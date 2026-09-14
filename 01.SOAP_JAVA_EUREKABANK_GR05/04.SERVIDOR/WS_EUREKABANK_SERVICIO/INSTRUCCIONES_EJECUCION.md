# Instrucciones para Ejecutar el Servidor SOAP

## Pasos para Ejecutar el Servidor

### 1. Compilar el Proyecto
```bash
mvn clean package
```

### 2. Ejecutar el Servidor Standalone

Ejecuta la clase `EurekaServicePublisher.java` como aplicación Java.

**En NetBeans/Eclipse:**
- Click derecho en `EurekaServicePublisher.java`
- Selecciona "Run File" o "Ejecutar"

**Desde línea de comandos:**
```bash
java -cp "target/classes:target/dependency/*" ec.edu.monster.servicios.EurekaServicePublisher
```

### 3. Verificar que el Servidor Esté Corriendo

Deberías ver en la consola:
```
==========================================
Publicando servicio SOAP EurekaBank
==========================================
URL local: http://localhost:8080/WS_EUREKABANK_SERVICIO/EurekaService
URL de red: http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService
✓ Servicio publicado exitosamente
```

### 4. Probar el Servidor

Abre en tu navegador (en la misma PC donde corre el servidor):
```
http://localhost:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

O desde otra PC/dispositivo en la misma red:
```
http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
```

Si ves el XML del WSDL, el servidor está funcionando correctamente.

## Solución de Problemas

### Error: "Address already in use"
El puerto 8080 está en uso. Soluciones:
1. Cierra otras aplicaciones que usen el puerto 8080
2. Cambia el puerto en `EurekaServicePublisher.java` (línea 17)

### Error: "Connection refused" desde Android
1. **Verifica el Firewall de Windows:**
   - Panel de Control → Firewall de Windows
   - Configuración avanzada → Reglas de entrada
   - Nueva regla → Puerto → TCP → 8080 → Permitir

2. **Verifica que el servidor esté corriendo:**
   - Debe mostrar "✓ Servicio publicado exitosamente"

3. **Verifica la IP de tu PC:**
   ```cmd
   ipconfig
   ```
   Si tu IP no es `127.0.0.1`, actualiza:
   - `EurekaServicePublisher.java` (línea 22)
   - La app Android (usando `soapService.setServerIp("tu_ip_real")`)

### El servidor no es accesible desde la red
1. Asegúrate de que el servidor esté corriendo
2. Verifica el firewall (ver arriba)
3. Verifica que tu PC y el dispositivo Android estén en la misma red WiFi
4. Prueba desde el navegador del dispositivo Android:
   ```
   http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl
   ```

## Verificación Rápida

Ejecuta en tu PC (donde corre el servidor):
```cmd
netstat -an | findstr :8080
```

Deberías ver:
```
TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING
```

Si ves `127.0.0.1:8080`, el servidor solo escucha en localhost y no será accesible desde la red.

## Notas Importantes

- El servidor debe estar corriendo mientras uses la app Android
- No cierres la ventana de consola donde corre el servidor
- Para detener el servidor, presiona ENTER en la consola



