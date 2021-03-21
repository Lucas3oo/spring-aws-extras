package se.solrike.springawsextras.context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.services.s3.AmazonS3;

/**
 * The test need to be run with -Daws.region=us-east-1 or some other region
 *
 * @author Lucas Persson
 *
 */
@SpringBootTest(classes = { AwsClientBeanFactory2.class })
public class AwsClientBeanFactoryTestWithSystemPropertyTest {

  static {
    System.setProperty(SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY, "us-east-1");
  }

  @AfterAll
  static void tearDownAll() {
    // remove SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY
    System.setProperties(null);
  }

  @Autowired
  AmazonS3 mAmazonS3;

  @Test
  void testAmazonS3Created() {
    assertNotNull(mAmazonS3);
    MockingDetails mockingDetails = mockingDetails(mAmazonS3);
    assertFalse(mockingDetails.isMock());
  }

}
