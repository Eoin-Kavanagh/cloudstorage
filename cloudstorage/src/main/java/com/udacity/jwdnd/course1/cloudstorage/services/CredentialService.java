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
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encodedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        //String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedKey);
        return this.credentialMapper.insertCredential(new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encodedPassword, credential.getUserId()));
    }

    public int updateCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(hashedPassword);
        return this.credentialMapper.updateCredential(credential);
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer userId) {
        return this.credentialMapper.getAllCredentials(userId);
    }
}
