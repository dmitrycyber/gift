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
@RequestMapping("/api/v1.0/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/all")
    public ResponseEntity<ErrorResponse<List<TagDto>>> allTags(){
        List<TagDto> allTags = tagService.getAllTags();
        ErrorResponse<List<TagDto>> build = ErrorResponse.<List<TagDto>>builder()
                .message(allTags)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ErrorResponse<TagDto>> tagById(
            @PathVariable Long tagId
    ){
        try{
            TagDto tagById = tagService.getTagById(tagId);

            ErrorResponse<TagDto> build = ErrorResponse.<TagDto>builder()
                    .message(tagById)
                    .code(Status.SUCCESSFUL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (TagNotFoundException e){
            ErrorResponse<TagDto> body = ErrorResponse.<TagDto>builder()
                    .code(Status.TAG_NOT_FOUND.getCode())
                    .comment(Status.TAG_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ErrorResponse<List<TagDto>>> searchGift(
            @RequestBody CustomSearchRequest customSearchRequest
    ){
        List<TagDto> tagDtoList = tagService.getTagByName(customSearchRequest.getName());

        ErrorResponse<List<TagDto>> build = ErrorResponse.<List<TagDto>>builder()
                .message(tagDtoList)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/create")
    public ResponseEntity<ErrorResponse> create(
            @RequestBody TagDto tagDtoList
    ){
        tagService.createTag(tagDtoList);

        ErrorResponse<Object> build = ErrorResponse.builder()
                .code(Status.SUCCESSFUL.getCode()).build();

        return ResponseEntity.ok(build);
    }

    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<ErrorResponse> deleteGift(
            @PathVariable Long tagId
    ) {
        try {
            tagService.deleteTagById(tagId);
            ErrorResponse<Object> build = ErrorResponse.builder()
                    .code(Status.SUCCESSFUL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (TagNotFoundException e){
            ErrorResponse<TagDto> body = ErrorResponse.<TagDto>builder()
                    .code(Status.TAG_NOT_FOUND.getCode())
                    .comment(Status.TAG_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }
}
