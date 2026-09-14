# Solución de Errores de Conexión SOAP

## Errores Comunes y Soluciones

### 1. Error: "No se pudo conectar al servidor"

**Causas posibles:**
- El servidor no está corriendo
- La IP del servidor es incorrecta
- Estás usando un emulador y necesitas usar `10.0.2.2` en lugar de la IP real
- Firewall bloqueando la conexión
- No estás en la misma red que el servidor

**Soluciones:**

#### Si estás usando un EMULADOR Android:
```kotlin
val soapService = EurekaSoapService(this)
soapService.setServerIp("10.0.2.2") // IP especial para emulador
```

#### Si estás usando un DISPOSITIVO FÍSICO:
1. Verifica que el dispositivo esté en la misma red WiFi que el servidor
2. Verifica la IP del servidor ejecutando en el servidor:
   - Windows: `ipconfig`
   - Linux/Mac: `ifconfig` o `ip addr`
3. Configura la IP correcta:
```kotlin
val soapService = EurekaSoapService(this)
soapService.setServerIp("127.0.0.1") // Tu IP real
```

### 2. Error: "Timeout"

**Causas:**
- El servidor tarda mucho en responder
- La red es lenta
- El servidor está sobrecargado

**Solución:**
- Verifica que el servidor esté corriendo y accesible
- Prueba acceder desde un navegador: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl`

### 3. Error: "Host no encontrado"

**Causas:**
- La IP está mal escrita
- No hay conexión a internet/red

**Solución:**
- Verifica la IP del servidor
- Verifica tu conexión de red

### 4. Verificar la Conexión

Puedes usar el método de diagnóstico:

```kotlin
val soapService = EurekaSoapService(this)
soapService.verificarConexion(object : EurekaSoapService.SoapCallback<Boolean> {
    override fun onSuccess(result: Boolean) {
        Toast.makeText(this@TuActivity, "Conexión exitosa!", Toast.LENGTH_SHORT).show()
    }
    
    override fun onError(error: String) {
        Toast.makeText(this@TuActivity, "Error: $error", Toast.LENGTH_LONG).show()
    }
})
```

## Verificar Logs

Para ver información detallada de la conexión, revisa los logs de Android:

1. Abre Android Studio
2. Ve a la pestaña "Logcat"
3. Filtra por: `EurekaSoapService`
4. Verás mensajes como:
   - `URL del servicio: http://...`
   - `Enviando petición SOAP a: ...`
   - Errores detallados si hay problemas

## Checklist de Verificación

Antes de reportar un error, verifica:

- [ ] El servidor Java está corriendo en el puerto 8080
- [ ] Puedes acceder al WSDL desde un navegador: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl`
- [ ] Si usas emulador, configuraste `10.0.2.2`
- [ ] Si usas dispositivo físico, estás en la misma red WiFi
- [ ] La IP del servidor es correcta
- [ ] No hay firewall bloqueando el puerto 8080
- [ ] Revisaste los logs en Logcat

## IPs Especiales

- **Emulador Android**: Usa `10.0.2.2` para acceder a `localhost` de tu PC
- **Dispositivo físico en misma red**: Usa la IP real del servidor (ej: `127.0.0.1`)
- **Dispositivo físico en red diferente**: Necesitas configurar port forwarding o usar la IP pública

## Ejemplo de Configuración Correcta

```kotlin
// En tu Activity
val soapService = EurekaSoapService(this)

// Para emulador:
// soapService.setServerIp("10.0.2.2")

// Para dispositivo físico en misma red:
soapService.setServerIp("127.0.0.1")

// Verificar IP actual:
val currentIp = soapService.getCurrentServerIp()
val currentUrl = soapService.getCurrentServiceUrl()
Log.d("Config", "IP: $currentIp, URL: $currentUrl")
```



