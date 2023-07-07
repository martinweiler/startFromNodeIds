package com.sample;

import static com.sample.Constants.*;

import java.util.List;

import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;

public class StartHTProcess {

    public static void main(String[] args) {

        KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(BASE_URL, KIE_SERVER_USER, KIE_SERVER_PASS);
        KieServicesClient client = KieServicesFactory.newKieServicesClient(config);

        ProcessServicesClient processClient = client.getServicesClient(ProcessServicesClient.class);
        UserTaskServicesClient taskClient = client.getServicesClient(UserTaskServicesClient.class);
        
        // start a process
        Long startProcess = processClient.startProcess(CONTAINER_ID, HT_PROCESS);
        System.out.println("Process " + HT_PROCESS + " started with id: " + startProcess);


        // complete the task
        List<org.kie.server.api.model.instance.TaskSummary> taskList;
        taskList = taskClient.findTasksAssignedAsPotentialOwner("rhpamAdmin", 0, 100);
        for (org.kie.server.api.model.instance.TaskSummary taskSummary : taskList) {
            System.out.println("taskSummary.getId() = " + taskSummary.getId() + ", staus = " + taskSummary.getStatus());
            long taskId = taskSummary.getId();
            if (taskSummary.getStatus().equals("Reserved")) {
                taskClient.startTask(CONTAINER_ID, taskId, "rhpamAdmin");
            }
            taskClient.completeTask(CONTAINER_ID, taskId, "rhpamAdmin", null);
        }

        // abort the process
        processClient.abortProcessInstance(CONTAINER_ID, startProcess);

        List<NodeInstance> nodeList = processClient.findNodeInstancesByType(CONTAINER_ID, startProcess, "ABORTED", 0, 10);
        String[] nodeIds = nodeList.stream().map(NodeInstance::getNodeId).toArray(String[]::new);

        System.out.println("Found nodes: " + nodeIds);

        Long newProcessId = processClient.startProcessFromNodeIds(CONTAINER_ID, HT_PROCESS, null, nodeIds);
        System.out.println("Process " + HT_PROCESS + " started with id: " + newProcessId + " from node " + nodeIds);

    }
}
