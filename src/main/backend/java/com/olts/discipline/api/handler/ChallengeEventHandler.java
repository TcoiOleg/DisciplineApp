package com.olts.discipline.api.handler;

import com.olts.discipline.api.service.UserService;
import com.olts.discipline.entity.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.olts.discipline.configuration.WebSocketConfiguration.MESSAGE_PREFIX;

/**
 * OLTS on 17.09.2017.
 */
@Component
@RepositoryEventHandler(Challenge.class)
public class ChallengeEventHandler {

    private final SimpMessagingTemplate websocket;

    private final EntityLinks entityLinks;

    @Resource
    private UserService userService;

    @Autowired
    public ChallengeEventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void applyUserInformationUsingSecurityContext(Challenge challenge) {
        challenge.setCreatedBy(userService.getCurrent());
    }

    @HandleAfterCreate
    public void newChallenge(Challenge challenge) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/newChallenge", getPath(challenge));
    }

    @HandleAfterDelete
    public void deleteChallenge(Challenge challenge) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/deleteChallenge", getPath(challenge));
    }

    @HandleAfterSave
    public void updateChallenge(Challenge challenge) {
        this.websocket.convertAndSend(
                MESSAGE_PREFIX + "/updateChallenge", getPath(challenge));
    }

    /**
     * Take an {@link Challenge} and get the URI using Spring Data REST's {@link EntityLinks}.
     *
     * @param challenge
     */
    private String getPath(Challenge challenge) {
        return this.entityLinks.linkForSingleResource(challenge.getClass(),
                challenge.getId()).toUri().getPath();
    }

}