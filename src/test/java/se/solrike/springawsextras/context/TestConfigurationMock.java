package se.solrike.springawsextras.context;

import static org.mockito.Mockito.*;

import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Configuration
public class TestConfigurationMock {

  @Bean
  AmazonS3 amazonS3() {
    AmazonS3 amazonS3 = mock(AmazonS3.class);

    // load the real message files
    InputStream inputStreamSv = getClass().getResourceAsStream("/i18n/messages_sv.properties");
    S3Object s3Object = new S3Object();
    s3Object.setObjectContent(inputStreamSv);
    GetObjectRequest getObjectRequestSv = new GetObjectRequest("BuildComponents", "messages_sv.properties");
    when(amazonS3.getObject(getObjectRequestSv)).thenReturn(s3Object);

    InputStream inputStreamDefault = getClass().getResourceAsStream("/i18n/messages.properties");
    S3Object s3ObjectDefault = new S3Object();
    s3ObjectDefault.setObjectContent(inputStreamDefault);
    GetObjectRequest getObjectRequestDefault = new GetObjectRequest("BuildComponents", "messages.properties");
    when(amazonS3.getObject(getObjectRequestDefault)).thenReturn(s3ObjectDefault);

    // en doesn't exitst
    GetObjectRequest getObjectRequestEn = new GetObjectRequest("BuildComponents", "messages_en.properties");
    when(amazonS3.getObject(getObjectRequestEn)).thenThrow(AmazonS3Exception.class);

    return amazonS3;
  }
}
