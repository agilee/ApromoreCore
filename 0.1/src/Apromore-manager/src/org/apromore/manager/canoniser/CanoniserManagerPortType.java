package org.apromore.manager.canoniser;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.7
 * Thu May 13 17:44:21 EST 2010
 * Generated source version: 2.2.7
 * 
 */
 
@WebService(targetNamespace = "http://www.apromore.org/canoniser/service_manager", name = "CanoniserManagerPortType")
@XmlSeeAlso({org.apromore.manager.model_canoniser.ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface CanoniserManagerPortType {

    @WebResult(name = "CanoniseProcessOutputMsg", targetNamespace = "http://www.apromore.org/canoniser/model_manager", partName = "payload")
    @WebMethod(operationName = "CanoniseProcess")
    public org.apromore.manager.model_canoniser.CanoniseProcessOutputMsgType canoniseProcess(
        @WebParam(partName = "payload", name = "CanoniseProcessInputMsg", targetNamespace = "http://www.apromore.org/canoniser/model_manager")
        org.apromore.manager.model_canoniser.CanoniseProcessInputMsgType payload
    );

    @WebResult(name = "DeCanoniseProcessOutputMsg", targetNamespace = "http://www.apromore.org/canoniser/model_manager", partName = "payload")
    @WebMethod(operationName = "DeCanoniseProcess")
    public org.apromore.manager.model_canoniser.DeCanoniseProcessOutputMsgType deCanoniseProcess(
        @WebParam(partName = "payload", name = "DeCanoniseProcessInputMsg", targetNamespace = "http://www.apromore.org/canoniser/model_manager")
        org.apromore.manager.model_canoniser.DeCanoniseProcessInputMsgType payload
    );
}
