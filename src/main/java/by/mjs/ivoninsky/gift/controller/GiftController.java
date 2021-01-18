package by.mjs.ivoninsky.gift.controller;

import by.mjs.ivoninsky.gift.model.ErrorResponse;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.GiftService;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
public class GiftController {
    private final GiftService giftService;


    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }


    @GetMapping
    //add search request too
    public ResponseEntity<List<GiftCertificateDto>> allGifts(CustomSearchRequest customSearchRequest) {
        System.out.println("CUSTOM SEARCH REQUEST " + customSearchRequest);
        List<GiftCertificateDto> allGifts;

        if (!customSearchRequest.equals(CustomSearchRequest.builder().build())){
            allGifts = giftService.searchGifts(customSearchRequest);
        }
        else {
            allGifts = giftService.getAllGifts();
        }

        return ResponseEntity.ok(allGifts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> giftById(
            @PathVariable Long id
    ) {
        GiftCertificateDto giftById = giftService.getGiftById(id);

        return ResponseEntity.ok(giftById);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> createGift(
            @RequestBody GiftCertificateDto giftCertificateDto
    ) {
        System.out.println("CREATE GIFT " + giftCertificateDto);

        GiftCertificateDto gift = giftService.createGift(giftCertificateDto);

        return ResponseEntity.ok(gift);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGift(
            @RequestBody GiftCertificateDto giftCertificateDto,
            @PathVariable Long id

    ) {
        giftCertificateDto.setId(id);

        GiftCertificateDto gift = giftService.updateGift(giftCertificateDto);

        return ResponseEntity.ok(gift);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGift(
            @PathVariable Long id
    ) {
        giftService.deleteGiftById(id);

        return ResponseEntity.ok().build();
    }

    //    @PostMapping("/search")
//    public ResponseEntity<ErrorResponse<List<GiftCertificateDto>>> searchGift(
//            @RequestBody CustomSearchRequest customSearchRequest
//    ) {
//
//        List<GiftCertificateDto> giftCertificateDtos = giftService.searchGifts(customSearchRequest);
//
//        ErrorResponse<List<GiftCertificateDto>> build = ErrorResponse.<List<GiftCertificateDto>>builder()
//                .message(giftCertificateDtos)
//                .code(Status.SUCCESSFUL.getCode()).build();
//        return ResponseEntity.ok(build);
//    }
}
