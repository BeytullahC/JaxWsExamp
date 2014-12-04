package ws.handler;

import java.io.IOException;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SoapHandler implements SOAPHandler<SOAPMessageContext> {
    
    private final String VALID_PROPERTY = "RANDOM";
    
    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        
        logToSystemOut(context);
        return true;
    }
    
    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }
    
    @Override
    public void close(MessageContext context) {
    }
    
    @Override
    public Set<QName> getHeaders() {
        return null;
    }
    
    private void logToSystemOut(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        if (outboundProperty) {
            System.out.println("\nOutbound message:");
        } else {
            System.out.println("\nInbound message:");
        }
        
        SOAPMessage message = smc.getMessage();
        try {
            message.writeTo(System.out);            
        } catch (SOAPException | IOException e) {
            System.err.println("Exception in handler: " + e);
        }
    }
    
}
