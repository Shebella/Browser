package tw.org.itri.ccma.css.safebox.service.client;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tw.org.itri.ccma.css.safebox.config.ClientConfig;
import tw.org.itri.ccma.css.util.JsonUtil;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/***
 * Browser App 專用的 Event 同步服務
 * 
 * @author Keanu
 * 
 */
public abstract class AbsEventSyncService implements IEventSyncService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbsEventSyncService.class);

	@Autowired
	protected ClientConfig clientConfig;

	private String getServiceResource() {
		return clientConfig.getServiceURL() + clientConfig.getResourceName();
	}

	public WebResource getResource(String service_path) {
		return Client.create().resource(getServiceResource()).path(service_path);
	}

	public WebResource getResource(String service_path, MultivaluedMap<String, String> param_map) {
		if (null != param_map) {
			return getResource(service_path).queryParams(param_map);
		}

		return getResource(service_path);
	}

	public Object getServiceResponse(ClientResponse client_resp, Class<?> clazz_name) {
		if (ClientResponse.Status.OK == client_resp.getClientResponseStatus()) {
			String respText = client_resp.getEntity(String.class);

			if (StringUtils.isNotEmpty(respText)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("=== resp: " + respText);
				}

				return JsonUtil.convertToObject(respText, clazz_name);
			}
		} else {
			LOGGER.error("=== perform http request to EBS Service error, statusCode: "
					+ client_resp.getStatus());
		}

		return null;
	}

}
