package com.awakelabs.writeservice.service;


import com.awakelabs.writeservice.dto.HeartRateDTO;
import com.redislabs.redistimeseries.RedisTimeSeries;
import com.redislabs.redistimeseries.Value;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is the abstraction of redis-timeseries module.
 * Behaves as a write-through cache-ing protocol.
 */

@AllArgsConstructor
@Service
public class CacheWriteService {

    private RedisTimeSeries redisTimeSeries;

    /**
     * Sets the heart rates vitals into the cache
     * @param heartRatesDTO
     * @param id
     */
    public void setHeartRates(Set<HeartRateDTO> heartRatesDTO, Integer id) {
        Map<String, String> labels = new HashMap<>();
        labels.put("patient_id", id.toString());

        try {
            Value value = redisTimeSeries.get(id.toString());
        } catch (Exception e) {

            System.out.println(e.getMessage());
            redisTimeSeries.create(id.toString());
        }

        Long max = Long.MIN_VALUE;
        Long min = Long.MAX_VALUE;

        for (HeartRateDTO heartRate : heartRatesDTO) {
            Double value = heartRate.getHeartRate();
            Long time = heartRate.getTime();

            /*
            use this when necessary
            if (max <= heartRate.getTime())
                max = heartRate.getTime();

            if (min > heartRate.getTime())
                min = heartRate.getTime();

             */
            redisTimeSeries.add(id.toString(), time, value);
            System.out.println(heartRate);
        }

        System.out.println("Max:" + max + " Min:" + min);


    }


}
