package kr.inuappcenter.spotinu.domain.common.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import kr.inuappcenter.spotinu.domain.common.storage.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static kr.inuappcenter.spotinu.domain.common.storage.exception.StorageErrorCode.S3_DELETE_FAIL;
import static kr.inuappcenter.spotinu.domain.common.storage.exception.StorageErrorCode.S3_UPLOAD_FAIL;

@Service
@RequiredArgsConstructor
public class S3FileStorageServiceImpl implements S3FileStorageService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Override
  public String uploadImage(MultipartFile file) {
    String key = "images/" + UUID.randomUUID();

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(file.getSize());
    metadata.setContentType(file.getContentType());

    try {
      amazonS3.putObject(bucket, key, file.getInputStream(), metadata);
    } catch (IOException e) {
      throw new StorageException(S3_UPLOAD_FAIL);
    }

    return amazonS3.getUrl(bucket, key).toString();
  }

  @Override
  public void deleteImage(String imageUrl) {
    try {
      // imageUrl에서 key 추출 (예: https://bucket.s3.../images/abc.jpg → images/abc.jpg)
      String key = extractKeyFromUrl(imageUrl);
      amazonS3.deleteObject(bucket, key);
    } catch (Exception e) {
      throw new StorageException(S3_DELETE_FAIL);
    }
  }

  private String extractKeyFromUrl(String url) {
    // 버킷 도메인 이후 경로만 남김
    int index = url.indexOf(bucket);
    if (index == -1) {
      throw new IllegalArgumentException("유효하지 않은 S3 URL입니다.");
    }
    return url.substring(url.indexOf("/", index) + 1);
  }
}
