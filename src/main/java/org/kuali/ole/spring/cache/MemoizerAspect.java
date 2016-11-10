package org.kuali.ole.spring.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by SheikS on 5/9/2016.
 */
@Aspect
public class MemoizerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoizerAspect.class);

    @Autowired
    private RequestScopeCache requestScopeCache;

    /**
     * Memoize current call
     * @param pjp joint point
     * @return result
     * @throws Throwable call failure
     */
    @Around("@annotation(org.kuali.ole.spring.cache.Memoize)")
    public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
        InvocationContext invocationContext = new InvocationContext(
                pjp.getSignature().getDeclaringType(),
                pjp.getSignature().getName(),
                pjp.getArgs()
        );
        Object result = requestScopeCache.get(invocationContext);
        if (RequestScopeCache.NONE == result) {
            result = pjp.proceed();
            //Enable to log method call information
            //LOGGER.info("Memoizing result {}, for method invocation: {}", result, invocationContext);
            requestScopeCache.put(invocationContext, result);
        } else {
            //Enable to log method call information
           // LOGGER.info("Using memoized result: {}, for method invocation: {}", result, invocationContext);
        }
        return result;
    }
}
