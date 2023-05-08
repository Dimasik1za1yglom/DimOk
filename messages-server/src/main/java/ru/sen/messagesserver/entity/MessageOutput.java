package ru.sen.messagesserver.entity;

import lombok.Data;

@Data
public class MessageOutput {

    /**
     * message user
     */
    private Message message;

    /**
     * first name and last name user
     */
    private String userName;
}
