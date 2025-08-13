package kr.inuappcenter.spotinu.domain.common.storage.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3FileStorageService {
  String uploadImage(MultipartFile file);
  void deleteImage(String imageUrl);
}
