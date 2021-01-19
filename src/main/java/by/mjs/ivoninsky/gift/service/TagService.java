package by.mjs.ivoninsky.gift.service;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagDto> getAllTags();
    TagDto getTagById(Long tagId);
    List<TagDto> getTagByName(String tagName);
    List<TagDto> getTagByPartName(String tagName);

    TagDto createTag(TagDto tagDto);
    void deleteTagById(Long tagId);
}
