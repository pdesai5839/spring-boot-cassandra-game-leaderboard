package com.desai.demo.cassandra.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@UserDefinedType
public class GameInfo implements Serializable {
    private static final long serialVersionUID = -6145830055166020067L;

    private String gameName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int maxPlayersAllowed;
}
