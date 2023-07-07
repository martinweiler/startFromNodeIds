package com.sample;

import static com.sample.Constants.*;

import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;

public class SimpleAbort {


    
    public static void main(String[] args) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, KIE_SERVER_USER, KIE_SERVER_PASS);

        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient proessClient = client.getServicesClient(ProcessServicesClient.class);

        proessClient.abortProcessInstance(CONTAINER_ID, 1L);

    }
}
