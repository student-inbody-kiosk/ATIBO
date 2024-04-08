package com.atibo.backendspring.util;

import java.util.List;

import com.atibo.backendspring.accounts.domain.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {
    public static String listsToJson(List<Account> actives, List<Account> inActives) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode combinedJson = mapper.createObjectNode();

        ArrayNode inactiveArray = mapper.valueToTree(inActives);
        combinedJson.set("inactiveUsers", inactiveArray);

        ArrayNode activeArray = mapper.valueToTree(actives);
        combinedJson.set("activeUsers", activeArray);

        try {
            return mapper.writeValueAsString(combinedJson);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
