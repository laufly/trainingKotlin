package com.example.iyunxiao.activity

import java.util.LinkedList

import android.app.Activity
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView

import com.example.iyunxiao.training.R
import kotlinx.android.synthetic.main.activity_record.*


class RecordPlayActivity : Activity(), OnClickListener {

    private var bt_exit: Button? = null

    protected var m_in_buf_size: Int = 0

    private var m_in_rec: AudioRecord? = null

    private var m_in_bytes: ByteArray? = null

    private var m_in_q: LinkedList<ByteArray>? = null

    private var m_out_buf_size: Int = 0

    private var m_out_trk: AudioTrack? = null

    private var m_out_bytes: ByteArray? = null

    private var record: Thread? = null

    private var play: Thread? = null

    private var flag = true

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        val textview = TextView(this).apply{
            text = "hah"
            val drawable = resources.getDrawable(R.drawable.close)
                    drawable.setBounds(0, 0, 32, 32);
            setCompoundDrawables(drawable,null,null,null)
        }
        content.addView(textview)
        init()
        record = Thread(recordSound())
        play = Thread(playRecord())
        record!!.start()
        play!!.start()
    }

    private fun init() {
        bt_exit = findViewById<View>(R.id.record_exit_button) as Button
        Log.i(TAG, "bt_exit====" + bt_exit!!)

        bt_exit!!.setOnClickListener(this)
        m_in_buf_size = AudioRecord.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT)
        m_in_rec = AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, m_in_buf_size)

        m_in_bytes = ByteArray(m_in_buf_size)
        m_in_q = LinkedList()

        m_out_buf_size = AudioTrack.getMinBufferSize(8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT)
        m_out_trk = AudioTrack(AudioManager.STREAM_MUSIC, 8000,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, m_out_buf_size,
                AudioTrack.MODE_STREAM)
        m_out_bytes = ByteArray(m_out_buf_size)
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        when (v.id) {

            R.id.record_exit_button -> {
                flag = false
                m_in_rec!!.stop()
                m_in_rec = null
                m_out_trk!!.stop()
                m_out_trk = null
                this.finish()
            }
        }
    }

    /**
     * 录制
     */
    internal inner class recordSound : Runnable {
        override fun run() {
            Log.i(TAG, "........recordSound run()......")
            var bytes_pkg: ByteArray
            m_in_rec!!.startRecording()

            while (flag) {
                m_in_rec!!.read(m_in_bytes!!, 0, m_in_buf_size)
                bytes_pkg = m_in_bytes!!.clone()
                Log.i(TAG, "........recordSound bytes_pkg==" + bytes_pkg.size)
                if (m_in_q!!.size >= 2) {
                    m_in_q!!.removeFirst()
                }
                m_in_q!!.add(bytes_pkg)
            }
        }

    }

    /**
     * 播放
     */
    internal inner class playRecord : Runnable {
        override fun run() {
            // TODO Auto-generated method stub
            Log.i(TAG, "........playRecord run()......")
            var bytes_pkg: ByteArray? = null
            m_out_trk!!.play()

            while (flag) {
                try {
                    m_out_bytes = m_in_q!!.first
                    bytes_pkg = m_out_bytes!!.clone()
                    m_out_trk!!.write(bytes_pkg, 0, bytes_pkg.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    companion object {

        private val TAG = "RecordPlayActivity"
    }

}