# Configuración de IP del Servidor

## IP por Defecto
La aplicación está configurada para usar la IP: **127.0.0.1**

## Cambiar la IP del Servidor

### Opción 1: Desde el código (temporal)
Puedes cambiar la IP directamente en el código modificando la constante `DEFAULT_IP` en el archivo:
```
app/src/main/java/ec/edu/monster/servicio/EurekaSoapService.kt
```

### Opción 2: Usando SharedPreferences (recomendado)
La aplicación guarda la IP en SharedPreferences. Puedes cambiarla programáticamente desde cualquier Activity:

```kotlin
val soapService = EurekaSoapService(this)
soapService.setServerIp("127.0.0.1") // Nueva IP
```

### Opción 3: IP Alternativa (0.0.0.0)
Si configuras la IP como "0.0.0.0", la aplicación automáticamente usará la IP por defecto (127.0.0.1).

## Verificar la IP Actual
Para verificar qué IP está usando la aplicación:

```kotlin
val soapService = EurekaSoapService(this)
val currentIp = soapService.getCurrentServerIp()
Toast.makeText(this, "IP actual: $currentIp", Toast.LENGTH_SHORT).show()
```

## URL Completa del Servicio
La URL se construye automáticamente como:
```
http://[IP]:8080/WS_EUREKABANK_SERVICIO/EurekaService
```

Ejemplo con IP por defecto:
```
http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService
```



