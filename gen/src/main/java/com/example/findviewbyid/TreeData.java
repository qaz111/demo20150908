package com.example.findviewbyid;

import java.util.List;

public interface TreeData {

    List<? extends TreeData> getChildNodes();

    TreeData getParent();

    String getNodeName();

}