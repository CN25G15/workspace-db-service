package org.tripmonkey.mongo.mapper;

import org.tripmonkey.domain.data.LocationList;
import org.tripmonkey.domain.patch.PatchVisitor;
import org.tripmonkey.mongo.data.WorkspaceDB;
import org.tripmonkey.domain.data.User;
import org.tripmonkey.domain.data.Workspace;
import org.tripmonkey.domain.patch.WorkspacePatcher;
import org.tripmonkey.rest.domain.WorkspacePatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkspaceMapper {

    public static Workspace from(WorkspaceDB wdb){
        Workspace w = Workspace.from(wdb.getWorkspaceId(),
                wdb.getUsers().stream()
                        .map(User::from)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toCollection(ArrayList::new)),
                new ArrayList<>(List.of(LocationList.newSaved())));
        WorkspacePatcher wp = WorkspacePatcher.forWorkspace(w);
        List<PatchVisitor> list = wdb.getHistory().stream().map(WorkspacePatchMapper::from)
                .collect(Collectors.toCollection(ArrayList::new));
        list.forEach(wp::apply);
        w.setHistory(list);
        return w;
    }

    public static WorkspaceDB from(Workspace w) {
        return WorkspaceDB.from(w.getWid(),
                w.getCollaborators().stream().map(User::toString).toList(),
                w.getHistory()
                        .stream().map(WorkspacePatchMapper::from).toList()
        );
    }

}
