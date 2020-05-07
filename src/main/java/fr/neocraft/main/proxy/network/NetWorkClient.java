package fr.neocraft.main.proxy.network;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import fr.neocraft.main.proxy.network.util.Serializer;
import fr.neocraft.main.proxy.network.util.T;
import io.netty.buffer.ByteBuf;

public class NetWorkClient implements IMessage {

    T data;
    public NetWorkClient() { } 

    public NetWorkClient(T data) {
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

    public static class Handler implements IMessageHandler <NetWorkClient, IMessage> {
        @Override
        public IMessage onMessage(NetWorkClient message, MessageContext ctx) {
        	if (ctx.side == Side.SERVER) {
        		System.err.println("ERROR PACKET CLIENT: MUST BE CLIENT NOT SERVER SIDE");
        		System.err.println("DATA: "+message.data);
                return null;
            }
    
        	if(message.data != null)
        	{
        		message.data.A();
        	}
        	
            return null;
        }

		
    }

}
