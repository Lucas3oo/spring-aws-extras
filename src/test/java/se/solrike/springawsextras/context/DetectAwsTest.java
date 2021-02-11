package se.solrike.springawsextras.context;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.SdkClientException;

public class DetectAwsTest {

  @AfterAll
  static void tearDownAll() throws ReflectiveOperationException {
    // remove SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY
    System.setProperties(null);
    // remove the env var
    removeEnv(SDKGlobalConfiguration.AWS_REGION_ENV_VAR);
  }

  /**
   * If a AWS region config is found then it can be assumed that AWS is present. I.e. that the
   * code shall try to connect to AWS services.
   */
  @Test
  void testDetectIfRunningWithAwsSupportFail() {
    // use FargateAwsRegionProviderChain to let AWS SDK try to find the region settings.
    // Like system properties, env var,
    FargateAwsRegionProviderChain providerChain = new FargateAwsRegionProviderChain();

    // if no region is found this call will through exception even though the JavaDoc says null
    // should be returned.
    String region = null;
    try {
      region = providerChain.getRegion();
      fail("No provider shall find region info");
    }
    catch (SdkClientException e) {
      assertThat(e.getMessage()).isEqualTo("Unable to load region information from any provider in the chain");
    }
    assertThat(region).isNull();
  }

  @Test
  void testDetectIfRunningWithAwsSupportSystemProperty() {
    // use FargateAwsRegionProviderChain to let AWS SDK try to find the region settings.
    // Like system properties, env var,
    String region = "us-east-1";
    System.setProperty(SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY, region);
    FargateAwsRegionProviderChain providerChain = new FargateAwsRegionProviderChain();

    String actualRegion = providerChain.getRegion();

    assertThat(actualRegion).isEqualTo(region);
  }

  @Test
  void testDetectIfRunningWithAwsSupportEnvVar() throws ReflectiveOperationException {
    // use FargateAwsRegionProviderChain to let AWS SDK try to find the region settings.
    // Like system properties, env var,
    String region = "us-east-1";
    System.setProperty(SDKGlobalConfiguration.AWS_REGION_SYSTEM_PROPERTY, "some-region");
    // also set the env var which shall override the system property
    updateEnv(SDKGlobalConfiguration.AWS_REGION_ENV_VAR, region);
    FargateAwsRegionProviderChain providerChain = new FargateAwsRegionProviderChain();

    String actualRegion = providerChain.getRegion();

    assertThat(actualRegion).isEqualTo(region);
  }

  public static void updateEnv(String envName, String value) throws ReflectiveOperationException {
    getEnv().put(envName, value);
  }

  public static void removeEnv(String envName) throws ReflectiveOperationException {
    getEnv().remove(envName);
  }

  @SuppressWarnings({ "unchecked" })
  private static Map<String, String> getEnv() throws NoSuchFieldException, IllegalAccessException {
    Map<String, String> env = System.getenv();
    Field field = env.getClass().getDeclaredField("m");
    field.setAccessible(true);
    return ((Map<String, String>) field.get(env));
  }

}
