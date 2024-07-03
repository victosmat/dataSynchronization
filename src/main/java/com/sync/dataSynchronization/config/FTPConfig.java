package com.sync.dataSynchronization.config;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPReply;

@Configuration
public class FTPConfig {
    @Value("${ftp.ip}")
    private String ip;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    @Value("${ftp.path}")
    private String path;

    @Value("${ftp.buffer-size}")
    private int bufferSize;

    @Value("${ftp.timeout}")
    private int timeout;

    private static final Logger logger = LoggerFactory.getLogger(FTPConfig.class);

    public void connectFTPServer(FTPClient ftpClient) {
        try {
            logger.info("Connecting to FTP server...");
            ftpClient.setDefaultTimeout(timeout);
            ftpClient.connect(ip, port);
            ftpClient.enterLocalPassiveMode();
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                disconnectFTPServer(ftpClient);
                logger.error("FTP server not respond!");
                throw new IOException("FTP server not respond!");
            } else {
                ftpClient.setSoTimeout(timeout);
                if (!ftpClient.login(username, password)) {
                    logger.error("Username or password is incorrect!");
                    throw new IOException("Username or password is incorrect!");
                }
                ftpClient.setDataTimeout(timeout);
                logger.info("Connected to FTP server.");
            }
        } catch (IOException ex) {
            logger.error("Error connecting to FTP server: " + ex.getMessage(), ex);
        }
    }

    public void downloadFile(FTPClient ftpClient, String remoteFilePath, String localFilePath) {
        try {
            logger.info("Downloading file...");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(bufferSize);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFilePath, new FileOutputStream(localFilePath));
            logger.info("File downloaded successfully.");
        } catch (IOException ex) {
            logger.error("Error downloading file: " + ex.getMessage(), ex);
        }
    }

    public static void disconnectFTPServer(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                logger.error("Error disconnecting from the server: " + ex.getMessage());
            }
        }
    }

    public void uploadFile(FTPClient ftpClient, String localFilePath, String remoteFilePath) {
        try (FileInputStream inputStream = new FileInputStream(localFilePath)) {
            logger.info("Uploading file...");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(bufferSize);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            boolean done = ftpClient.storeFile(remoteFilePath, inputStream);
            if (done) {
                logger.info("File uploaded successfully.");
            } else {
                logger.error("Could not upload the file.");
            }
        } catch (IOException ex) {
            logger.error("Error uploading file: " + ex.getMessage(), ex);
        }
    }
}
