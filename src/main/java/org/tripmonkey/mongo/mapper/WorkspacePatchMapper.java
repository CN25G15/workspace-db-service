package org.tripmonkey.mongo.mapper;

import org.tripmonkey.domain.patch.PatchVisitor;
import org.tripmonkey.mongo.data.WorkspacePatchDB;
import org.tripmonkey.rest.domain.WorkspacePatch;
import org.tripmonkey.rest.domain.value.CommentPatch;
import org.tripmonkey.rest.domain.value.LocationListPatch;
import org.tripmonkey.rest.domain.value.LocationMetadataPatch;
import org.tripmonkey.rest.domain.value.LocationPatch;
import org.tripmonkey.rest.domain.value.UserPatch;
import org.tripmonkey.rest.patch.fields.Op;
import org.tripmonkey.rest.patch.fields.path.PathNode;

public class WorkspacePatchMapper {

    public static WorkspacePatchDB from(PatchVisitor wp){
        return switch (wp){
            case CommentPatch cp -> WorkspacePatchDB.from(cp.getWorkspaceId(),cp.getUser(),cp.getOp().toString(),cp.getPath().toString(), cp.getValue());
            case UserPatch up -> WorkspacePatchDB.from(up.getWorkspaceId(),up.getUser(),up.getOp().toString(),up.getPath().toString(), up.getValue());
            case LocationPatch lp -> WorkspacePatchDB.from(lp.getWorkspaceId(),lp.getUser(),lp.getOp().toString(),lp.getPath().toString(), lp.getValue());
            case LocationListPatch llp -> WorkspacePatchDB.from(llp.getWorkspaceId(),llp.getUser(),llp.getOp().toString(),llp.getPath().toString(), llp.getValue());
            case LocationMetadataPatch lmp -> WorkspacePatchDB.from(lmp.getWorkspaceId(),lmp.getUser(),lmp.getOp().toString(),lmp.getPath().toString(), lmp.getValue());
            default -> null;
        };
    }

    public static PatchVisitor from(WorkspacePatchDB wpdb) {
        return switch(wpdb.getValue().getType()){
            case INVALID -> null;
            case COMMENT -> new CommentPatch(wpdb.getWid(), wpdb.getUserId(),
                    Op.forValue(wpdb.getOp()), PathNode.from(wpdb.getPath()), wpdb.getValue().asComment());
            case LOCATION -> new LocationPatch(wpdb.getWid(), wpdb.getUserId(),
                    Op.forValue(wpdb.getOp()), PathNode.from(wpdb.getPath()), wpdb.getValue().asLocation());
            case LOC_LIST -> new LocationListPatch(wpdb.getWid(), wpdb.getUserId(),
                    Op.forValue(wpdb.getOp()), PathNode.from(wpdb.getPath()), wpdb.getValue().asLocationList());
            case LOC_META -> new LocationMetadataPatch(wpdb.getWid(), wpdb.getUserId(),
                    Op.forValue(wpdb.getOp()), PathNode.from(wpdb.getPath()), wpdb.getValue().asLocationMetadata());
            case USER -> wpdb.getValue().asUser().map(userDTO -> new UserPatch(wpdb.getWid(), wpdb.getUserId(),
                    Op.forValue(wpdb.getOp()), PathNode.from(wpdb.getPath()), userDTO)).orElse(null) ;
        };
    }
}
