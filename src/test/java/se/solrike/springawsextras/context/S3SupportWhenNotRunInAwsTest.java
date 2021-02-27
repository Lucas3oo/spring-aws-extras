package se.solrike.springawsextras.context;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest(classes = { S3ProtocolResolverConfigurator.class, TestConfigForS3Support.class })
class S3SupportWhenNotRunInAwsTest {

  @Autowired
  ApplicationContext mApplicationContext;

  @Test
  void testS3Support() {
    // check that resourceLoader do not supports S3
    SomeClass bean = mApplicationContext.getBean(SomeClass.class);

    assertFalse(bean.isS3Supported());

    ResourceLoader resourceLoader = bean.getResourceLoader();
    Resource resource = resourceLoader.getResource("s3://BuildComponents/messages.properties");
    try {
      resource.getInputStream();
      fail("should not be possible to get the resource");
    }
    catch (IOException e) {

    }
  }

}
