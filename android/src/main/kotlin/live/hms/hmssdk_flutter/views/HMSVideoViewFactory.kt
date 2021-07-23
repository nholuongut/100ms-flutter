package live.hms.hmssdk_flutter.views

import android.content.Context
import android.util.Log
import android.view.View
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformViewFactory
import live.hms.hmssdk_flutter.HmssdkFlutterPlugin
import live.hms.video.sdk.models.HMSPeer

class HMSVideoViewWidget(context: Context, id: Int, creationParams: Map<String?, Any?>?,peer:HMSPeer,trackId:String) : PlatformView {
    val hmsVideoView: HMSVideoView

    override fun getView(): View {
        return hmsVideoView
    }

    override fun dispose() {}

    init {
        hmsVideoView=HMSVideoView(context)
        Log.i("HMSVIdeo",peer.toString())
        peer?.videoTrack.let {
            if (it?.trackId==trackId){
                hmsVideoView.setVideoTrack(peer)
            }
        }
    }
}

class HMSVideoViewFactory(val plugin: HmssdkFlutterPlugin) : PlatformViewFactory(StandardMessageCodec.INSTANCE) {


    override fun create(context: Context, viewId: Int, args: Any?): PlatformView {

        val creationParams = args as Map<String?, Any?>?
        val id=args!!["peer_id"] as String
        val isLocal=args!!["is_local"] as Boolean
        val trackId=args!!["track_id"] as String
        Log.i("onCreateHMSVideoView", "$isLocal $id")
        Log.i("HMSVideoViewFactory",plugin.getPeerById(id,isLocal)!!.toString())
        return HMSVideoViewWidget(context, viewId, creationParams,plugin.getPeerById(id,isLocal)!!,trackId)
    }
}




