package se.solrike.springawsextras.context;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.services.s3.AmazonS3;

@SpringBootTest(classes = { AwsClientBeanFactory.class })
public class AwsClientBeanFactoryTest {

  @Autowired
  AmazonS3 mAmazonS3;

  /**
   * When no region configured the AmazonS3 will be a mock
   */
  @Test
  void testAmazonS3NotCreated() {

    String getenv = System.getenv("AWS_REGION");
    System.out.println(getenv);

    MockingDetails mockingDetails = mockingDetails(mAmazonS3);
    assertTrue(mockingDetails.isMock());
  }
}
