package ws;

import javax.jws.HandlerChain;
import javax.jws.WebService;


@WebService(endpointInterface = "ws.WebServiceInterface")
@HandlerChain(file="./handler/handlers.xml")
public class WebServiceImpl implements WebServiceInterface{

	@Override
	public String printMessage() {
		return "SERVICE is Running";
	}

}