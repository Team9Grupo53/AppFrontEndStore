package co.edu.mintic.connection;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionRest {

	private URL url = null;
	private static String sitio = "http://localhost:5000/";
	private static HttpURLConnection httpCon = null;

	public ConnectionRest(String path) {
		try {
			url = new URL(sitio+path);
			httpCon = (HttpURLConnection)url.openConnection();
            if (httpCon != null) {
                System.out.println("Conexion realizada con exito con el servicio rest");
            }
            httpCon.setRequestProperty("Accept", "application/json");
    		httpCon.setRequestProperty("Content-Type", "application/json");
        } catch (Exception ex) {
            System.err.println("No se ha podido conectar al servicio rest\n" + ex.getMessage());
        }		
	}

	public static HttpURLConnection getConnection(String path) {
		new ConnectionRest(path);
		return httpCon;
	}
	
	/*
	 * metodo que cierra una conexion de la BD
     */
    public static void close() {
        try {
        	System.out.println("httpCon::>"+httpCon);
            if (httpCon != null) {
            	httpCon.disconnect();
            }
        }  catch (Exception ex) {
            System.err.println("Error al cerrar la conexion con el servicio::>" + ex.getMessage());

        }
    }
}
