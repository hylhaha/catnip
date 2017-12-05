package com.syeinfo.catnip;

import com.syeinfo.catnip.endpoint.MyEndpoint;

import java.util.ArrayList;
import java.util.List;

public class ResourcesEnrollment {

    public static List<Class> getResourceClasses() {

        List<Class> resources = new ArrayList<>();
        resources.add(MyEndpoint.class);

        return resources;

    }

}
