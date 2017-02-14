package com.nexuslink.model.data;

import com.litao.android.lib.entity.PhotoEntry;

import java.util.List;

/**
 * Created by ASUS-NB on 2017/2/10.
 */

public class EventEntry {
    public List<PhotoEntry> photos;

    public EventEntry(List<PhotoEntry> photos) {
        this.photos = photos;
    }
}
