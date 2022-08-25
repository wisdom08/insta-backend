package com.insta.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.insta.global.error.exception.EntityNotFoundException;
import com.insta.global.error.exception.ErrorCode;
import com.insta.model.Image;
import com.insta.model.ImageTarget;
import com.insta.repository.ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
	private final ImageRepo imageRepo;

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadFile(MultipartFile[] files, String targetDirectory, Long targetId) {
		ObjectMetadata omd = new ObjectMetadata();
		for (MultipartFile file : files) {
			String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.KOREA))
					+ "_" + Objects.requireNonNull(file.getOriginalFilename()).toLowerCase();
			if(!(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith("jpeg"))) {
				throw new EntityNotFoundException(ErrorCode.UNSUPPORTED_FILE_FORMAT);
			}
			omd.setContentType(file.getContentType());
			omd.setContentLength(file.getSize());
			omd.setHeader("filename", file.getOriginalFilename());
			try {
				amazonS3.putObject(new PutObjectRequest(bucket + "/" + targetDirectory,
						fileName, file.getInputStream(), omd)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			} catch (IOException e) {
				throw new EntityNotFoundException(ErrorCode.CONVERTING_FAILED);
			}
			String imagePath = amazonS3.getUrl(bucket + "/" + targetDirectory, fileName).toString();
			Image image = Image.builder()
					.imageTarget(ImageTarget.valueOf(targetDirectory))
					.targetId(targetId)
					.imageUrl(imagePath)
					.build();
			imageRepo.save(image);
		}
	}

	public void deleteFile(ImageTarget target, Long targetId) {
		List<Image> images = imageRepo.findAllByTargetId(target, targetId);
		if(images.size() != 0) {
			for (Image image : images) {
				String url = image.getImageUrl();
				amazonS3.deleteObject(bucket, url.split(bucket + "/", 2)[1]);
			}
		}
		imageRepo.deleteAllByTargetId(target, targetId);
	}

	public Image getImage(ImageTarget target, Long targetId) {
		return imageRepo.findByTargetId(target, targetId);
	}

	public List<Image> getImages(ImageTarget target, Long targetId) {
		return imageRepo.findAllByTargetId(target, targetId);
	}
}