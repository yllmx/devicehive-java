/*
 * Device Hive REST API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 3.3.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.devicehive.client.model;

import com.devicehive.client.DeviceHive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Network {

    private Long id = null;

    private String name = null;

    private String description = null;

    private Network() {
    }

    public void save() throws IOException {
        DeviceHive.getInstance().getNetworkService().updateNetwork(id, name, description);
    }

    public static Network create(com.devicehive.rest.model.Network network) {
        if (network == null) return null;
        return Network.builder()
                .id(network.getId())
                .name(network.getName())
                .description(network.getDescription())
                .build();
    }

    public static Network create(com.devicehive.rest.model.NetworkVO network) {
        if (network == null) return null;
        return Network.builder()
                .id(network.getId())
                .name(network.getName())
                .description(network.getDescription())
                .build();
    }

    public static List<Network> createList(List<com.devicehive.rest.model.Network> list) {
        if (list == null) return null;
        List<Network> result = new ArrayList<Network>(list.size());
        for (com.devicehive.rest.model.Network n : list) {
            result.add(create(n));
        }
        return result;
    }

    public static List<Network> createListVO(List<com.devicehive.rest.model.NetworkVO> list) {
        if (list == null) return null;
        List<Network> result = new ArrayList<Network>(list.size());
        for (com.devicehive.rest.model.NetworkVO n : list) {
            result.add(create(n));
        }
        return result;
    }

}

