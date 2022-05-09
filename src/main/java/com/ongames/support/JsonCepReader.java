package com.ongames.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonCepReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {
      is.close();
    }
  }

  public static String getTextFromJson(String dadoBuscado, String cep){
	  JSONObject json = null;
	  try{
    	json = readJsonFromUrl("https://viacep.com.br/ws/"+cep+"/json/"); 
    	return json.getString(dadoBuscado);
    } catch (IOException e1) {
    	System.err.println("Erro ao buscar CEP: "+e1.getMessage());
    	return "";
    }catch (JSONException erro) {					
    	System.err.println("O CEP "+cep+" é inválido");
    	return "";
	}	 
  }  

}