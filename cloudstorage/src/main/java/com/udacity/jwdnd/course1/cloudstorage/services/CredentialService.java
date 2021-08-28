package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassWord(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassWord(encryptedPassword);
        return credentialMapper.saveCredential(credential);
    }

    public int updateCredential(Credential credential){
        String encodedKey = credentialMapper.getKeybyCredentialId(credential.getCredentialId());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassWord(), encodedKey);
        credential.setPassWord(encryptedPassword);
        return credentialMapper.updateCredential(credential);
    }

    public List<Credential> getCredEncListbyUser(Integer userid){
        return credentialMapper.getCredentialList(userid);
    }

    public void deleteCredential(Integer credentialId){
        credentialMapper.deleteCredential(credentialId);
    }


}
