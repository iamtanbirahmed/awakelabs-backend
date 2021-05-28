package com.awakelabs.readservice.service;

import com.awakelabs.readservice.dto.TimeSeriesValueDTO;
import com.redislabs.redistimeseries.RedisTimeSeries;
import com.redislabs.redistimeseries.Value;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class CacheReadService {

    private RedisTimeSeries redisTimeSeries;
    private ModelMapper modelMapper;

    /**
     * Method to write the phi values form the cache (Abandoned approach due to the privacy concern)
     * @param key patient id
     * @param from start date
     * @param to end date
     * @return returns the PHI values with in the time range
     */
    public List<TimeSeriesValueDTO> getHeartRatesByRange(Integer key, Long from, Long to) {

        List<Value> values = Arrays.asList(redisTimeSeries.range(key.toString(), from, to));
        List<TimeSeriesValueDTO> timeSeriesValueDTO = modelMapper.map(values, List.class);
        return timeSeriesValueDTO;

    }
}
