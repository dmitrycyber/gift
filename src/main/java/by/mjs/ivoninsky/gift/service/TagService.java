package by.mjs.ivoninsky.gift.service;

import by.mjs.ivoninsky.gift.dao.TagDao;
import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.service.exception.ServiceException;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<TagDto> getAllTags() throws ServiceException;
    TagDto getTagById(Long tagId) throws ServiceException;
    List<TagDto> getTagByName(String tagName) throws ServiceException;
    List<TagDto> getTagByPartName(String tagName) throws ServiceException;

    TagDto createTag(TagDto tagDto) throws ServiceException;
    void deleteTagById(Long tagId) throws ServiceException;
}
