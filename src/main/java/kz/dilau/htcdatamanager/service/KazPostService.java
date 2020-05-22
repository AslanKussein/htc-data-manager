package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.domain.KazPostData;
import kz.dilau.htcdatamanager.web.dto.KazPostDTO;

public interface KazPostService {
    KazPostData processingData(KazPostDTO dto);
}
