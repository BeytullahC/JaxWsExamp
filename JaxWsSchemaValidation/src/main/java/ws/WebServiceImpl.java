package ws;

import com.sun.xml.internal.ws.developer.SchemaValidation;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import ws.bean.TestBean;


@WebService(endpointInterface = "ws.WebServiceInterface", serviceName="ws")
@HandlerChain(file="./handler/handlers.xml")
@SchemaValidation
public class WebServiceImpl implements WebServiceInterface{

	@Override
	public String printMessage() {
		return "SERVICE is Running";
	}

	@Override
	public String setTest(TestBean testBean) throws Exception {
		// TODO Auto-generated method stub
		return testBean.toString();
	}

}