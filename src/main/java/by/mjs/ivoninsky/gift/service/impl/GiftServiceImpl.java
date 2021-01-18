package by.mjs.ivoninsky.gift.service.impl;

import by.mjs.ivoninsky.gift.dao.GiftDao;
import by.mjs.ivoninsky.gift.dao.GiftTagDao;
import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.GiftNotFoundException;
import by.mjs.ivoninsky.gift.model.CustomSearchRequest;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.GiftTagEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;
import by.mjs.ivoninsky.gift.service.GiftService;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;
import by.mjs.ivoninsky.gift.util.converter.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GiftServiceImpl implements GiftService {
    private final GiftDao giftDao;
    private final TagDao tagDao;
    private final GiftTagDao giftTagDao;

    @Autowired
    public GiftServiceImpl(@Qualifier("giftDaoImpl") GiftDao giftDao, @Qualifier("tagDaoImpl") TagDao tagDao, GiftTagDao giftTagDao) {
        this.giftDao = giftDao;
        this.tagDao = tagDao;
        this.giftTagDao = giftTagDao;
    }


    @Override
    public List<GiftCertificateDto> getAllGifts() throws ServiceException {
        List<GiftCertificateDto> giftCertificateDtoList = giftDao.findAllGifts().stream()
                .map(EntityConverter::convertGiftEntityDto)
                .collect(Collectors.toList());

        return giftCertificateDtoList;
    }

    @Override
    public GiftCertificateDto getGiftById(Long giftId) throws ServiceException {
        return EntityConverter.convertGiftEntityDto(giftDao.findGiftById(giftId));
    }

    @Override
    public List<GiftCertificateDto> searchGifts(CustomSearchRequest customSearchRequest) throws ServiceException {
        List<GiftCertificateEntity> giftList = new ArrayList<>();

        if (customSearchRequest.getName() != null){
            giftList = giftDao.findGiftByName(customSearchRequest);

        }
        if (customSearchRequest.getDurationFrom() != null && customSearchRequest.getDurationTo() != null){
            giftList = giftDao.findGiftByDuration(customSearchRequest);
        }
        if (customSearchRequest.getPriceFrom() != null && customSearchRequest.getPriceTo() != null){
            giftList = giftDao.findGiftByPrice(customSearchRequest);
        }

        return giftList.stream()
                .map(EntityConverter::convertGiftEntityDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto) throws ServiceException {

        GiftCertificateEntity giftEntity = giftDao.createGift(EntityConverter.convertGiftDtoEntity(giftCertificateDto));

        Set<TagEntity> tags = giftEntity.getTags();
        if (tags != null){
            for (TagEntity tag : tags){

                TagEntity tagEntity = tagDao.createTag(tag);
                tag.setId(tagEntity.getId());

                GiftTagEntity giftTagEntity = GiftTagEntity.builder()
                        .giftId(giftEntity.getId())
                        .tagId(tagEntity.getId()).build();

                giftTagDao.insertNewGiftTagEntity(giftTagEntity);
            }
        }

        return EntityConverter.convertGiftEntityDto(giftEntity);
    }

    @Override
    public GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto) throws ServiceException {
        GiftCertificateEntity entity = giftDao.updateGift(EntityConverter.convertGiftDtoEntity(giftCertificateDto));

        return EntityConverter.convertGiftEntityDto(entity);
    }

    @Override
    public void deleteGiftById(Long giftId) throws ServiceException {
        giftDao.deleteGiftById(giftId);
    }
}
