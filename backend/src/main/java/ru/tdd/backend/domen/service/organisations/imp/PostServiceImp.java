package ru.tdd.backend.domen.service.organisations.imp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tdd.backend.controller.repositories.organisations.PostPagingRepository;
import ru.tdd.backend.controller.repositories.organisations.PostRepository;
import ru.tdd.backend.domen.service.organisations.PostService;
import ru.tdd.backend.model.dto.DictionaryDto;
import ru.tdd.backend.model.entities.organisations.Post;
import ru.tdd.backend.model.exceptions.organisations.PostAlreadyExistsException;
import ru.tdd.backend.model.exceptions.organisations.PostByIdNotFoundException;

import java.util.List;

@Service
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final PostPagingRepository postPagingRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository, PostPagingRepository postPagingRepository) {
        this.postRepository = postRepository;
        this.postPagingRepository = postPagingRepository;
    }

    @Override
    @Transactional
    public DictionaryDto create(@Valid DictionaryDto dictionaryDto) {
        String name = dictionaryDto.getName();
        if (postRepository.existsByName(name)) {
            throw new PostAlreadyExistsException(name);
        }

        Post post = new Post();
        post.setName(name);
        post.setDescription(dictionaryDto.getDescription());

        return postRepository.save(post)
                .toDto();
    }

    @Override
    public List<DictionaryDto> findAll(String text, Integer page, Integer perPage) {
        return postPagingRepository.findAllByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByName(
                        text,
                        text,
                        PageRequest.of(page, perPage)
                ).stream()
                .map(Post::toDto)
                .toList();
    }

    @Override
    public DictionaryDto getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostByIdNotFoundException(id)).toDto();
    }

    @Override
    @Transactional
    public DictionaryDto update(DictionaryDto dictionaryDto) {
        Post post = postRepository.findById(dictionaryDto.getId())
                .orElseThrow(() -> new PostByIdNotFoundException(dictionaryDto.getId()));
        String name = dictionaryDto.getName();
        if (name != null) {
            if (postRepository.existsByName(name)) {
                throw new PostAlreadyExistsException(name);
            } else {
                post.setName(name);
            }
        }
        post.setDescription(dictionaryDto.getDescription());

        return postRepository.save(post).toDto();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow(() -> new PostByIdNotFoundException(id)));
    }
}
