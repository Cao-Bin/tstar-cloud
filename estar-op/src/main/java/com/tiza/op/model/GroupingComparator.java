package com.tiza.op.model;

import org.apache.hadoop.io.WritableComparator;

/**
 * Description: GroupingComparator
 * Author: DIYILIU
 * Update: 2017-09-27 17:29
 */
public class GroupingComparator extends WritableComparator {

    public GroupingComparator() {
        super(TrackKey.class, true);
    }

    @Override
    public int compare(Object a, Object b) {
        TrackKey keya = (TrackKey) a;
        TrackKey keyb = (TrackKey) a;

        return keya.getVehicleId().compareTo(keyb.getVehicleId());
    }
}
