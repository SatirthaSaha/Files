/* Copyright (c) 2017 Travelport. All rights reserved. */
 
package com.travelport.resman.atomic.hca;

import java.io.IOException;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.ws.Holder;

import org.apache.commons.lang.StringUtils;

import com.travelport.acs.logger.impl.ACSLogger;
import com.travelport.acs.logger.impl.ACSLogger.LOGSYSTEM;
import com.travelport.resman.atomic.hca.dto.HCACredentialInfo;
import com.travelport.resman.atomic.hca.util.CustomRuntimeException;
import com.travelport.sandbox.hca.schemas.hca.v1.CommandFault;
import com.travelport.sandbox.hca.schemas.hca.v1.CommandRQ;
import com.travelport.sandbox.hca.schemas.hca.v1.CommandRS;
import com.travelport.sandbox.hca.schemas.hca.v1.HCACommandPortType;
import com.travelport.schemas.hca.v0.SessionContextHCA;

public abstract class DirHCAConnector implements HostConnector<CommandRQ, CommandRS> {

	private static final ACSLogger LOGGER = ACSLogger.getLogger(DirHCAConnector.class);
	private static com.travelport.sandbox.hca.schemas.hca.v1.ObjectFactory hcaObjectFactory = new com.travelport.sandbox.hca.schemas.hca.v1.ObjectFactory();

	/**
	 * Invoke HCA with CommandRQ and Existing Session Token
	 * 
	 * @param request
	 *            CommandRQ payload
	 * @param sessionToken
	 *            Existing sessionToken
	 * @return CommandRS
	 * @throws CommandFault 
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws Exception
	 * 
	 */
	public CommandRS invokeService(CommandRQ request, String sessionToken, HCACredentialInfo hcaCredentialInfo) throws  CustomRuntimeException  {
		
		LOGGER.debug("DirHCAConnector : In invokeService()");
		
		hasHCAInformation(request);
		
		LOGGER.info("hcaCredentialInfo ==> " + hcaCredentialInfo);
		boolean startSessionFlag = false;
		if(sessionToken == null){
			startSessionFlag = true;
		}
		
		if (extractHCACredInfo(hcaCredentialInfo)
				&& hcaCredentialInfo.getSessionContextInfo().getHostAccessProfile() != null
				&& !StringUtils.isEmpty(
								hcaCredentialInfo.getSessionContextInfo().getHostAccessProfile().getAccessProfileName ())) {
					
			SessionContextBuilder builder = hasHCACred(sessionToken, hcaCredentialInfo, startSessionFlag);
			
			SessionContextHCA sessionContext = builder.build();
			LOGGER.debug("DirHCAConnector : Got session context : " + sessionContext);
			
			// This gets the delegate needed to execute your request. The file hca-config.xml is in hca-connector.jar as part of the class path. You
            // don't need to have a copy of it in your jar or application. This will send and receive via JCP Manager within a CXF conduit. Alternatively,
			HCACommandPortType hcaService = HCAConnectorFactory.getInstance("hca-config.xml").getHCAService("JCPManager");
			
			LOGGER.debug("DirHCAConnector : got hcaservice - " + hcaService);
			Holder<SessionContextHCA> contextHolder = new Holder<SessionContextHCA>(sessionContext);
			LOGGER.logRequestResponse("Galileo(1G) HCA Request", HCAConnector.prepareRequest(contextHolder.value, hcaObjectFactory.createCommandRQ(request)), LOGSYSTEM.HCA);
			CommandRS response;
			try {
				response = hcaService.command(contextHolder, request);
			} catch (CommandFault e) {
				throw new CustomRuntimeException("CommandFault", e);
			}
			LOGGER.logRequestResponse("Galileo(1G) HCA Request", HCAConnector.prepareRequest(contextHolder.value, hcaObjectFactory.createCommandRQ(request)), LOGSYSTEM.HCA);
			
			SessionContextHCA sessionContextRsp = contextHolder.value;
			
            isSessionFlag(hcaCredentialInfo, startSessionFlag, sessionContextRsp);
			
			isHCAResponse(response);
			
			return response;
			
		}
		
		return null;
	}

	/**
	 * 
	 * @param sessionToken
	 * @param hcaCredentialInfo
	 * @param startSessionFlag
	 * @return
	 */
	private SessionContextBuilder hasHCACred(String sessionToken, HCACredentialInfo hcaCredentialInfo,
			boolean startSessionFlag) {
		LOGGER.debug("DirHCAConnector : Got credential");
		SessionContextBuilder builder = new SessionContextBuilder().setE2eTrackingId(UUID.randomUUID().toString())
				.setOriginApplication(hcaCredentialInfo.getSessionContextInfo().getPointOfSale().getOriginApplication())
				.setCustomerProfileID(hcaCredentialInfo.getSessionContextInfo().getHostAccessProfile().getAccessProfileName ())
				.setExternalSessionToken(sessionToken).setStartSession(startSessionFlag).setEndSession(false);
		return builder;
	}

	/**
	 * 
	 * @param request
	 */
	private void hasHCAInformation(CommandRQ request) {
		if (hasHCADetails(request) && request.getHCARequest().getHCAAData().getHCAAPayload() != null
				&& request.getHCARequest().getHCAAData().getHCAAPayload().getHCAADIR() != null) {

			LOGGER.info("DIR Request ==> " + request.getHCARequest().getHCAAData().getHCAAPayload().getHCAADIR());

		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean hasHCADetails(CommandRQ request) {
		return request != null && request.getHCARequest() != null && request.getHCARequest().getHCAAData() != null;
	}

	/**
	 * 
	 * @param response
	 */
	private static void isHCAResponse(CommandRS response) {
		if(response != null && response.getHCAResponse() != null && response.getHCAResponse().getHCAAData() != null
				&& response.getHCAResponse().getHCAAData().getHCAAPayload() != null) {
			if(response.getHCAResponse().getHCAAData().getHCAAPayload().getHCAAKLR() != null) {
				LOGGER.info("DIR Response ==> " + response.getHCAResponse().getHCAAData().getHCAAPayload().getHCAADIR());
			}
		} else {
			LOGGER.error("DirHCAConnector : Response from HCA is Empty!!");
		}
	}

	/**
	 * 
	 * @param hcaCredentialInfo
	 * @param startSessionFlag
	 * @param sessionContextRsp
	 */
	private static void isSessionFlag(HCACredentialInfo hcaCredentialInfo, boolean startSessionFlag,
			SessionContextHCA sessionContextRsp) {
		if(startSessionFlag && sessionContextRsp.getExternalSessionToken() != null){
			hcaCredentialInfo.setSessionToken(sessionContextRsp.getExternalSessionToken());
		}
	}

	/**
	 * 
	 * @param hcaCredentialInfo
	 * @return
	 */
	private static boolean extractHCACredInfo(HCACredentialInfo hcaCredentialInfo) {
		return hcaCredentialInfo != null && hcaCredentialInfo.getSessionContextInfo() != null
				&& hcaCredentialInfo.getSessionContextInfo().getPointOfSale() != null
				&& !StringUtils.isEmpty(hcaCredentialInfo.getSessionContextInfo().getPointOfSale().getOriginApplication());
	}



	/**
	 * Get Session Token using Interaction Id; 
	 * It will try to retrieve existing sessionToken 
	 * from Interaction Manager 
	 * 
	 * @param interactionId
	 *            Interaction ID
	 * @return SessionToken
	 * @throws Exception
	 */
	public String getSessionToken(String interactionId) {
		return returnNull();
	}

	@Override
	public String createSessionToken()  {
		return returnNull();
	}

	public String returnNull(){
		return null;
	}
	
	@Override
	public CommandRS invokeService(CommandRQ request, String sessionToken, String interactionId){
		return null;
	}
	
	
	

}
