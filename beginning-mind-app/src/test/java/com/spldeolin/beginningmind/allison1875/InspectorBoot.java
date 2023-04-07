package com.spldeolin.beginningmind.allison1875;

import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.inspector.InspectorConfig;
import com.spldeolin.allison1875.inspector.InspectorModule;
import com.spldeolin.beginningmind.allison1875.config.ConfigConstant;

/**
 * @author Deolin 2020-11-17
 */
public class InspectorBoot {

    public static void main(String[] args) {
        InspectorConfig inspectorConfig = ConfigConstant.inspectorConfig;
        inspectorConfig.setProjectLocalGitPath("/Users/deolin/Documents/project-repo/beginning-mind");
        Allison1875.allison1875(InspectorBoot.class, new InspectorModule(inspectorConfig));
    }

}