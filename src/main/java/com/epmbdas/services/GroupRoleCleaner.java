package com.epmbdas.services;

import com.atlassian.configurable.ObjectConfiguration;
import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.service.AbstractService;
import com.opensymphony.module.propertyset.PropertySet;
import java.util.Arrays;
import java.util.List;

public class GroupRoleCleaner extends AbstractService {
    private String input;

    public ObjectConfiguration getObjectConfiguration() throws ObjectConfigurationException {
        return getObjectConfiguration("GroupRoleCleaner", "com/epmbdas/services/GroupRoleCleaner.xml", null);
    }

    public void run() {
        if (input != null && input.length() > 1) {

            List<String> groups = Arrays.asList(input.split(","));
            ProjectRoleManager projectRoleManager = (ProjectRoleManager) ComponentAccessor.getComponentOfType(ProjectRoleManager.class);

            for (String group : groups) {
                projectRoleManager.removeAllRoleActorsByNameAndType(group.trim(), com.atlassian.jira.security.roles.ProjectRoleActor.GROUP_ROLE_ACTOR_TYPE);
            }
        }
    }

    @Override
    public void init(PropertySet props) throws ObjectConfigurationException {
        super.init(props);
        if (hasProperty("Affected Groups")) input = getProperty("Affected Groups");
    }
}
