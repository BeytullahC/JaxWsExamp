package ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import ws.bean.TestBean;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface WebServiceInterface {

	@WebMethod
	String printMessage();
	
	@WebMethod
	String setTest(@WebParam(name="testBean") TestBean  testBean) throws Exception;

}