package com.sample;

import static com.sample.Constants.*;

import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class DisposeContainerTest {


    public static void main(String[] args) throws Exception {
        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, KIE_SERVER_USER, KIE_SERVER_PASS);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ServiceResponse<Void> response1 = client.disposeContainer(CONTAINER_ID);

        System.out.println(response1);
        
    }
}
