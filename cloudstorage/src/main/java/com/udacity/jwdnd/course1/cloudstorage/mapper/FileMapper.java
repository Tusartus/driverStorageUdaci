package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFileList(Integer userid);

    @Insert("INSERT INTO FILES (fileName, fileSize, fileData ,contentType, userid) VALUES (#{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int saveFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFilebyFileId(int fileId);

    @Select("SELECT fileName FROM FILES WHERE userid = #{userid}")
    List<String> getFileNamebyUserId(Integer userid);

}