package se.solrike.springawsextras.context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

import com.amazonaws.services.s3.AmazonS3;

/**
 *
 *
 *
 *
 */
@SpringBootTest(classes = { S3ProtocolResolverConfigurator.class, TestConfigurationMock.class })
class ResourceMessageSourceInSpringTest {

  @Autowired
  ResourceLoader mResourceLoader;
  ResourceMessageSource mMessageSource;

  @Autowired
  AmazonS3 mAmazonS3;

  @BeforeEach
  void setUp() {
    mMessageSource = new ResourceMessageSource(mResourceLoader);
    mMessageSource.setBasename("s3://BuildComponents/messages");
  }

  @Test
  void testGetMessageFromS3BucketResource() {
    String message = mMessageSource.getMessage("key1", null, new Locale("sv"));
    assertEquals("nyckel ett", message);
  }

  @Test
  void testGetDefaultMessageFromS3BucketResource() {

    // "en" does not exists instead it will fallback to default messages.properties
    String messageEn = mMessageSource.getMessage("key1", null, new Locale("en"));
    assertEquals("key one", messageEn);

    // but next time the default bundle should be cached under "en" locale
    messageEn = mMessageSource.getMessage("key1", null, new Locale("en"));
    assertEquals("key one", messageEn);

    // check that the AmazonS3 only been called once for "en" and then once for the default
    // after that the resource bundle shall be cached
    verify(mAmazonS3, times(2)).getObject(any());
  }

}
