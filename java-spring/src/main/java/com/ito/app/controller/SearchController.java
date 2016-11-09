package com.ito.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ito.app.dto.search.Search;
import com.ito.app.service.SearchService;
import com.snail.core.beans.DeliveryMap;

@RestController
@RequestMapping("get_fare")
public class SearchController {
	
	@Autowired
	private SearchService service;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody DeliveryMap search(@RequestBody Search search) {
		return service.search(search);
	}
}
