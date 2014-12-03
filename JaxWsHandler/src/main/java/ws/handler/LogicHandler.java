/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.handler;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author 912867
 */
public class LogicHandler implements javax.xml.ws.handler.LogicalHandler<LogicalMessageContext> {
    
    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        return processMessage(context);
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return processMessage(context);
    }

    @Override
    public void close(MessageContext context) {
        // Clean up Resources
    }

    private boolean processMessage(LogicalMessageContext context) {
        Boolean outboundProperty = (Boolean)
                context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty) {
            System.out.println("\n Logical Outbound message:");
        } else {
             System.out.println("\n Logical Inbound message:");
        }
        LogicalMessage lm = context.getMessage();
        Source payload = lm.getPayload();

        // Process Payload Source
        printSource(payload);
        // ....

        // If the payload is modified, Do lm.setPayload(source) to be safe,
        // Without it, behavior may vary on the kind of source returned in lm.getPayload().
        // See LogicalMessage JavaDoc for more details.
        // lm.setPayload(modifiedPayload);
        return true;

    }

    private static void printSource(Source payload) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Result result = new StreamResult( System.out);
            transformer.transform(payload, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.err);
        }
    }
}
