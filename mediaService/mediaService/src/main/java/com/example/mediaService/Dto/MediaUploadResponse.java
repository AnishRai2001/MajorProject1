package com.example.mediaService.Dto;



public class MediaUploadResponse {

    private String s3Url;
    private String fileName;
    private String fileType;

    public MediaUploadResponse() {}

    public MediaUploadResponse(String s3Url, String fileName, String fileType) {
        this.s3Url = s3Url;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getS3Url() { return s3Url; }
    public void setS3Url(String s3Url) { this.s3Url = s3Url; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
}
