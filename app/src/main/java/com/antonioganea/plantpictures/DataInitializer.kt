package com.antonioganea.plantpictures

import java.util.ArrayList
import java.util.HashMap

class DataInitializer {

    companion object {
        fun initData(data: ArrayList<String>, images: HashMap<String, Int>) {
            data.add("Carnation")
            images.put("Carnation", R.drawable.carnation)

            data.add("Chamomille")
            images.put("Chamomille", R.drawable.chamomille)

            data.add("Daffodil")
            images.put("Daffodil", R.drawable.daffodil)

            data.add("Dandelion")
            images.put("Dandelion", R.drawable.dandelion)

            data.add("Fern")
            images.put("Fern", R.drawable.fern)

            data.add("Mint")
            images.put("Mint", R.drawable.mint)

            data.add("Pansy")
            images.put("Pansy", R.drawable.pansy)

            data.add("Rose")
            images.put("Rose", R.drawable.rose)

            data.add("Tulip")
            images.put("Tulip", R.drawable.tulip)
        }
    }
}