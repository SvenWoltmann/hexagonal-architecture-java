package eu.happycoders.shop.adapter;

import io.quarkus.test.junit.QuarkusTestProfile;

public class AdapterTestProfile implements QuarkusTestProfile {

  static final String PROFILE_NAME = "AdapterTestProfile";

  @Override
  public String getConfigProfile() {
    return PROFILE_NAME;
  }
}
