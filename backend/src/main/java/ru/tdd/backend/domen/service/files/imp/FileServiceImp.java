package ru.tdd.backend.domen.service.files.imp;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import ru.tdd.backend.domen.service.files.FileService;
import ru.tdd.backend.model.exceptions.NotFoundException;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImp implements FileService {
    @Override
    public Resource downloadFile(String filename) {
       try {
           return new UrlResource(Path.of(filename).toUri());
       } catch (Exception e) {
           throw new NotFoundException("Файл не найден") {
           };
       }
    }

    @Override
    public Boolean fileExists(String fileName) {
        return Files.exists(Path.of(fileName));
    }
}
