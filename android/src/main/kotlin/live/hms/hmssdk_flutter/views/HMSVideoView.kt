package live.hms.hmssdk_flutter.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import live.hms.hmssdk_flutter.R
import live.hms.video.media.tracks.HMSVideoTrack
import live.hms.videoview.HMSVideoView
import org.webrtc.RendererCommon

class HMSVideoView(
    context: Context,
    setMirror: Boolean,
    scaleType: Int? = RendererCommon.ScalingType.SCALE_ASPECT_FIT.ordinal,
    private val track: HMSVideoTrack?,
    disableAutoSimulcastLayerSelect: Boolean
) : FrameLayout(context, null) {

    private val hmsVideoView: HMSVideoView

    init {
        val inflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.hms_video_view, this)

        hmsVideoView = view.findViewById(R.id.hmsVideoView)
        hmsVideoView.setEnableHardwareScaler(false)
        hmsVideoView.setMirror(setMirror)
        hmsVideoView.disableAutoSimulcastLayerSelect(disableAutoSimulcastLayerSelect)
        if ((scaleType ?: 0) <= RendererCommon.ScalingType.values().size) {
            hmsVideoView.setScalingType(RendererCommon.ScalingType.values()[scaleType ?: 0])
        }
    }

    fun onDisposeCalled() {
        super.onDetachedFromWindow()
        hmsVideoView.removeTrack()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (track != null) {
            hmsVideoView.addTrack(track)
        } else {
            Log.e("HMSVideoView Error", "track is null, cannot attach null track")
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        hmsVideoView.removeTrack()
    }
}
