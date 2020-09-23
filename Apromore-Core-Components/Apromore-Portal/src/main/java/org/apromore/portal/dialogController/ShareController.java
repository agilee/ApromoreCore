/*-
 * #%L
 * This file is part of "Apromore Core".
 * %%
 * Copyright (C) 2018 - 2020 Apromore Pty Ltd.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package org.apromore.portal.dialogController;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apromore.dao.model.Group;
import org.apromore.dao.model.Role;
import org.apromore.dao.model.User;
import org.apromore.portal.model.GroupAccessType;
import org.apromore.plugin.portal.PortalContext;

import org.apromore.service.SecurityService;
import org.apromore.manager.client.ManagerService;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Span;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import org.zkoss.spring.SpringUtil;

import org.apromore.portal.model.FolderType;
import org.apromore.portal.model.LogSummaryType;
import org.apromore.portal.model.ProcessSummaryType;
import org.apromore.portal.model.UserType;

import org.apromore.portal.common.access.Assignee;
import org.apromore.portal.common.access.Assignment;

public class ShareController extends SelectorComposer<Window> {

    private Comparator assigneeComparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            String input = (String) o1;
            Assignee assignee = (Assignee) o2;
            return assignee.getName().contains(input) ? 0 : 1;
        }
    };

    private static Logger LOGGER = LoggerFactory.getLogger(ShareController.class);

    private PortalContext portalContext = (PortalContext) Executions.getCurrent().getArg().get("portalContext");
    private MainController mainController = (MainController) Executions.getCurrent().getArg().get("mainController");
    private Object selectedItem = Executions.getCurrent().getArg().get("selectedItem");
    private UserType currentUser = (UserType) Executions.getCurrent().getArg().get("currentUser");

    private ManagerService managerService;
    private SecurityService securityService;
    private Window mainWindow;

    private ListModelList<Assignee> candidateAssigneeModel;
    private ListModelList<Assignment> assignmentModel;

    @Wire("#selectedIconLog")
    Span selectedIconLog;

    @Wire("#selectedIconModel")
    Span selectedIconModel;

    @Wire("#selectedIconFolder")
    Span selectedIconFolder;

    @Wire("#selectedIconMetadata")
    Span selectedIconMetadata;

    @Wire("#selectedName")
    Label selectedName;

    @Wire("#relatedDependencies")
    Div relatedDependencies;

    @Wire("#candidateAssigneeCombobox")
    Combobox candidateAssigneeCombobox;

    @Wire("#candidateAssigneeAdd")
    Button candidateAssigneeAdd;

    @Wire("#applyBtn")
    Button applyBtn;

    @Wire("#assignmentListbox")
    Listbox assignmentListbox;

    public ShareController() throws Exception {
        // TO DO: Common constants
        this.managerService = (ManagerService) SpringUtil.getBean("managerClient");
        this.securityService = (SecurityService) SpringUtil.getBean("securityService");
        selectedItem = Executions.getCurrent().getArg().get("selectedItem");
        currentUser = (UserType) Executions.getCurrent().getArg().get("currentUser");
    }

    @Override
    public void doAfterCompose(Window win) throws Exception {
        super.doAfterCompose(win);
        mainWindow = win;
//        String userName = this.mainController.getPortalContext().getCurrentUser().getUsername();
//        User currentUser = securityService.getUserByName(userName);

        loadAssignment(selectedItem);
        loadCandidateAssignee();
        loadAssignment2();
        loadRelatedDependencies();
    }

    private void loadCandidateAssignee() {
        List<User> users = securityService.getAllUsers();
        List<Group> groups = securityService.findElectiveGroups();
        List<Assignee> candidates = new ArrayList<Assignee>();

        for (User user: users) {
            String userName = user.getUsername();
            candidates.add(new Assignee(userName, 0));
        }
        for (Group group: groups) {
            String groupName = group.getName();
            candidates.add(new Assignee(groupName, 1));
        }
        candidateAssigneeModel = new ListModelList<>(candidates, false);
        candidateAssigneeModel.setMultiple(false);
        candidateAssigneeCombobox.setModel(ListModels.toListSubModel(candidateAssigneeModel, assigneeComparator, 20));
    }

    private void loadAssignment2() {
        List<Assignment> assignments = new ArrayList<Assignment>();
        assignments.add(new Assignment("John", 0, "Viewer"));
        assignments.add(new Assignment("Group 8", 1, "Editor"));
        assignments.add(new Assignment("Jack", 0, "Owner"));

        assignmentModel = new ListModelList<>(assignments, false);
        assignmentListbox.setMultiple(false);
        assignmentListbox.setModel(assignmentModel);
    }

    private void loadRelatedDependencies() {
        relatedDependencies.getChildren().clear();
        relatedDependencies.appendChild(new Label("Log: Dependent log"));
        relatedDependencies.appendChild(new Label("Process Model: Dependent model"));
    }

    public void loadAssignment(Object selectedItem){
        List<GroupAccessType> groups;
        int id;
        String name;

        selectedIconFolder.setVisible(false);
        selectedIconLog.setVisible(false);
        selectedIconModel.setVisible(false);
        selectedIconMetadata.setVisible(false);

        if (selectedItem instanceof FolderType) {
            FolderType folder = (FolderType)selectedItem;
            id = folder.getId();
            name = folder.getFolderName();
            groups = managerService.getFolderGroups(id);
            selectedIconFolder.setVisible(true);
        } else if (selectedItem instanceof ProcessSummaryType) {
            ProcessSummaryType process = (ProcessSummaryType)selectedItem;
            id = process.getId();
            name = process.getName();
            groups = managerService.getProcessGroups(id);
            selectedIconModel.setVisible(true);
        } else if (selectedItem instanceof LogSummaryType) {
            LogSummaryType log = (LogSummaryType)selectedItem;
            id = log.getId();
            name = log.getName();
            groups = managerService.getLogGroups(id);
            selectedIconLog.setVisible(true);
        } else {
            return;
        }
        mainWindow.setTitle("Sharing: " + name);
        selectedName.setValue(name);
    }

    @Listen("onClick = #candidateAssigneeAdd")
    public void onClickCandidateUserAdd() {
        Set<Assignee> assignees = candidateAssigneeModel.getSelection();
        if (assignees != null && assignees.size() == 1) {
            Assignee assignee = assignees.iterator().next();
            assignmentModel.add(new Assignment(assignee.getName(), assignee.getType(), "Viewer"));
        }
    }

    @Listen("onClick = #btnApply")
    public void onClickBtnApply() {
        getSelf().detach();
    }

    @Listen("onClick = #btnCancel")
    public void onClickBtnCancel() {
        getSelf().detach();
    }

    private void showNotification(String message, String type) {
        Clients.evalJavaScript("Ap.common.notify('" + message + "','" + type + "');");
    }
}