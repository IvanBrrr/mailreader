package com.company.mailreader.service;


import javax.mail.*;
import java.util.List;

public interface ImapService {


    String NAME = "mailreader_ImapService";

    List<Message> getUnreadMessages() throws MessagingException;
    public void closeConnection() throws MessagingException;
    public boolean isConnected();

}