package com.masterswork.process.api.dto.process;

import lombok.Data;

import java.time.Instant;

@Data
public class ProcessStartRequest {

    private Instant startTime;
}
