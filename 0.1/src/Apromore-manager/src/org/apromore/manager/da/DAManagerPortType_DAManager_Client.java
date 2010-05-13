
package org.apromore.manager.da;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.7
 * Thu May 13 17:20:22 EST 2010
 * Generated source version: 2.2.7
 * 
 */

public final class DAManagerPortType_DAManager_Client {

    private static final QName SERVICE_NAME = new QName("http://www.apromore.org/data_access/service_manager", "DAManagerService");

    private DAManagerPortType_DAManager_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = DAManagerService.WSDL_LOCATION;
        if (args.length > 0) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        DAManagerService ss = new DAManagerService(wsdlURL, SERVICE_NAME);
        DAManagerPortType port = ss.getDAManager();  
        
        {
        System.out.println("Invoking writeUser...");
        org.apromore.manager.model_da.WriteUserInputMsgType _writeUser_payload = new org.apromore.manager.model_da.WriteUserInputMsgType();
        org.apromore.manager.model_da.UserType _writeUser_payloadUser = new org.apromore.manager.model_da.UserType();
        java.util.List<org.apromore.manager.model_da.SearchHistoriesType> _writeUser_payloadUserSearchHistories = new java.util.ArrayList<org.apromore.manager.model_da.SearchHistoriesType>();
        org.apromore.manager.model_da.SearchHistoriesType _writeUser_payloadUserSearchHistoriesVal1 = new org.apromore.manager.model_da.SearchHistoriesType();
        _writeUser_payloadUserSearchHistoriesVal1.setSearch("Search-1947462243");
        _writeUser_payloadUserSearchHistoriesVal1.setNum(Integer.valueOf(2100754973));
        _writeUser_payloadUserSearchHistories.add(_writeUser_payloadUserSearchHistoriesVal1);
        _writeUser_payloadUser.getSearchHistories().addAll(_writeUser_payloadUserSearchHistories);
        _writeUser_payloadUser.setId(Integer.valueOf(1474019153));
        _writeUser_payloadUser.setFirstname("Firstname305831933");
        _writeUser_payloadUser.setLastname("Lastname-1867710170");
        _writeUser_payloadUser.setEmail("Email-1051117831");
        _writeUser_payloadUser.setUsername("Username2060229507");
        _writeUser_payloadUser.setPasswd("Passwd-103280609");
        _writeUser_payload.setUser(_writeUser_payloadUser);
        org.apromore.manager.model_da.WriteUserOutputMsgType _writeUser__return = port.writeUser(_writeUser_payload);
        System.out.println("writeUser.result=" + _writeUser__return);


        }
        {
        System.out.println("Invoking readDomains...");
        org.apromore.manager.model_da.ReadDomainsInputMsgType _readDomains_payload = new org.apromore.manager.model_da.ReadDomainsInputMsgType();
        _readDomains_payload.setEmpty("Empty357407026");
        org.apromore.manager.model_da.ReadDomainsOutputMsgType _readDomains__return = port.readDomains(_readDomains_payload);
        System.out.println("readDomains.result=" + _readDomains__return);


        }
        {
        System.out.println("Invoking readFormats...");
        org.apromore.manager.model_da.ReadFormatsInputMsgType _readFormats_payload = new org.apromore.manager.model_da.ReadFormatsInputMsgType();
        _readFormats_payload.setEmpty("Empty896032750");
        org.apromore.manager.model_da.ReadFormatsOutputMsgType _readFormats__return = port.readFormats(_readFormats_payload);
        System.out.println("readFormats.result=" + _readFormats__return);


        }
        {
        System.out.println("Invoking readUser...");
        org.apromore.manager.model_da.ReadUserInputMsgType _readUser_payload = new org.apromore.manager.model_da.ReadUserInputMsgType();
        _readUser_payload.setUsername("Username158534696");
        org.apromore.manager.model_da.ReadUserOutputMsgType _readUser__return = port.readUser(_readUser_payload);
        System.out.println("readUser.result=" + _readUser__return);


        }
        {
        System.out.println("Invoking readProcessSummaries...");
        org.apromore.manager.model_da.ReadProcessSummariesInputMsgType _readProcessSummaries_payload = new org.apromore.manager.model_da.ReadProcessSummariesInputMsgType();
        _readProcessSummaries_payload.setSearchExpression("SearchExpression-785670316");
        org.apromore.manager.model_da.ReadProcessSummariesOutputMsgType _readProcessSummaries__return = port.readProcessSummaries(_readProcessSummaries_payload);
        System.out.println("readProcessSummaries.result=" + _readProcessSummaries__return);


        }

        System.exit(0);
    }

}
