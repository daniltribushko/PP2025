package ru.tdd.backend.controller.controllers.organisations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tdd.backend.controller.controllers.RestFullController;
import ru.tdd.backend.domen.service.organisations.PostService;
import ru.tdd.backend.model.dto.DictionaryDto;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/posts")
public class PostController implements RestFullController<DictionaryDto> {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<DictionaryDto> create(DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.create(dictionaryDto));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<DictionaryDto> update(DictionaryDto dictionaryDto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(dictionaryDto));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<?> delete(Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<DictionaryDto> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getById(id));
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<DictionaryDto>> findAll(String text, Integer page, Integer perPage) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll(text, page, perPage));
    }
}
