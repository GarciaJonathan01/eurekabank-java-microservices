# Configuración para usar IP 127.0.0.1

Este documento explica cómo configurar el servicio SOAP para que siempre use la IP **127.0.0.1** en lugar de localhost.

## Opción 1: Configurar el Servidor de Aplicaciones (RECOMENDADO)

### Para GlassFish/Payara:

1. Abre la consola de administración de GlassFish/Payara
2. Ve a **Configurations** → **server-config** → **Network Config** → **Network Listeners**
3. Edita el listener **http-listener-1** (puerto 8080)
4. Cambia el campo **Address** de `0.0.0.0` o `localhost` a `127.0.0.1`
5. Guarda los cambios y reinicia el servidor

### Para Tomcat:

1. Edita el archivo `server.xml` en la carpeta `conf` de Tomcat
2. Busca el elemento `<Connector>` para el puerto 8080
3. Agrega o modifica el atributo `address="127.0.0.1"`:

```xml
<Connector port="8080" protocol="HTTP/1.1"
           address="127.0.0.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```

4. Reinicia Tomcat

## Opción 2: Usar la Clase de Publicación Personalizada

Si prefieres ejecutar el servicio de forma standalone (sin servidor de aplicaciones):

1. Ejecuta la clase `EurekaServicePublisher.java` como aplicación Java
2. El servicio estará disponible en: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService`
3. El WSDL estará en: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl`

## Verificación

Después de configurar, verifica que el servicio esté accesible:

- URL del servicio: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService`
- WSDL: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl`

## Nota Importante

Si usas un servidor de aplicaciones (GlassFish, Payara, Tomcat, etc.), la **Opción 1** es la más recomendada y permanente. La configuración en el servidor asegura que todas las aplicaciones desplegadas usen esa IP.

