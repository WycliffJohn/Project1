    RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(60000)
            .setSocketTimeout(60000)
            .build();
            
        private void uploadFileToArtifact(CloseableHttpClient client, String baseArtifactoryPath, String content, String username, String token) throws Exception{
        HttpPut httpPut = new HttpPut(baseArtifactoryPath);
        httpPut.setConfig(requestConfig);
        httpPut.setEntity(new StringEntity(content));
        httpPut.setHeader("Content-Type","application/json; charset=utf-8");
        httpPut.setHeader(new BasicScheme().authenticate(credsObject,httpPut,null));

        try (CloseableHttpResponse response = client.execute(httpPut)) {
            log.info("Properties Successfully Uploaded and the sesponse from Artifactory Properties Upload: {}",response);
            String responseContent = EntityUtils.toString(response.getEntity());
            log.info("Response content: {}",responseContent);
        } catch (SocketTimeoutException e) {
            log.error("Socket timeout occurred while uploading Properties: {}",e.getMessage());
            throw new RuntimeException("Request timed out while uploading properties.",e);
        } catch (ConnectTimeoutException e) {
            log.error("Connection timeout occurred while uploading Properties: {}",e.getMessage());
            throw new RuntimeException("Connection timed out while uploading properties.",e);
        } catch (Exception e) {
            log.error("Exception occurred while uploading Properties: {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
