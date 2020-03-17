package fr.neocraft.main.proxy.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

import cpw.mods.fml.common.network.ByteBufUtils;
import fr.neocraft.main.util.CRASH;
import io.netty.buffer.ByteBuf;


public class Serializer {
    public static String toString( Serializable o ){
        try {
        	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( o );
            oos.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
        	 CRASH.Push(e);
            return null;
        }
    }

 

    public static Object fromString( ByteBuf u ){
        try{
        	ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( Base64.getDecoder().decode( ByteBufUtils.readUTF8String(u)) ) );
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (ClassNotFoundException e) {
            CRASH.Push(e);
            return null;
        } catch (IOException e) {
        	 CRASH.Push(e);
        	return null;
        }

    }
    
    public static Object fromString( String u ){
        try{
        	ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( Base64.getDecoder().decode( u) ) );
            Object o = ois.readObject();
            ois.close();
            return o;
        } catch (ClassNotFoundException e) {
            CRASH.Push(e);
            return null;
        } catch (IOException e) {
        	 CRASH.Push(e);
        	return null;
        }

    }


}