package kz.dilau.htcdatamanager.service;


import kz.dilau.htcdatamanager.domain.Application;
import kz.dilau.htcdatamanager.domain.Event;

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

    void createCompletedEventRelatedApplication(Event event);

    void createCompletedLinkedTicketApplication(Application application);

}
