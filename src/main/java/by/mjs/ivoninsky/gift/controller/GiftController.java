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
import java.util.List;
import java.util.Set;

//rest controller
@Controller
@RequestMapping("/api/v1/gifts")
//@RestController()
public class GiftController {
    private final GiftService giftService;
    private final ObjectMapper objectMapper;

    @Autowired
    public GiftController(GiftService giftService, ObjectMapper objectMapper) {
        this.giftService = giftService;
        this.objectMapper = objectMapper;
    }


    @ExceptionHandler(GiftNotFoundException.class)
    @SneakyThrows
    public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        System.out.println("AT URI: " + request.getRequestURI() + " HANDLE EXCEPTION: " + ex);

        ErrorResponse<GiftCertificateDto> body = ErrorResponse.<GiftCertificateDto>builder()
                .code(Status.GIFT_NOT_FOUND.getCode())
                .comment(Status.GIFT_NOT_FOUND.getMessage()).build();

        response.setStatus(HttpStatus.INSUFFICIENT_STORAGE.value());
        response.getWriter().write(objectMapper.writeValueAsString(body));
        return new ModelAndView();
    }


    @GetMapping("")
    //remove custom response
    //add search request too
    public ResponseEntity<ErrorResponse<List<GiftCertificateDto>>> allGifts() {
        List<GiftCertificateDto> allGifts = giftService.getAllGifts();
        ErrorResponse<List<GiftCertificateDto>> build = ErrorResponse.<List<GiftCertificateDto>>builder()
                .message(allGifts)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ErrorResponse<GiftCertificateDto>> giftById(
            @PathVariable Long id
    ) {
        GiftCertificateDto giftById = giftService.getGiftById(id);

        ErrorResponse<GiftCertificateDto> build = ErrorResponse.<GiftCertificateDto>builder()
                .message(giftById)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/search")
    public ResponseEntity<ErrorResponse<List<GiftCertificateDto>>> searchGift(
            @RequestBody CustomSearchRequest customSearchRequest
    ) {

        List<GiftCertificateDto> giftCertificateDtos = giftService.searchGifts(customSearchRequest);

        ErrorResponse<List<GiftCertificateDto>> build = ErrorResponse.<List<GiftCertificateDto>>builder()
                .message(giftCertificateDtos)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    //save one gift
    @PostMapping("/create")
    public ResponseEntity<ErrorResponse> createGifts(
            @RequestBody List<GiftCertificateDto> giftCertificateDtoList
    ) {
        giftService.createGift(giftCertificateDtoList);

        ErrorResponse<Object> build = ErrorResponse.builder()
                .code(Status.SUCCESSFUL.getCode()).build();

        return ResponseEntity.ok(build);
    }

    @PostMapping("/update")
    public ResponseEntity<ErrorResponse> updateGift(
            @RequestBody GiftCertificateDto giftCertificateDto
    ) {
        giftService.updateGift(giftCertificateDto);
        ErrorResponse<Object> build = ErrorResponse.builder()
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @DeleteMapping("/{giftId}")
    public ResponseEntity<ErrorResponse> deleteGift(
            @PathVariable Long giftId
    ) {
        giftService.deleteGiftById(giftId);
        ErrorResponse<Object> build = ErrorResponse.builder()
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }

    @PostMapping("/tags/{giftId}")
    public ResponseEntity<ErrorResponse<Set<TagDto>>> getGiftTags(
            @PathVariable Long giftId
    ) {
        Set<TagDto> tagsByGiftId = giftService.getTagsByGiftId(giftId);

        ErrorResponse<Set<TagDto>> build = ErrorResponse.<Set<TagDto>>builder()
                .message(tagsByGiftId)
                .code(Status.SUCCESSFUL.getCode()).build();
        return ResponseEntity.ok(build);
    }
}
