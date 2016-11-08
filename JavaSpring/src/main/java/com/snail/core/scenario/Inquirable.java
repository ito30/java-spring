package com.snail.core.scenario;

import com.ito.app.controller.JsonHandler;
import com.snail.core.fault.Fault;

public interface Inquirable {

	void inquire(JsonHandler param) throws Fault;

}
