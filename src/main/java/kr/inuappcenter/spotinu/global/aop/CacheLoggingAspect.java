package kr.inuappcenter.spotinu.global.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheLoggingAspect {

  @Around("@annotation(cacheable)")
  public Object logCacheHitMiss(ProceedingJoinPoint joinPoint,
                                org.springframework.cache.annotation.Cacheable cacheable) throws Throwable {

    String cacheName = cacheable.value()[0];
    String methodName = joinPoint.getSignature().getName();

    log.info("Calling method {} with cache '{}'", methodName, cacheName);
    log.info("Assuming cache MISS before DB fetch...");

    // 실제 메서드 호출 (Spring Cache가 내부적으로 캐시 처리)
    Object result = joinPoint.proceed();

    log.info("Finished method {} with cache '{}'", methodName, cacheName);
    log.info("After method execution, Spring Cache would have updated the cache.");

    return result;
  }
}
