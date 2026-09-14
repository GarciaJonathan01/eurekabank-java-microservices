package ec.edu.monster.servicio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ec.edu.monster.modelo.Movimiento;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EurekaService {

    private static final String MS_AUTH = "http://127.0.0.1:8080/WSEurekaBank_Autenticacion_Rest/resources/autenticacion/login";
    private static final String MS_CUENTAS = "http://127.0.0.1:8080/WSEurekaBank_Cuentas_Rest/resources/corebancario/movimientos/";
    private static final String MS_OPERACIONES_DEP = "http://127.0.0.1:8080/WSEurekaBank_Operaciones_Rest/resources/corebancario/deposito";
    private static final String MS_OPERACIONES_RET = "http://127.0.0.1:8080/WSEurekaBank_Operaciones_Rest/resources/corebancario/retiro";
    private static final String MS_TRANSF = "http://127.0.0.1:8080/WSEurekaBank_Transferencias_Rest/resources/corebancario/transferencia";

    private static final String MONOLITO_BASE = "http://127.0.0.1:8080/WSEurekaBank_Restfull_Java_G5/resources/corebancario";

    private final HttpClient http;
    private final Gson gson;

    public EurekaService() {
        this.http = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().setLenient().create();
    }

    public boolean autenticar(String usuario, String clave) {
        try {
            String url = MS_AUTH + "?usuario=" + encode(usuario) + "&clave=" + encode(clave);
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString("{}"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200) {
                JsonObject obj = gson.fromJson(resp.body(), JsonObject.class);
                return obj.has("estado") && obj.get("estado").getAsInt() == 1;
            }
            return false;
        } catch (Exception e) {
            System.err.println("ERROR: El Microservicio de Autenticación REST NO está disponible o falló: " + e.getMessage());
            return false;
        }
    }

    public List<Movimiento> traerMovimientos(String cuenta) {
        List<Movimiento> lista = new ArrayList<>();
        try {
            String url = MS_CUENTAS + encode(cuenta);
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept", "application/json").build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            
            if (resp.statusCode() != 200) {
                url = MONOLITO_BASE + "/movimientos/" + encode(cuenta);
                req = HttpRequest.newBuilder().uri(URI.create(url)).GET().header("Accept", "application/json").build();
                resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            }

            if (resp.statusCode() == 200) {
                JsonArray arr = gson.fromJson(resp.body(), JsonArray.class);
                for (JsonElement el : arr) {
                    JsonObject o = el.getAsJsonObject();
                    Movimiento m = new Movimiento();
                    m.setCuencodigo(getAsString(o, "cuenta"));
                    m.setMovinumero(getAsInt(o, "nromov"));
                    m.setMoviimporte(getAsDouble(o, "importe"));
                    m.setTipocodigo(getAsString(o, "tipo"));
                    m.setCuenreferencia(getAsString(o, "referencia"));
                    Date fecha = parseFecha(getAsString(o, "fecha"));
                    if (fecha != null) m.setMovifecha(fecha);
                    lista.add(m);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al consultar movimientos REST: " + e.getMessage());
        }
        return lista;
    }

    public int regDeposito(String cuenta, double importe) {
        return ejecutarPost(MS_OPERACIONES_DEP + "?cuenta=" + encode(cuenta) + "&importe=" + importe, MONOLITO_BASE + "/deposito?cuenta=" + encode(cuenta) + "&importe=" + importe);
    }

    public int regRetiro(String cuenta, double importe) {
        return ejecutarPost(MS_OPERACIONES_RET + "?cuenta=" + encode(cuenta) + "&importe=" + importe, MONOLITO_BASE + "/retiro?cuenta=" + encode(cuenta) + "&importe=" + importe);
    }

    public int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        String query = "?cuentaOrigen=" + encode(cuentaOrigen) + "&cuentaDestino=" + encode(cuentaDestino) + "&importe=" + importe;
        return ejecutarPost(MS_TRANSF + query, MONOLITO_BASE + "/transferencia" + query);
    }

    private int ejecutarPost(String urlMs, String urlMono) {
        try {
            HttpRequest req = HttpRequest.newBuilder().uri(URI.create(urlMs)).POST(HttpRequest.BodyPublishers.ofString("{}")).header("Content-Type", "application/json").build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() != 200) {
                req = HttpRequest.newBuilder().uri(URI.create(urlMono)).POST(HttpRequest.BodyPublishers.ofString("{}")).header("Content-Type", "application/json").build();
                resp = http.send(req, HttpResponse.BodyHandlers.ofString());
            }
            if (resp.statusCode() == 200) {
                JsonObject obj = gson.fromJson(resp.body(), JsonObject.class);
                return (obj != null && obj.has("estado") && obj.get("estado").getAsInt() == 1) ? 1 : 0;
            }
        } catch (Exception e) {
            System.err.println("Error en transacción REST: " + e.getMessage());
        }
        return 0;
    }

    private String encode(String val) {
        return val == null ? "" : URLEncoder.encode(val, StandardCharsets.UTF_8);
    }

    private String getAsString(JsonObject o, String member) {
        return (o.has(member) && !o.get(member).isJsonNull()) ? o.get(member).getAsString() : null;
    }

    private int getAsInt(JsonObject o, String member) {
        return (o.has(member) && !o.get(member).isJsonNull()) ? o.get(member).getAsInt() : 0;
    }

    private double getAsDouble(JsonObject o, String member) {
        return (o.has(member) && !o.get(member).isJsonNull()) ? o.get(member).getAsDouble() : 0.0;
    }

    private Date parseFecha(String raw) {
        if (raw == null) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(raw);
        } catch (Exception ignore) {
            return null;
        }
    }
}
