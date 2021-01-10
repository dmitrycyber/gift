package by.mjs.ivoninsky.gift.dao;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;

import java.util.List;
import java.util.Set;

public interface TagDao {
    List<TagDto> findAllTags() throws DaoException;
    TagDto findTagById(Long tagId) throws DaoException;
    List<TagDto> findTagByName(String tagName) throws DaoException;
    void createTag(List<TagDto> giftCertificateDtoList) throws DaoException;
    void deleteTagById(Long tagId) throws DaoException;

    Set<GiftCertificateDto> findGiftsByTagId(Long tagId) throws DaoException;
}
