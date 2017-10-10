package com.disciplineapp.rest.dto;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * OLTS on 16.09.2017.
 */
@Data
public class ChallengeDto extends ResourceSupport {

    private String name;

    private int difficulty;

    private String description;

    private Date challengeDate;

    private String sphere;

    private String type;

    private int votes;

    private int acceptedCount;

    private int completedCount;

    private boolean withCreator;

    private boolean voteableForCurrentUser;

    private boolean acceptableForCurrentUser;

    private Date createdWhen;

    private Date updatedWhen;
}
