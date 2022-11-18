package com.insta.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private ImageTarget imageTarget;

	private String imageUrl;

	private Long targetId;

	protected Image() {}

	@Builder
	public Image(ImageTarget imageTarget, Long targetId, String imageUrl) {
		this.imageTarget = imageTarget;
		this.targetId = targetId;
		this.imageUrl = imageUrl;
	}
}