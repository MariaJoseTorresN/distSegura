package edu.escuelaing.arep;

import static spark.Spark.*;
import org.json.*;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Calculator servicesCalculator = new Calculator();
    public static void main( String[] args )
    {   
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath,truststorePassword);
        secure(getKeyStore(), "123456", getTrustStore(), "123456");
        port(getPort());
        get("/hello", (req, res) ->{
            return "Hello World";
        });
        get("/cuadrado", (req,res) ->{
            return getCuadrado(req, res);
        });
    }

    private static Object getCuadrado(spark.Request req, spark.Response res){
        double calculo = servicesCalculator.cuadrado(Double.parseDouble(((spark.Request) req).queryParams("value")));
        JSONObject myObject = new JSONObject();
        myObject.put("operation","Log");
        myObject.put("input",((spark.Request) req).queryParams("value"));
        myObject.put("output",calculo);
        return myObject;
    }

    private static String getTrustStore() {
        if (System.getenv("TRUSTSTORE") != null) {
            return System.getenv("TRUSTSTORE");
        }
        return "keystores/myTrustStore"; //returns default keystore 
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    static String getKeyStore(){
        if (System.getenv("KEYSTORE") != null) {
            return System.getenv("KEYSTORE");
        }
        return "keystores/ecikeystore.p12"; //returns default keystore 

    }
}
