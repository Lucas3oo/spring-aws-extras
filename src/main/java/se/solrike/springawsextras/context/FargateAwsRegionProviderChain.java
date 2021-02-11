package se.solrike.springawsextras.context;

import com.amazonaws.regions.AwsEnvVarOverrideRegionProvider;
import com.amazonaws.regions.AwsProfileRegionProvider;
import com.amazonaws.regions.AwsRegionProviderChain;
import com.amazonaws.regions.AwsSystemPropertyRegionProvider;

/**
 * Region provider chain for env var, system properties and profiles.
 *
 * EC2 instance identity is not supported.
 *
 * When executed in AWS Fargate the AWS_REGION env var is automatically set by the execution
 * task in ECS.
 */
public class FargateAwsRegionProviderChain extends AwsRegionProviderChain {

  public FargateAwsRegionProviderChain() {
    super(new AwsEnvVarOverrideRegionProvider(), new AwsSystemPropertyRegionProvider(), new AwsProfileRegionProvider());
  }
}
