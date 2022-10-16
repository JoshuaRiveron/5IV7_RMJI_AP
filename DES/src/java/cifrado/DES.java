/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cifrado;

/**
 *
 * @author alumno
 * 
 * vamos a cifrar des del siguiente modo:
 * 
 * Modo de bloques de 64 bits 
 * Manejar una llave privada de 64 bits 
 * Vamos a inicilizar la llave privada a 56 bits
 * 
 * Las permutaciones y las cajas de sustitucion se van 
 * a crear desde la instancia del algoritmo de cifrado
 * para ello vamos a ocupar las librerias de crypto y
 * security
 */

import java.security.*;

//para generar las llaves
import javax.crypto.spec.*;

import javax.crypto.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DES {
    
    private String path, clave;
    private Cipher cifrador;
    private SecretKey key;
    
    public DES(String ruta, String clave) throws Exception{
        this.path = ruta;
        this.clave=clave;
    }
    
    public void cifrar() throws Exception{
        
        key = new SecretKeySpec(this.clave.getBytes(), "DES");
        cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        //Vamos a generar las subclaves
        //se generan las 16 subclaves
        
        
        
        
        //cifrado
        cifrador.init(Cipher.ENCRYPT_MODE, key);
        
        //el problema es la lectura de los bloques
        //es la entrada o salida del buffer del archivo
        
        byte[] buffer = new byte[1000];
        byte[] bufferCifrado;
        
        //generar los archivos
        FileInputStream in = new FileInputStream(path);
        FileOutputStream out = new FileOutputStream(path.substring(0, path.length()-4)+".des");
        
        //lectura
        int bytesleidos = in.read(buffer, 0, 1000);
        while(bytesleidos != -1){
            //pasar al texto claro a cifrado
            bufferCifrado = cifrador.update(buffer, 0, bytesleidos);
            //generamos la salida
            out.write(bufferCifrado);
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //vamos a reunir los pedazos
        bufferCifrado = cifrador.doFinal();
        //ya escribo la salida 
        out.write(bufferCifrado);
        
        in.close();
        out.close();
    }
    
    public void descifrar() throws Exception{
        
        key = new SecretKeySpec(this.clave.getBytes(), "DES");
        cifrador = Cipher.getInstance("DES/ECB/PKCS5Padding");
        
        cifrador.init(Cipher.DECRYPT_MODE, key);
        
        //el buffer de la entrada y salida debe de ser en bytes
        
        byte[] bufferPlano;
        
        FileInputStream in = new FileInputStream(path);
        FileOutputStream out = new FileOutputStream(path.substring(0,path.length()-4)+".txt");
        
        byte[] buffer = new byte[1000];
        
        //damos lectura de cada elemento
        int bytesleidos = in.read(buffer, 0, 1000);
        //mientras no este al final del archivo que siga leyendo
        
        while(bytesleidos != -1){
            //pasamos el texto plano a descifrar
            bufferPlano = cifrador.update(buffer, 0, bytesleidos);
            //generamos la salida
            out.write(bufferPlano);
            bytesleidos = in.read(buffer, 0, 1000);
        }
        //reuno los bloques
        bufferPlano = cifrador.doFinal();
        
        in.close();
        out.close();
    }
}