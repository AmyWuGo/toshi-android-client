/*
 * 	Copyright (c) 2017. Toshi Inc
 *
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.toshi.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.toshi.R
import com.toshi.view.adapter.viewholder.WalletViewHolder
import com.toshi.viewModel.Wallet

class WalletAdapter(
        private val onItemClickedListener: (Int) -> Unit
) : RecyclerView.Adapter<WalletViewHolder>() {

    private val wallets by lazy { mutableListOf<Wallet>() }

    private var currentSelectedItem = 0
    private var previousSelectedItem = 0

    fun setItems(wallets: List<Wallet>, selectedWalletIndex: Int) {
        this.wallets.clear()
        this.wallets.addAll(wallets)
        setInitialSelectedNetwork(selectedWalletIndex)
        notifyDataSetChanged()
    }

    private fun setInitialSelectedNetwork(currentWalletIndex: Int) {
        currentSelectedItem = currentWalletIndex
        previousSelectedItem = currentWalletIndex
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item__wallet, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val wallet = wallets[position]
        val isSelected = currentSelectedItem == position
        holder.apply {
            setWallet(wallet)
            if (isSelected) setSelected() else setUnselected()
            setOnItemClickedListener(position) { onItemClickedListener.invoke(it) }
        }
    }

    fun handleSelectedItem(walletIndex: Int) {
        previousSelectedItem = currentSelectedItem
        currentSelectedItem = walletIndex
        notifyItemChanged(previousSelectedItem)
        notifyItemChanged(currentSelectedItem)
    }

    override fun getItemCount(): Int = wallets.size
}