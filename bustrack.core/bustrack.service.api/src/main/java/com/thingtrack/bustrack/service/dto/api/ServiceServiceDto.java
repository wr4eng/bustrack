package com.thingtrack.bustrack.service.dto.api;

import java.util.List;

import com.thingtrack.bustrack.domain.dto.ServiceDto;

public interface ServiceServiceDto {
	public List<ServiceDto> getAll() throws Exception;
}
