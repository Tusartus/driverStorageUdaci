package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> getCredentialList(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, userName, key ,passWord, userid) VALUES (#{url}, #{userName}, #{key}, #{passWord}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int saveCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void deleteCredential(Integer credentialId);

    @Update("UPDATE CREDENTIALS set url = #{url}, userName = #{userName}, passWord = #{passWord} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);

    @Select("SELECT key FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    String getKeybyCredentialId(Integer credentialId);

}
