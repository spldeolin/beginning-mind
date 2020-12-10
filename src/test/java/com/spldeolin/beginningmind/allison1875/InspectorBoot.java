package com.spldeolin.beginningmind.allison1875;

import java.time.LocalDateTime;
import com.spldeolin.allison1875.base.Allison1875;
import com.spldeolin.allison1875.inspector.InspectorConfig;
import com.spldeolin.allison1875.inspector.InspectorModule;

/**
 * @author Deolin 2020-11-30
 */
public class InspectorBoot {

    public static void main(String[] args) {
        InspectorConfig config = new InspectorConfig();
        config.setProjectLocalGitPath("/Users/deolin/Documents/project-repo/beginning-mind");
        config.setTargetFileSince(LocalDateTime.of(3000, 1, 1, 1, 1));

        Allison1875.allison1875(InspectorBoot.class, new InspectorModule(config));
    }

}