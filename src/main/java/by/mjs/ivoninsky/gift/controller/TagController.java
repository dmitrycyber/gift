package by.mjs.ivoninsky.gift.controller;

import by.mjs.ivoninsky.gift.model.CustomResponse;
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
    public ResponseEntity<CustomResponse<List<TagDto>>> allTags(){
        List<TagDto> allTags = tagService.getAllTags();
        CustomResponse<List<TagDto>> build = CustomResponse.<List<TagDto>>builder()
                .message(allTags)
                .code(Status.SUCCESSFULL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<CustomResponse<TagDto>> tagById(
            @PathVariable Long tagId
    ){
        try{
            TagDto tagById = tagService.getTagById(tagId);

            CustomResponse<TagDto> build = CustomResponse.<TagDto>builder()
                    .message(tagById)
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (TagNotFoundException e){
            CustomResponse<TagDto> body = CustomResponse.<TagDto>builder()
                    .code(Status.TAG_NOT_FOUND.getCode())
                    .comment(Status.TAG_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<CustomResponse<List<TagDto>>> searchGift(
            @RequestBody CustomSearchRequest customSearchRequest
    ){
        List<TagDto> tagDtoList = tagService.getTagByName(customSearchRequest.getName());

        CustomResponse<List<TagDto>> build = CustomResponse.<List<TagDto>>builder()
                .message(tagDtoList)
                .code(Status.SUCCESSFULL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createGifts(
            @RequestBody List<TagDto> tagDtoList
    ){
        tagService.createTag(tagDtoList);

        CustomResponse<Object> build = CustomResponse.builder()
                .code(Status.SUCCESSFULL.getCode()).build();

        return ResponseEntity.ok(build);
    }

    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<CustomResponse> deleteGift(
            @PathVariable Long tagId
    ) {
        try {
            tagService.deleteTagById(tagId);
            CustomResponse<Object> build = CustomResponse.builder()
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (TagNotFoundException e){
            CustomResponse<TagDto> body = CustomResponse.<TagDto>builder()
                    .code(Status.TAG_NOT_FOUND.getCode())
                    .comment(Status.TAG_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @PostMapping("/tags/{tagId}")
    public ResponseEntity<CustomResponse<Set<GiftCertificateDto>>> getGiftTags(
            @PathVariable Long tagId
    ) {
        try {
            Set<GiftCertificateDto> giftsByTagId = tagService.getGiftsByTagId(tagId);

            CustomResponse<Set<GiftCertificateDto>> build = CustomResponse.<Set<GiftCertificateDto>>builder()
                    .message(giftsByTagId)
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (TagNotFoundException e){
            CustomResponse<Set<GiftCertificateDto>> body = CustomResponse.<Set<GiftCertificateDto>>builder()
                    .code(Status.TAG_NOT_FOUND.getCode())
                    .comment(Status.TAG_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }
}
