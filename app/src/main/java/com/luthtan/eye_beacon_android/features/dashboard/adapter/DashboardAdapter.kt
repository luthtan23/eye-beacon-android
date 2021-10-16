package com.luthtan.eye_beacon_android.features.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luthtan.eye_beacon_android.databinding.ItemDashboardDetailBinding
import com.luthtan.simplebleproject.domain.entities.dashboard.BleEntity
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
                val url = UrlBeaconUrlCompressor.uncompress(bleEntity.id1.toByteArray())
                tvItemDashboardDate.text = bleEntity.bluetoothName
                tvItemDashboardRoomDescription.text = bleEntity.bluetoothAddress
                tvItemDashboardTime.text = bleEntity.rssi.toString().plus(" Url: $url")
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