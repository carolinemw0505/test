package com.dco.eureka.client.factory;

import com.dco.eureka.client.platform.PlatformInterface;

public interface PlatformFactoryInterface {
	public PlatformInterface createPlatform(String type);
}
