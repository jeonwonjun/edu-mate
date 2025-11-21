package com.edumate.infra.gemini;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GeminiRequestBuilder {

    public String buildRequestBody(String prompt) {
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonArray partsArray = new JsonArray();
        partsArray.add(textPart);

        JsonObject contentObj = new JsonObject();
        contentObj.add("parts", partsArray);

        JsonArray contentsArray = new JsonArray();
        contentsArray.add(contentObj);

        JsonObject root = new JsonObject();
        root.add("contents", contentsArray);

        return root.toString();
    }
}
