package com.andresgm.erp_lite.infrastructure.persistence.aws.adapters;


import com.andresgm.erp_lite.domain.exceptions.MyBusinessException;
import com.andresgm.erp_lite.domain.ports.ImageStorageService;
import com.andresgm.erp_lite.domain.product.ProductImage;
import com.andresgm.erp_lite.infrastructure.persistence.aws.models.AwsConfigModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsImageStorageService implements ImageStorageService {

    private final S3Client s3Client;
    private final AwsConfigModel awsConfig;

    @Override
    public ProductImage upload(String imageName, byte[] imageData) {
        try{
            final var key = this.getKeyFromUrl("products/" + imageName);
            final var putObjectRequest = PutObjectRequest
                    .builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .contentType(this.determineContentType(imageName))
                    .contentLength((long) imageData.length)
                    .build();
            this.s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageData));
            final var imgUrl = this.buildUrlImage(key);
            log.info("image uploaded successfully in {}", imgUrl);

            return new ProductImage(imgUrl);


        } catch (S3Exception s3e) {
            log.error("Error uploading image", s3e);
            throw new MyBusinessException("Error uploading image " + s3e.getMessage());
        } catch (Exception e){
            log.error("Unexpected error uploading image", e);
            throw new MyBusinessException("Error uploading image" + e.getMessage());
        }
    }

    @Override
    public void delete(ProductImage productImage) {
        try{
            final var key = this.getKeyFromUrl(productImage.imageUrl());
            final var deleteObjectRequest = DeleteObjectRequest
                    .builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .build();
            this.s3Client.deleteObject(deleteObjectRequest);
            log.info("Deleted image success {}",productImage.imageUrl());
        }
        catch(S3Exception s3e){
            log.error("Error deleting image", s3e);
            throw new MyBusinessException("Error deleting image" + s3e.getMessage());
        }catch (Exception e){
            log.error("Unexpected error deleting image", e);
            throw new MyBusinessException("Error deleting image" + e.getMessage());
        }
    }

    @Override
    public byte[] download(ProductImage productImage) {
        try{
            final var key = this.getKeyFromUrl(productImage.imageUrl());
            final var getObjectRequest = GetObjectRequest
                    .builder()
                    .bucket(awsConfig.bucketName())
                    .key(key)
                    .build();
            final var bytes = this.s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
            log.info("Downloading image {} bytes", bytes.length);
            return bytes;

        } catch (S3Exception s3e){
            log.error("Error downloading image", s3e);
            throw new MyBusinessException("Error downloading image" + s3e.getMessage());
        } catch(Exception e){
            log.error("Unexpected error downloading image", e);
            throw new MyBusinessException("Unexpected error downloading image" + e.getMessage());
        }
    }

    private String getKeyFromUrl(String url){
        var bucketName = awsConfig.bucketName();
        var parts = url.split("/" + bucketName + "/");

        if(parts.length > 1 ){
            return parts[1];
        }
        log.warn("No bucket name found for url {}", url);
        return url;
    }

    private String buildUrlImage(String key){
        final var placeholder = "%s/%s/%s";
        return String.format(placeholder,this.awsConfig.endpoint(),awsConfig.bucketName(),key);
    }

    private String determineContentType(String fileName){
        final var extension =
                fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        return switch (extension){
            case "jpg" -> "image/jpeg";
            case "png" -> "image/png";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

}
