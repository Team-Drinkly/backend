package com.drinkhere.common.cache;

import com.drinkhere.common.cache.operators.SetOperator;
import com.drinkhere.common.cache.operators.ValueOperator;

public interface CacheTemplate<K, V> {

    SetOperator<K> opsForSet();

    ValueOperator<K, V> opsForValue();
}
