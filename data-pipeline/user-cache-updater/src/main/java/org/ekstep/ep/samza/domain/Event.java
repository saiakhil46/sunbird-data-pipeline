package org.ekstep.ep.samza.domain;

import org.ekstep.ep.samza.reader.NullableValue;
import org.ekstep.ep.samza.reader.Telemetry;

import java.util.ArrayList;
import java.util.Map;

public class Event {
    private final Telemetry telemetry;

    public Event(Map<String, Object> map) {
        this.telemetry = new Telemetry(map);
    }

    public String objectUserId() {
        NullableValue<String> objectType = telemetry.read("object.type");
        if (null != objectType.value() && objectType.value().equalsIgnoreCase("User")) {
            NullableValue<String> objectUserId = telemetry.read("object.id");
            return objectUserId.value();
        }
        return null;
    }

    public String getUserSignInType() {
        NullableValue<ArrayList<Map<String, String>>> cdata = telemetry.read("context.cdata");
        ArrayList<Map<String, String>> cdataList = cdata.value();
        if (null != cdataList && !cdataList.isEmpty()) {
            for (Map<String, String> cdataMap : cdataList) {
                if (cdataMap.containsKey("type") && cdataMap.get("type").equalsIgnoreCase("SignupType"))
                    return cdataMap.get("id").toString();
            }

        }

        return null;
    }

    public String getUserLoginType() {
        NullableValue<ArrayList<Map<String, String>>> cdata = telemetry.read("context.cdata");
        ArrayList<Map<String, String>> cdataList = cdata.value();
        if (null != cdataList && !cdataList.isEmpty()) {
            for (Map<String, String> cdataMap : cdataList) {
                if (cdataMap.containsKey("type") && cdataMap.get("type").equalsIgnoreCase("UserRole"))
                    return cdataMap.get("id").toString();
            }
        }
        return null;
    }

    public ArrayList<String> getUserMetdataUpdatedList() {
        NullableValue<ArrayList<String>> edata_props = telemetry.read("edata.props");
        if(null != edata_props) {
            ArrayList<String> edata_props_list = edata_props.value();
            if(null != edata_props_list && !edata_props_list.isEmpty()) {
                return edata_props_list;
            }
        }
        return null;
    }

    public String getUserStateValue() {
        NullableValue<String> edata_state = telemetry.read("edata.state");
        if( null != edata_state.value() && edata_state.value().equalsIgnoreCase("Create")) {
            return "Create";
        }
        else if( null != edata_state.value() && edata_state.value().equalsIgnoreCase("Update")) {
            return "Update";
        }
        return null;
    }


    @Override
    public String toString() {
        return "Event{" +
                "telemetry=" + telemetry +
                '}';
    }


}

