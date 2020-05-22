package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.KazPostData;

public interface KazPostService {
    KazPostData processingData(String jsonString);
}
