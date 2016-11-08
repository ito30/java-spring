package com.snail.core.scenario;

import com.ito.common.JsonHandler;
import com.snail.core.fault.Fault;

public interface Inquirable {

	void inquire(JsonHandler param) throws Fault;

}
