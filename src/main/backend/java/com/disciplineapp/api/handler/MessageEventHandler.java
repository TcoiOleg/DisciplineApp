package com.disciplineapp.api.handler;

import com.disciplineapp.configuration.WebSocketConfiguration;
import com.disciplineapp.api.service.UserService;
import com.disciplineapp.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OLTS on 23.09.2017.
 */
@Component
@RepositoryEventHandler(Message.class)
public class MessageEventHandler {

    private final SimpMessagingTemplate websocket;

    private final EntityLinks entityLinks;

    @Resource
    private UserService userService;

    @Autowired
    public MessageEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
    }

    /*@HandleBeforeCreate
    public void applyUserInformationUsingSecurityContext(Message message) {
        message.setMessageUser(userService.getCurrent());
    }*/

    @HandleAfterCreate
    public void newMessage(Message message) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/newMessage", getPath(message));
    }

    @HandleAfterDelete
    public void deleteMessage(Message message) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/deleteMessage", getPath(message));
    }

    @HandleAfterSave
    public void updateMessage(Message message) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/updateMessage", getPath(message));
    }

    /**
     * Take an {@link Message} and get the URI using Spring Data REST's {@link EntityLinks}.
     *
     * @param message
     */
    private String getPath(Message message) {
        return this.entityLinks.linkForSingleResource(message.getClass(),
                message.getId()).toUri().getPath();
    }

}