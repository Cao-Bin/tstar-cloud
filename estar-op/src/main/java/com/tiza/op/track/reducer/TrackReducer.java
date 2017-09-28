package com.tiza.op.track.reducer;

import com.tiza.op.entity.MileageRecord;
import com.tiza.op.model.Position;
import com.tiza.op.model.TrackKey;
import com.tiza.op.util.DateUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Description: TrackReducer
 * Author: DIYILIU
 * Update: 2017-09-27 16:42
 */
public class TrackReducer extends Reducer<TrackKey, Position, MileageRecord, NullWritable> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 统计时间
    private Date date = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        String date_time = context.getConfiguration().get("date_time");
        date = DateUtil.str2Date(date_time);

        logger.info("TrackReducer获取时间[{}]", date_time);
    }

    @Override
    protected void reduce(TrackKey key, Iterable<Position> values, Context context) throws IOException, InterruptedException {

        // 当日最大里程
        double maxMileage = 0;
        // 当日最小里程
        double minMileage = 0;
        for (Iterator iterator = values.iterator(); iterator.hasNext();){
            Position position = (Position) iterator.next();

            double mileage = position.getMileage();
            if (mileage < 0){
                continue;
            }

            if (mileage > maxMileage){
                maxMileage = mileage;
            }

            if (mileage < minMileage){

                minMileage = mileage;
            }
        }

        double dailyMileage = maxMileage - minMileage;

        MileageRecord record = new MileageRecord();
        record.setVehicleId(Long.parseLong(key.getVehicleId()));
        record.setDateTime(date);
        record.setMileage(dailyMileage);
        record.setTotalMileage(maxMileage);
        record.setCreateTime(new Date());

        context.write(record, NullWritable.get());
    }
}
