package by.bsu.rikz.service;

import by.bsu.rikz.entity.TestInfoContext;

/**
 * Created by USER on 14.12.2016.
 */
public interface MailService {

    void sendNotification(TestInfoContext assignment);
    void sendResult(TestInfoContext assignment);

}
