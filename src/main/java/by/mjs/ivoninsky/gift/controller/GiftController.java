package by.mjs.ivoninsky.gift.controller;

import by.mjs.ivoninsky.gift.model.CustomResponse;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.GiftService;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/v1.0/gift")
public class GiftController {
    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponse<List<GiftCertificateDto>>> allGifts(){
        List<GiftCertificateDto> allGifts = giftService.getAllGifts();
        CustomResponse<List<GiftCertificateDto>> build = CustomResponse.<List<GiftCertificateDto>>builder()
                .message(allGifts)
                .code(Status.SUCCESSFULL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @GetMapping("/{giftId}")
    public ResponseEntity<CustomResponse<GiftCertificateDto>> giftById(
            @PathVariable Long giftId
    ){
        try{
            GiftCertificateDto giftById = giftService.getGiftById(giftId);

            CustomResponse<GiftCertificateDto> build = CustomResponse.<GiftCertificateDto>builder()
                    .message(giftById)
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        }
        catch (GiftNotFoundException e){
            CustomResponse<GiftCertificateDto> body = CustomResponse.<GiftCertificateDto>builder()
                    .code(Status.GIFT_NOT_FOUND.getCode())
                    .comment(Status.GIFT_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<CustomResponse<List<GiftCertificateDto>>> searchGift(
            @RequestBody CustomSearchRequest customSearchRequest
            ){

        List<GiftCertificateDto> giftCertificateDtos = giftService.searchGifts(customSearchRequest);

        CustomResponse<List<GiftCertificateDto>> build = CustomResponse.<List<GiftCertificateDto>>builder()
                .message(giftCertificateDtos)
                .code(Status.SUCCESSFULL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/create")
    public ResponseEntity<CustomResponse> createGifts(
            @RequestBody List<GiftCertificateDto> giftCertificateDtoList
    ){
        giftService.createGift(giftCertificateDtoList);

        CustomResponse<Object> build = CustomResponse.builder()
                .code(Status.SUCCESSFULL.getCode()).build();

        return ResponseEntity.ok(build);
    }

    @PostMapping("/update")
    public ResponseEntity<CustomResponse> updateGift(
            @RequestBody GiftCertificateDto giftCertificateDto
    ) {
        giftService.updateGift(giftCertificateDto);
        CustomResponse<Object> build = CustomResponse.builder()
                .code(Status.SUCCESSFULL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @DeleteMapping("/delete/{giftId}")
    public ResponseEntity<CustomResponse> deleteGift(
            @PathVariable Long giftId
    ) {
        try{
            giftService.deleteGiftById(giftId);
            CustomResponse<Object> build = CustomResponse.builder()
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        }
        catch (GiftNotFoundException e){
            CustomResponse<GiftCertificateDto> body = CustomResponse.<GiftCertificateDto>builder()
                    .code(Status.GIFT_NOT_FOUND.getCode())
                    .comment(Status.GIFT_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }

    @PostMapping("/tags/{giftId}")
    public ResponseEntity<CustomResponse<Set<TagDto>>> getGiftTags(
            @PathVariable Long giftId
    ) {
        try {
            Set<TagDto> tagsByGiftId = giftService.getTagsByGiftId(giftId);

            CustomResponse<Set<TagDto>> build = CustomResponse.<Set<TagDto>>builder()
                    .message(tagsByGiftId)
                    .code(Status.SUCCESSFULL.getCode()).build();
            return ResponseEntity.ok(build);
        } catch (GiftNotFoundException e){
            CustomResponse<Set<TagDto>> body = CustomResponse.<Set<TagDto>>builder()
                    .code(Status.GIFT_NOT_FOUND.getCode())
                    .comment(Status.GIFT_NOT_FOUND.getMessage()).build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
    }
}
