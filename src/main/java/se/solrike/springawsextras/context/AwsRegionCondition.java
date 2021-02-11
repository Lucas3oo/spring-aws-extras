package se.solrike.springawsextras.context;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.amazonaws.SdkClientException;

/**
 * Condition that is true if there is an AWS region env var or system property or profile
 * configured.
 * <p>
 * EC2 instance identity is not supported.
 * <p>
 * This condition can be used to automatically detect if the Spring app is running in AWS cloud
 * or not.
 *
 *
 * @author Lucas Persson
 *
 */
public class AwsRegionCondition implements Condition {

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    FargateAwsRegionProviderChain providerChain = new FargateAwsRegionProviderChain();
    try {
      String region = providerChain.getRegion();
      return region != null;
    }
    catch (SdkClientException e) {
      return false;
    }
  }
}
