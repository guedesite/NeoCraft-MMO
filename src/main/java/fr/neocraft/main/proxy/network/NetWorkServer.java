package fr.neocraft.main.proxy.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import fr.neocraft.main.proxy.network.util.Serializer;
import fr.neocraft.main.proxy.network.util.T;
import io.netty.buffer.ByteBuf;

public class NetWorkServer implements IMessage {

    T data;
    public NetWorkServer() { } 

    public NetWorkServer(T data) {
        this.data = data;
      
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = (T) Serializer.fromString( buf );
  
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	ByteBufUtils.writeUTF8String(buf, Serializer.toString(data));
    }

    public static class Handler implements IMessageHandler <NetWorkServer, IMessage> {
        @Override
        public IMessage onMessage(NetWorkServer message, MessageContext ctx) {
        	if (ctx.side == Side.CLIENT) {
        		System.err.println("ERROR PACKET SERVER: MUST BE SERVER NOT CLIENT SIDE");
        		System.err.println("DATA: "+message.data);
                return null;
            }
        	
        	if(message.data != null)
        	{
        		message.data.A(ctx.getServerHandler().playerEntity);
        	}
        	
            return null;
        }

		
    }

}
