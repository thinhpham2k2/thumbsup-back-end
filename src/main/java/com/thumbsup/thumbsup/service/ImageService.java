package com.thumbsup.thumbsup.service;

import com.thumbsup.thumbsup.repository.ImageRepository;
import com.thumbsup.thumbsup.service.interfaces.IImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
}
