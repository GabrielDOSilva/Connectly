package br.com.dev.connectly.service;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailCryptoService {

    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;

    private final byte[] encryptionKey;
    private final byte[] hashKey;

    public EmailCryptoService(
            @Value("${email.encryption.key}") String encryptionKey,
            @Value("${email.hash.key}") String hashKey) {

        this.encryptionKey =
                Base64.getDecoder().decode(encryptionKey);

        this.hashKey =
                Base64.getDecoder().decode(hashKey);

        if (this.encryptionKey.length != 32) {
            throw new IllegalArgumentException(
                    "Email encryption key must contain 32 bytes");
        }
    }

    public String encrypt(String email) {

        try {
            byte[] iv = new byte[GCM_IV_LENGTH];

            java.security.SecureRandom secureRandom =
                    new java.security.SecureRandom();

            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance(AES_ALGORITHM);

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            encryptionKey,
                            "AES");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    gcmSpec);

            byte[] encrypted =
                    cipher.doFinal(
                            email.getBytes(
                                    StandardCharsets.UTF_8));

            byte[] result =
                    new byte[
                            iv.length +
                            encrypted.length];

            System.arraycopy(
                    iv,
                    0,
                    result,
                    0,
                    iv.length);

            System.arraycopy(
                    encrypted,
                    0,
                    result,
                    iv.length,
                    encrypted.length);

            return Base64.getEncoder()
                    .encodeToString(result);

        } catch (GeneralSecurityException exception) {

            throw new IllegalStateException(
                    "Could not encrypt email",
                    exception);
        }
    }

    public String decrypt(String encryptedEmail) {

        try {
            byte[] data =
                    Base64.getDecoder()
                    .decode(encryptedEmail);

            byte[] iv =
                    new byte[GCM_IV_LENGTH];

            byte[] encrypted =
                    new byte[
                            data.length -
                            GCM_IV_LENGTH];

            System.arraycopy(
                    data,
                    0,
                    iv,
                    0,
                    iv.length);

            System.arraycopy(
                    data,
                    iv.length,
                    encrypted,
                    0,
                    encrypted.length);

            Cipher cipher =
                    Cipher.getInstance(AES_ALGORITHM);

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            encryptionKey,
                            "AES");

            GCMParameterSpec gcmSpec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    gcmSpec);

            byte[] decrypted =
                    cipher.doFinal(encrypted);

            return new String(
                    decrypted,
                    StandardCharsets.UTF_8);

        } catch (GeneralSecurityException exception) {

            throw new IllegalStateException(
                    "Could not decrypt email",
                    exception);
        }
    }

    public String generateHash(String email) {

        try {
            String normalizedEmail =
                    email.trim()
                    .toLowerCase();

            Mac mac =
                    Mac.getInstance(HMAC_ALGORITHM);

            SecretKeySpec secretKey =
                    new SecretKeySpec(
                            hashKey,
                            HMAC_ALGORITHM);

            mac.init(secretKey);

            byte[] hash =
                    mac.doFinal(
                            normalizedEmail.getBytes(
                                    StandardCharsets.UTF_8));

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (GeneralSecurityException exception) {

            throw new IllegalStateException(
                    "Could not generate email hash",
                    exception);
        }
    }
}