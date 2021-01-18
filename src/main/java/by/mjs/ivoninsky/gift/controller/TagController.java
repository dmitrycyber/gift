package by.mjs.ivoninsky.gift.controller;

import by.mjs.ivoninsky.gift.model.ErrorResponse;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.TagService;
import by.mjs.ivoninsky.gift.dao.exception.TagNotFoundException;
import by.mjs.ivoninsky.gift.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> allTags(@RequestParam(required = false) String name) {
        List<TagDto> tags;

        if (name != null){
            tags = tagService.getTagByPartName(name);
        }else {
            tags = tagService.getAllTags();
        }
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> tagById(
            @PathVariable Long id
    ) {
        TagDto tagById = tagService.getTagById(id);
        return ResponseEntity.ok(tagById);
    }

    @PostMapping("/{id}")
    public ResponseEntity<TagDto> create(
            @RequestBody TagDto tagDto
    ) {
        TagDto tag = tagService.createTag(tagDto);

        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorResponse> deleteGift(
            @PathVariable Long id
    ) {
        tagService.deleteTagById(id);
        return ResponseEntity.ok().build();
    }
}
