package ws;

import javax.xml.ws.Endpoint;

public class WebServicePublisher{
 
	public static void main(String[] args) {
            
	   Endpoint.publish("http://localhost:8878/webservice/validator", new WebServiceImpl());
    }
 
}