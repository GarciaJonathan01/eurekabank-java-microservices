# 🏦 EurekaBank - Cliente Consola SOAP

## 📋 Descripción

Cliente consola para el sistema bancario EurekaBank que consume servicios SOAP.

## 🚀 Formas de Ejecución

### Opción 1: Ejecución Rápida (Recomendada)

Doble clic en `ejecutar.bat` o ejecuta desde la terminal:

```batch
ejecutar.bat
```

**Características:**
- ✅ Compilación silenciosa
- ✅ Colores ANSI activados
- ✅ Soporte completo para español (tildes, ñ, etc.)
- ✅ Interfaz limpia y profesional

### Opción 2: Ejecución con Logs Detallados

Doble clic en `ejecutar_con_logs.bat` o ejecuta desde la terminal:

```batch
ejecutar_con_logs.bat
```

**Características:**
- ✅ Muestra todo el proceso de compilación
- ✅ Útil para depuración
- ✅ Colores ANSI activados
- ✅ Soporte completo para español

### Opción 3: Ejecución Manual con Maven

```batch
mvn clean compile
mvn exec:java -Dexec.mainClass="ec.edu.monster.vista.CliCon_Vista"
```

## ⚙️ Requisitos Previos

1. **Java JDK 11 o superior**
   - Verificar: `java -version`

2. **Apache Maven**
   - Verificar: `mvn -version`
   - Descargar: https://maven.apache.org/download.cgi

3. **Servicio SOAP desplegado**
   - El servicio debe estar corriendo en: `http://127.0.0.1:8080/WS_EUREKABANK_SERVICIO/EurekaService?wsdl`
   - Si está en otra URL, edita `EurekaService.java` línea 22

## 🔐 Credenciales de Acceso

- **Usuario:** `MONSTER`
- **Contraseña:** (Hash MD5 configurado en el código)

## 📝 Notas Importantes

- Los archivos `.bat` configuran automáticamente:
  - Codificación UTF-8 para soporte de español
  - Colores ANSI en Windows 10+
  - Variables de entorno necesarias

- Si los colores no se muestran correctamente:
  - Asegúrate de usar Windows 10 o superior
  - Ejecuta como Administrador la primera vez
  - El script activará automáticamente los colores ANSI

## 🐛 Solución de Problemas

### Error: "Maven no está instalado"
- Instala Maven y agrégalo al PATH del sistema
- Reinicia la terminal después de instalar

### Error: "No se puede conectar al servicio SOAP"
- Verifica que el servicio esté desplegado y corriendo
- Revisa la URL en `EurekaService.java`
- Comprueba el firewall y la conectividad de red

### Los colores no se muestran
- Ejecuta el `.bat` como Administrador la primera vez
- Verifica que estés usando Windows 10 o superior
- El script activará automáticamente los colores ANSI

## 👥 Desarrolladores

**Grupo Monster G09**
- Sistema Bancario EurekaBank
- Cliente Consola SOAP

## 📅 Versión

**v1.0** - 2025

---

¡Gracias por usar EurekaBank! 🏦

