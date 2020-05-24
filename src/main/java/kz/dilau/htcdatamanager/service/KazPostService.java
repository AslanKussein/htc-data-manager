package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import kz.dilau.htcdatamanager.web.dto.KazPostReturnDTO;

public interface KazPostService {
    KazPostReturnDTO processingData(KazPostDTO dto);

    String getPostData(String postCode);
}
