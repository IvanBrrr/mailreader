package com.company.mailreader.service;

import com.company.mailreader.entity.Task;
import com.company.mailreader.entity.TicketsType;
import com.company.mailreader.entity.Tracker;
import com.company.mailreader.entity.TrackerPriorityType;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.List;

@Service(ReceiveEmailsService.NAME)
public class ReceiveEmailsServiceBean implements ReceiveEmailsService {
    @Inject
    private Metadata metadata;



    @Inject
    private Persistence persistence;


    @Inject
    private ImapService imapService;

    public void receive() throws MessagingException, IOException {

            List<Message> unreadMessages = imapService.getUnreadMessages();

            for (Message message : unreadMessages) {

                String subject = message.getSubject();
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                String body = getTextFromMimeMultipart(mimeMultipart);

                Tracker tracker = metadata.create(Tracker.class);
                tracker.setShortDesc(subject);
                tracker.setDescription(body);
                Task task = metadata.create(Task.class);
                task.setShortdesc("Снабжение");
                tracker.setProject(task);
                tracker.setType(TicketsType.BUG.getId());
                tracker.setTrackerPriorityType(TrackerPriorityType.CURRENT.getId());

                    try(Transaction t = persistence.createTransaction()) {
                        EntityManager em = persistence.getEntityManager();
                        em.merge(tracker);
                        t.commit();
                    }
            }
            imapService.closeConnection();
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break;
            } else if (bodyPart.isMimeType("text/html") && StringUtils.isEmpty(bodyPart.getFileName())) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }

}