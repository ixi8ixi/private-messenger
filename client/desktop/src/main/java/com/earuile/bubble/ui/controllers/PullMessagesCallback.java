package com.earuile.bubble.ui.controllers;

import com.earuile.bubble.rest.dto.model.MessageModel;

import java.util.List;

public interface PullMessagesCallback {
    void addMessages(List<MessageModel> newMessages);
}
