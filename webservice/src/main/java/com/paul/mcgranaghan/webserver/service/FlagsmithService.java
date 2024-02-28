package com.paul.mcgranaghan.webserver.service;


import com.flagsmith.FlagsmithClient;
import com.flagsmith.config.FlagsmithCacheConfig;
import com.flagsmith.exceptions.FlagsmithClientError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class FlagsmithService {

    @Value("${spring.flagsmith.URI}")
    public String flagsmithURI;

    public FlagsmithClient getFeatureFlagClient() throws FlagsmithClientError {
        return FlagsmithClient
                .newBuilder()
                .setApiKey("ser.DwYEsm4yDLnwuEe79KVtGh")
                .withCache(FlagsmithCacheConfig.newBuilder()
                        .maxSize(100)
                        .expireAfterAccess(10, TimeUnit.SECONDS)
                        .expireAfterWrite(10, TimeUnit.SECONDS)
                        .recordStats()
                        .build())
                .withApiUrl(flagsmithURI)
                .build();
    }

}
