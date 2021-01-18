package by.mjs.ivoninsky.gift.dao;

import by.mjs.ivoninsky.gift.dao.exception.DaoException;
import by.mjs.ivoninsky.gift.model.dto.GiftCertificateDto;
import by.mjs.ivoninsky.gift.model.dto.TagDto;
import by.mjs.ivoninsky.gift.model.entity.GiftCertificateEntity;
import by.mjs.ivoninsky.gift.model.entity.TagEntity;

import java.util.List;
import java.util.Set;

public interface TagDao {
    List<TagEntity> findAllTags() throws DaoException;
    TagEntity findTagById(Long tagId) throws DaoException;
    List<TagEntity> findTagByName(String tagName) throws DaoException;
    List<TagEntity> findTagByPartName(String tagName) throws DaoException;

    TagEntity createTag(TagEntity tagEntity) throws DaoException;
    void deleteTagById(Long tagId) throws DaoException;


}
