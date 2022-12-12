package com.cburcea.playground.camunda;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

public class CamundaApplication {
    private static final HashMap<String, Boolean> visited = new HashMap<>();
    private static final Stack<String> path = new Stack<>();

    private static class ProcessDefinition implements Serializable {
        String id;
        String bpmn20Xml;

        public String getId() {
            return id;
        }

        public String getBpmn20Xml() {
            return bpmn20Xml;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setBpmn20Xml(String bpmn20Xml) {
            this.bpmn20Xml = bpmn20Xml;
        }
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        ProcessDefinition result = restTemplate.getForObject("https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml", ProcessDefinition.class);

        InputStream inputStream = new ByteArrayInputStream(result.bpmn20Xml.getBytes());
        BpmnModelInstance modelInstance = Bpmn.readModelFromStream(inputStream);

        FlowNode flowNodeStart = modelInstance.getModelElementById(args[0]);
        FlowNode flowNodeEnd = modelInstance.getModelElementById(args[1]);

        if (flowNodeStart == null) {
            System.out.println("Start node is invalid");
            System.exit(-1);
        }

        if (flowNodeEnd == null) {
            System.out.println("End node is invalid");
            System.exit(-1);
        }

        visited.put(flowNodeStart.getId(), true);
        path.push(flowNodeStart.getId());

        boolean found = findPathBetweenTwoNodes(flowNodeStart, flowNodeEnd);
        if (!found) {
            System.out.println("No path found");
            System.exit(-1);
        }

        System.out.printf("The path from %s to %s is: %s", args[0], args[1], path);
    }

    private static boolean findPathBetweenTwoNodes(FlowNode flowNodeStart, FlowNode flowNodeEnd) {
        Collection<SequenceFlow> outgoing = flowNodeStart.getOutgoing();
        for (SequenceFlow sequenceFlow : outgoing) {
            String targetId = sequenceFlow.getTarget().getId();
            if (!visited.getOrDefault(targetId, false)) {
                path.push(targetId);
                visited.put(flowNodeStart.getId(), true);
                if (targetId.equals(flowNodeEnd.getId())) {
                    return true;
                }
                boolean found = findPathBetweenTwoNodes(sequenceFlow.getTarget(), flowNodeEnd);
                if (found) {
                    return true;
                }
                path.pop();
            }
        }
        return false;
    }
}
