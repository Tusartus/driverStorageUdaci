package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    public int saveFile(MultipartFile file, Integer userid) throws IOException {
        return fileMapper.saveFile(new File (0,file.getOriginalFilename(),file.getContentType(), file.getSize(), userid,file.getBytes()));
    }

    public List<File> getFileList(Integer userid){
        return fileMapper.getFileList(userid);
    }

    public void deleteFile(int fileId) {
        fileMapper.deleteFile(fileId);
    }

    public File getFilebyFileId(int fileId){
        return fileMapper.getFilebyFileId(fileId);
    }

    public boolean isFileNameDuplicate(String fileName, Integer userid){
        List<String> fileNameList = fileMapper.getFileNamebyUserId(userid);
        return fileNameList.contains(fileName);

    }

}
