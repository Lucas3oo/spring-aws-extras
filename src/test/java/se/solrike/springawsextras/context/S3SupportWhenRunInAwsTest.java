package se.solrike.springawsextras.context;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.core.io.s3.SimpleStorageResource;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.amazonaws.SDKGlobalConfiguration;

@SpringBootTest(classes = { TestConfigurationMock.class, TestConfigForS3Support.class })
class S3SupportWhenRunInAwsTest {

  static {
    System.setProperty(SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY, "us-east-1");
  }

  @Autowired
  ApplicationContext mApplicationContext;

  @Test
  void testS3Support() {
    SomeClass bean = mApplicationContext.getBean(SomeClass.class);
    // check that resourceLoader supports S3
    assertTrue(bean.isS3Supported());
    ResourceLoader resourceLoader = bean.getResourceLoader();
    Resource resource = resourceLoader.getResource("s3://BuildComponents/messages.properties");
    assertTrue(resource instanceof SimpleStorageResource);
  }

}
