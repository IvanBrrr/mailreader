package com.company.mailreader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service(ImapService.NAME)
public class ImapServiceBean implements ImapService {


    private final Session session;
    @Value("${mail.proto}")
    private final String proto;
    @Value("${imap.service.name}")
    private final String username;
    @Value("${imap.service.password}")
    private final String password;

    @Value("${imap.service.server}")
    private final String server;
    @Value("${imap.service.port}")
    private final Integer port;

    private Store store;
    private Folder inbox;

    public ImapServiceBean(
    ) {
        Properties props = new Properties();
            props.put("mail.imaps.ssl.trust", "*");


        session = Session.getDefaultInstance(props);
/*
        imap.service.server = imap.mail.ru
        imap.service.port = 993
        imap.service.name = aasdfsfad@list.ru
                imap.service.password = cieBae5e
        mail.proto=imaps
        mail.imap.ignore.invalid.ssl=true
*/
this.proto = "imaps";
this.server = "imap.mail.ru";
this.port = 993;
this.username = "aasdfsfad@list.ru";
this.password = "cieBae5e";

    }

    /**
     * Получение непрочитанных писем с сервера
     *
     * @return список непрочитанных писем
     * @throws MessagingException ошибка работы с почтовым сервером
     */
    public List<Message> getUnreadMessages() throws MessagingException {
        validateConnection();
        List<Message> result = new ArrayList<>();
        Message[] unreadMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        for (Message message : unreadMessages) {
            result.add(message);
            message.setFlag(Flags.Flag.SEEN, true);
        }

        return result;
    }

    /**
     * Закрытие соединения
     *
     * @throws MessagingException ошибка работы с почтовым сервером
     */
    public void closeConnection() throws MessagingException {
        if (store != null && store.isConnected()) {
            store.close();
            store = null;
        }
    }


    /**
     * Вычисление статуса соедиения
     *
     */
    public boolean isConnected() {
        return (store != null && store.isConnected());
    }

    private void validateConnection() throws MessagingException {
        if (store == null) {
            store = session.getStore(proto);
        }

        if (!store.isConnected()) {
            store.connect(server, port, username, password);
        }

        if (inbox == null) {
            inbox = store.getFolder("inbox");
        }

        if (!inbox.isOpen()) {
            inbox.open(Folder.READ_WRITE);
        }
    }

    public String getServer() {
        return server;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

}