package org.tripmonkey.mongo.mapper;

import org.tripmonkey.mongo.data.WorkspacePatchDB;
import org.tripmonkey.rest.domain.WorkspacePatch;
import org.tripmonkey.rest.patch.fields.Op;
import org.tripmonkey.rest.patch.fields.path.PathNode;

public class WorkspacePatchMapper {

    public static WorkspacePatchDB from(WorkspacePatch wp){
        return WorkspacePatchDB.from(wp.getWorkspaceId(), wp.getUser(), wp.getOp().toString(), wp.getPath().toString(),
                ValueWrapperMapper.from(wp.getValue()));
    }

    public static WorkspacePatch from(WorkspacePatchDB wpdb) {
        return WorkspacePatch.fromArgs(
                wpdb.getWid(),
                wpdb.getUserId(),
                Op.forValue(wpdb.getOp()),
                PathNode.from(wpdb.getPath()),
                ValueWrapperMapper.from(wpdb.getValue())
        );
    }
}
