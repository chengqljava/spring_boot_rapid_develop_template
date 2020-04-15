package com.hwsafe.template.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwsafe.position.domain.PositionCity;
import com.hwsafe.position.domain.PositionCounty;
import com.hwsafe.position.domain.PositionProvice;
import com.hwsafe.position.domain.PositionTown;
import com.hwsafe.position.domain.PositionVillage;
import com.hwsafe.position.service.PositionCityService;
import com.hwsafe.position.service.PositionCountyService;
import com.hwsafe.position.service.PositionProviceService;
import com.hwsafe.position.service.PositionTownService;
import com.hwsafe.position.service.PositionVillageService;

@Controller
@RequestMapping("/position")
public class PositionController {
    // 省
    @Autowired
    private PositionProviceService positionProviceService;
    // 市
    @Autowired
    private PositionCityService positionCityService;
    // 区 、县、县级市
    @Autowired
    private PositionCountyService positionCountyService;
    // 镇 街道 乡
    @Autowired
    private PositionTownService positionTownService;
    // 村 镇 社区
    @Autowired
    private PositionVillageService positionVillageService;

    @RequestMapping("/generate")
    @ResponseBody
    public String generate() throws IOException {
        // 0：地市 1：区县级别，2：街道办级别，3：社区级别)
        // 省 2位 市 4位 县 6位 街道 9 位社区 12位
        List<PositionProvice> proviceList = positionProviceService.list();
        List<PositionCity> cityList = null;
        List<PositionCounty> countyList = null;
        List<PositionTown> townList = null;
        List<PositionVillage> villageList = null;
        StringBuffer proviceStr = null;
        StringBuffer cityStr = null;
        for (PositionProvice positionProvice : proviceList) {
            proviceStr = new StringBuffer();
            proviceStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                    + positionProvice.getProviceId().toString().substring(0, 2)
                    + "', '-1', '"
                    + positionProvice.getProviceId().toString().substring(0, 2)
                    + "', '" + positionProvice.getProviceName() + "', '"
                    + positionProvice.getProviceName() + "', '"
                    + positionProvice.getProviceName() + "', '-1');\n");
            cityList = positionCityService
                    .selectByProvinceId(positionProvice.getProviceId());
            // 市
            for (PositionCity positionCity : cityList) {
                cityStr = new StringBuffer();
                proviceStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                        + positionCity.getCityId().toString().substring(0, 4)
                        + "', '"
                        + positionProvice.getProviceId().toString().substring(0,
                                2)
                        + "', '"
                        + positionCity.getCityId().toString().substring(0, 4)
                        + "', '" + positionCity.getCityName() + "', '"
                        + positionCity.getCityName() + "', '"
                        + positionCity.getCityName() + "', '0');\n");
                cityStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                        + positionCity.getCityId().toString().substring(0, 4)
                        + "', '-1', '"
                        + positionCity.getCityId().toString().substring(0, 4)
                        + "', '" + positionCity.getCityName() + "', '"
                        + positionCity.getCityName() + "', '"
                        + positionCity.getCityName() + "', '0');\n");
                countyList = positionCountyService
                        .selectByCityId(positionCity.getCityId());
                // 县 区
                for (PositionCounty positionCounty : countyList) {
                    proviceStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                            + positionCounty
                                    .getCountyId().toString().substring(0, 6)
                            + "', '"
                            + positionCity
                                    .getCityId().toString().substring(0, 4)
                            + "', '"
                            + positionCounty.getCountyId().toString()
                                    .substring(0, 6)
                            + "', '" + positionCounty.getCountyName() + "', '"
                            + positionCounty.getCountyName() + "', '"
                            + positionCounty.getCountyName() + "', '1');\n");
                    cityStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                            + positionCounty
                                    .getCountyId().toString().substring(0, 6)
                            + "', '"
                            + positionCity
                                    .getCityId().toString().substring(0, 4)
                            + "', '"
                            + positionCounty.getCountyId().toString()
                                    .substring(0, 6)
                            + "', '" + positionCounty.getCountyName() + "', '"
                            + positionCounty.getCountyName() + "', '"
                            + positionCounty.getCountyName() + "', '1');\n");
                    townList = positionTownService
                            .selectByCountyId(positionCounty.getCountyId());
                    // 镇 乡 街道
                    for (PositionTown positionTown : townList) {
                        cityStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                                + positionTown
                                        .getTownId().toString().substring(0, 9)
                                + "', '"
                                + positionCounty.getCountyId().toString()
                                        .substring(0, 6)
                                + "', '"
                                + positionTown.getTownId().toString()
                                        .substring(0, 9)
                                + "', '" + positionTown.getTownName() + "', '"
                                + positionTown.getTownName() + "', '"
                                + positionTown.getTownName() + "', '2');\n");
                        proviceStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                                + positionTown
                                        .getTownId().toString().substring(0, 9)
                                + "', '"
                                + positionCounty.getCountyId().toString()
                                        .substring(0, 6)
                                + "', '"
                                + positionTown.getTownId().toString()
                                        .substring(0, 9)
                                + "', '" + positionTown.getTownName() + "', '"
                                + positionTown.getTownName() + "', '"
                                + positionTown.getTownName() + "', '2');\n");
                        // 社区 村
                        villageList = positionVillageService
                                .selectByTownId(positionTown.getTownId());
                        for (PositionVillage positionVillage : villageList) {
                            cityStr.append("INSERT INTO SYS_DISTRICT  VALUES ('"
                                    + positionVillage.getVillageId() + "', '"
                                    + positionTown.getTownId().toString()
                                            .substring(0, 9)
                                    + "', '" + positionVillage.getVillageId()
                                    + "', '" + positionVillage.getVillageName()
                                    + "', '" + positionVillage.getVillageName()
                                    + "', '" + positionVillage.getVillageName()
                                    + "', '3');\n");
                            proviceStr.append(
                                    "INSERT INTO SYS_DISTRICT  VALUES ('"
                                            + positionVillage.getVillageId()
                                            + "', '"
                                            + positionTown.getTownId()
                                                    .toString().substring(0, 9)
                                            + "', '"
                                            + positionVillage.getVillageId()
                                            + "', '"
                                            + positionVillage.getVillageName()
                                            + "', '"
                                            + positionVillage.getVillageName()
                                            + "', '"
                                            + positionVillage.getVillageName()
                                            + "', '3');\n");
                        }
                    }
                }
                // 生成省文件 编码 _省_市
                fileGenerate(
                        positionProvice.getProviceId().toString().substring(0,
                                2) + "_" + positionProvice.getProviceName()
                                + "_"
                                + positionCity.getCityId().toString()
                                        .substring(0, 4)
                                + "_" + positionCity.getCityName(),
                        cityStr.toString());
            }
            // 生成省文件 编码_省
            fileGenerate(
                    positionProvice.getProviceId().toString().substring(0, 2)
                            + "_" + positionProvice.getProviceName(),
                    proviceStr.toString());
        }
        return "success";
    }

    /**
     * @param fileName
     * @param content
     * @throws IOException
     *             文件生成
     */
    public void fileGenerate(String fileName, String content)
            throws IOException {
        File file = new File("D:\\genter/" + fileName + ".txt");
        if (!file.exists())
            file.createNewFile();
        FileOutputStream out = new FileOutputStream(file, true);
        out.write(content.toString().getBytes("utf-8"));
        out.close();
    }
}
