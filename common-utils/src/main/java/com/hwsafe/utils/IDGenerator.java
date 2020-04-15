package com.hwsafe.utils;

import org.springside.modules.utils.misc.IdGenerator;

public interface IDGenerator<T> {

    T generate();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    IDGenerator<String> UUID = new IDGenerator<String>() {

        @Override
        public String generate() {
            return IdGenerator.uuid();
        }
    };

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    IDGenerator<String> UUID2 = new IDGenerator<String>() {

        @Override
        public String generate() {
            return IdGenerator.uuid2();
        }
    };

    /**
     * mongodb objectid
     */
    IDGenerator<String> OBJECTID = new IDGenerator<String>() {

        @Override
        public String generate() {
            return ObjectId.get().toString();
        }
    };

    /**
     * 使用SecureRandom随机生成Long.
     */
    IDGenerator<Long> RANDOM_LONG = new IDGenerator<Long>() {

        @Override
        public Long generate() {
            return IdGenerator.randomLong();
        }
    };

}
