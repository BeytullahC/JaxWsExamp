/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author 912867
 */
public class LogicHandler implements javax.xml.ws.handler.LogicalHandler<LogicalMessageContext> {

    private String schemaUrl = "http://localhost:7001/JaxWsSchemaValidation/ws?xsd=1";

    @Override
    public boolean handleMessage(LogicalMessageContext context) {
        Boolean isOutBound = (Boolean) context
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (isOutBound) {
            return true;
        }

        LogicalMessage lm = context.getMessage();
        Source payload = lm.getPayload();
        StreamResult res = new StreamResult(new StringWriter());
        String message = "";

        try {
            Transformer trans;
            trans = TransformerFactory.newInstance().newTransformer();
            trans.transform(payload, res);
            message = res.getWriter().toString();
            // Validate
            validate(message, schemaUrl);
        } catch (TransformerConfigurationException e) {
            // When Source payload Transformer object could not be obtained.
            throw new WebServiceException(e);
        } catch (TransformerFactoryConfigurationError | ParserConfigurationException | SAXException | TransformerException e) {
            // When Source payload Transformer object could not be obtained.
            throw new WebServiceException(e);
        } catch (MalformedURLException e) {
            // When URL to schema is invalid.
            throw new WebServiceException(e);
        } catch (IOException e) {
            // When something is wrong with IO.
            throw new WebServiceException(e);
        }

        return true;
    }

    private void validate(String xml, String schemaUrl)
            throws ParserConfigurationException, IOException,
            MalformedURLException, SAXException {

        DocumentBuilder parser = null;
        Document document = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        parser = dbf.newDocumentBuilder();

        byte bytes[] = xml.getBytes();
        document = parser.parse(new ByteArrayInputStream(bytes));
        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
       

        Schema schema = null;
        schema = factory.newSchema(new URL(schemaUrl));
        javax.xml.validation.Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
        
    }

    private boolean processMessage(LogicalMessageContext context) throws MalformedURLException, SAXException, JAXBException {

        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

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
            Result result = new StreamResult(System.out);
            transformer.transform(payload, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.err);
        }
    }
}
