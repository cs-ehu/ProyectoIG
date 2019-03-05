package bean;

import businessLogic.FacadeImplementationWS;

public class FacadeBean {
	
	private static FacadeImplementationWS facade = new FacadeImplementationWS();

	private FacadeBean(){}
	
	public static FacadeImplementationWS getFacade(){
		return facade;
	}
}
