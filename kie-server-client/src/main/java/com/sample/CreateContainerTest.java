package com.sample;


import static com.sample.Constants.*;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieScannerResource;
import org.kie.server.api.model.KieScannerStatus;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class CreateContainerTest {

	public static void main(String[] args) {
		KieServicesConfiguration config = KieServicesFactory
				.newRestConfiguration(
				        BASE_URL,
						KIE_SERVER_USER, KIE_SERVER_PASS);
		
		config.setTimeout(60000L);
		KieServicesClient client = KieServicesFactory
				.newKieServicesClient(config);
		
        ReleaseId releaseId = new ReleaseId(GROUP_ID, ARTIFACT_ID, VERSION);
        KieContainerResource resource = new KieContainerResource(CONTAINER_ID, releaseId);
        KieScannerResource scanner = new KieScannerResource(KieScannerStatus.STARTED, 10000L);
        resource.setScanner(scanner);
		ServiceResponse<KieContainerResource> response = client.createContainer(CONTAINER_ID, resource);

		System.out.println(response);
	}
}
