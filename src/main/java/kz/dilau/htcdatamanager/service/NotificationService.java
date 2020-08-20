package kz.dilau.htcdatamanager.service;


public interface NotificationService {

    void createNotesNotification(Long applicationId, Long notesId);

    void createNotesAnswerNotification(Long applicationId, Long notesId);

    void createBuyApplicationNotification(Long applicationId);

    void createSellApplicationNotification(Long applicationId);

    void createIpotekaNotification(Long applicationId, Long eventId);

    void createBookingViewNotification(Long applicationId, Long eventId);

    void createBookingPropertyNotification(Long sellApplicationId, Long buyApplicationId);

    void createBuyNowNotification(Long sellApplicationId, Long buyApplicationId);

    void createApplicationAssignedNotification(Long applicationId);

    void createApplicationDealClosingApproval(Long applicationId1, String statusChangedAgent);

    void createCompletedEventRelatedApplication(Long applicationId1);

    void createCompletedLinkedTicketApplication(Long applicationId1);

}
