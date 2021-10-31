package com.luthtan.eye_beacon_android.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthtan.eye_beacon_android.databinding.ItemDashboardDetailBinding
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private val bleEntity = ArrayList<Beacon>()

    fun setBleLogHistory(bleEntity: List<Beacon>) {
        this.bleEntity.clear()
        this.bleEntity.addAll(bleEntity)
        notifyDataSetChanged()
    }

    inner class DashboardViewHolder(private val binding: ItemDashboardDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bleEntity: Beacon) {
            binding.apply {
                if (bleEntity.serviceUuid == 0xfeaa && bleEntity.beaconTypeCode == 0x10) {
                    val url = UrlBeaconUrlCompressor.uncompress(bleEntity.id1.toByteArray())
                    tvItemDashboardTime.text = bleEntity.rssi.toString().plus(" Url: $url")
                }
                tvItemDashboardDate.text = bleEntity.bluetoothName
                tvItemDashboardRoomDescription.text = bleEntity.bluetoothAddress
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val itemDashboardDetailBinding = ItemDashboardDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DashboardViewHolder(itemDashboardDetailBinding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(bleEntity[position])
    }

    override fun getItemCount(): Int = bleEntity.size
}