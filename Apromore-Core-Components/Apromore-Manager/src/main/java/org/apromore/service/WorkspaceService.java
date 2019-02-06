/*
 * Copyright © 2009-2018 The Apromore Initiative.
 *
 * This file is part of "Apromore".
 *
 * "Apromore" is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package org.apromore.service;

import org.apromore.dao.dataObject.FolderTreeNode;
import org.apromore.dao.model.*;

import java.util.List;

import org.apromore.dao.model.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for the User Service. Defines all the methods that will do the majority of the work for
 * the Apromore application.
 *
 * @author <a href="mailto:cam.james@gmail.com">Cameron James</a>
 */
public interface WorkspaceService {

    /**
     * Finds a folders and returns it.
     * @param folderId the id oif the folder to find.
     * @return the found folder or null.
     */
    Folder getFolder(Integer folderId);

    List<GroupFolder> getGroupFolders(Integer folderId);

    List<GroupProcess> getGroupProcesses(Integer processId);

    List<GroupProcess> getGroupProcesses(String userId, Integer folderId);

    Page<Process> getProcesses(String userId, Integer folderId, Pageable pageable);

    Page<Log> getLogs(String userId, Integer folderId, Pageable pageable);

    void createFolder(String userId, String folderName, Integer parentFolderId, Boolean isGEDMatrixReady);

    void addProcessToFolder(Integer processId, Integer folderId);

    boolean isGEDReadyFolder(Integer folderId);

    void updateFolder(Integer folderId, String folderName, Boolean isGEDMatrixReady);

    void deleteFolder(Integer folderId);

    List<FolderTreeNode> getWorkspaceFolderTree(String userId);

    List<Folder> getBreadcrumbs(Integer folderId);

    List<GroupFolder> getSubFolders(String userRowGuid, Integer folderId);

    String saveFolderPermissions(Integer folderId, String groupRowGuid, boolean hasRead, boolean hasWrite, boolean hasOwnership);

    String saveProcessPermissions(Integer processId, String groupRowGuid, boolean hasRead, boolean hasWrite, boolean hasOwnership);

    String removeFolderPermissions(Integer folderId, String userId);

    String removeProcessPermissions(Integer processId, String userId);

    /**
     * Creates the public status for the users to have read rights to this model.
     * @param process the process.
     */
    void createPublicStatusForUsers(final Process process);

    /**
     * Removes all users from having access to this model, except the owner.
     * @param process the process model we are restricting access to.
     */
    void removePublicStatusForUsers(final Process process);

    /**
     * For this User make sure all the public models and folders are accessible.
     * @param user the user to update.
     */
    void updateUsersPublicModels(User user);
}