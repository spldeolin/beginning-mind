package com.spldeolin.beginningmind.core.dto;

import java.io.Serializable;
import com.google.common.cache.CacheStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Deolin 2018/09/23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CacheStatsDTO implements Serializable {

    private Long hitCount;

    private Long missCount;

    private Long loadSuccessCount;

    private Long loadExceptionCount;

    private Long totalLoadTime;

    private Long evictionCount;

    public static CacheStatsDTO fromCacheStats(CacheStats cacheStats) {
        CacheStatsDTO dto = new CacheStatsDTO();
        dto.setHitCount(cacheStats.hitCount());
        dto.setMissCount(cacheStats.missCount());
        dto.setLoadSuccessCount(cacheStats.loadSuccessCount());
        dto.setLoadExceptionCount(cacheStats.loadExceptionCount());
        dto.setTotalLoadTime(cacheStats.totalLoadTime());
        dto.setEvictionCount(cacheStats.evictionCount());
        return dto;
    }

    private static final long serialVersionUID = 1L;

}