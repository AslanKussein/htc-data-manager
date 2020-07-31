package kz.dilau.htcdatamanager.service;

import kz.dilau.htcdatamanager.web.dto.RealPropertyAnalyticsDto;
import kz.dilau.htcdatamanager.web.dto.ResultDto;

public interface AnalyticsService {
    ResultDto saveAnalytics(RealPropertyAnalyticsDto realPropertyAnalyticsDto);

    RealPropertyAnalyticsDto getAnalytics(Long appId);
}
