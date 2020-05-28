package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.KazPostDTO;
import kz.dilau.htcdatamanager.web.dto.KazPostReturnDTO;

public interface KazPostService {
    KazPostReturnDTO processingData(KazPostDTO dto);

    KazPostDTO getPostData(String postCode);
}
