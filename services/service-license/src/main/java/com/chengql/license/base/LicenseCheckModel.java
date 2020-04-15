package com.hwsafe.license.base;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 自定义需要校验的License参数
 *
 * @author zifangsky
 * @date 2018/4/23
 * @since 1.0.0
 */
@Data
public class LicenseCheckModel implements Serializable {

    private static final long serialVersionUID = 8600137500316662317L;
    /**
     * 可被允许的IP地址
     */
    /* private List<String> ipAddress; */

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

}
