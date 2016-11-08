package com.snail.core.scenario;

import com.snail.core.fault.Fault;
import com.snail.core.util.JsonHandler;

public interface Inquirable {

	void inquire(JsonHandler param) throws Fault;

}
