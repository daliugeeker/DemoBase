package com.leonardo.demobase.utils;

import android.net.Uri;
import android.os.Environment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.leonardo.demobase.DemoBaseApplication;
import java.io.File;

/**
 * Description:
 *
 * @author liuda (liuda@lkmotion.com)
 * @since 2018/11/21
 */

public class Tools {

  public static MediaSource buildMediaSource(Uri videoUri) {
    int type = Util.inferContentType(videoUri);
    DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    DataSource.Factory dataSourceFactory =
        new DefaultDataSourceFactory(DemoBaseApplication.getInstance(),
            Util.getUserAgent(DemoBaseApplication.getInstance(), "DemoBaseApplication"),
            bandwidthMeter);

    switch (type) {
      case C.TYPE_SS:
        DefaultSsChunkSource.Factory ssChunkSourceFactory =
            new DefaultSsChunkSource.Factory(dataSourceFactory);
        SsMediaSource.Factory ssMediaSourceFactory =
            new SsMediaSource.Factory(ssChunkSourceFactory, dataSourceFactory);
        return ssMediaSourceFactory.createMediaSource(videoUri);
      case C.TYPE_DASH:

        DashChunkSource.Factory dashChunkSourceFactory =
            new DefaultDashChunkSource.Factory(dataSourceFactory);
        DashMediaSource.Factory dashMediaSourceFactory =
            new DashMediaSource.Factory(dashChunkSourceFactory, dataSourceFactory);
        return dashMediaSourceFactory.createMediaSource(videoUri);

      case C.TYPE_HLS:

        return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

      case C.TYPE_OTHER:

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        ExtractorMediaSource.Factory extractorMediaSourceFactory =
            new ExtractorMediaSource.Factory(dataSourceFactory);
        extractorMediaSourceFactory.setExtractorsFactory(extractorsFactory);
        return extractorMediaSourceFactory.createMediaSource(videoUri);

      default: {
        throw new IllegalStateException("Unsupported type: " + type);
      }
    }
  }

  public static String generateDir() {
    return Environment.getExternalStorageDirectory().getPath()
        + File.separator
        + "demobase"
        + File.separator;
  }
}
