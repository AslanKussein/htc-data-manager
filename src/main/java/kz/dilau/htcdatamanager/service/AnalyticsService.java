package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.AnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;

public interface AnalyticsService {
    ResultDto saveAnalytics(AnalyticsDto analyticsDto);
}
