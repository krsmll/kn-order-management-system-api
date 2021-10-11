package com.example.ordermanagementsystemapi.api.responses;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Messages {
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void addMessages(List<String> messages) {
        this.messages.addAll(messages);
    }
}
