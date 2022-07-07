package edu.miu.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BucketName {
    ALUMNI("waa-alumni");
    private final String bucketName;
}
