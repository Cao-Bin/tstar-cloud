package com.tiza.op.model;

import org.apache.hadoop.io.WritableComparable;
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
    public int compare(WritableComparable a, WritableComparable b) {
        TrackKey key1 = (TrackKey)a;
        TrackKey key2 = (TrackKey)b;

        return key1.getVehicleId().compareTo(key2.getVehicleId());
    }
}
