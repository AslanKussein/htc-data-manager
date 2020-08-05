package kz.dilau.htcdatamanager.service;


public interface NotificationService {

    void createNotesNotification(Long applicationId, Long notesId);

    void createNotesAnswerNotification(Long applicationId, Long notesId);

    void createBuyApplicationNotification(Long applicationId);

    void createSellApplicationNotification(Long applicationId);

    void createIpotekaNotification(Long applicationId, Long eventId);

    void createBookingViewNotification(Long applicationId, Long eventId);

    void createBookingPropertyNotification(Long applicationId);

    void createBuyNowNotification(Long applicationId);


}
