package edu.miu.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
@Getter
public enum BucketName {
    ALUMNI("waa-alumni-bucket");
    private final String bucketName;
}
