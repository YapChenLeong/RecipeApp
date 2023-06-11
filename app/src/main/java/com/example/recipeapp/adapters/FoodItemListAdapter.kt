package com.example.recipeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.Item
import com.example.recipeapp.data.getFormattedPrice
import com.example.recipeapp.databinding.GridFoodsItemBinding
import java.lang.IllegalArgumentException

class FoodItemListAdapter(
    private val onItemClicked: (Item) -> Unit
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    private val dataset = DataSource.foods
var list : List<Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerView.ViewHolder {
//        return when(layout){
//            1 -> {
//                FoodItemCardViewHolder(
//                    GridFoodsItemBinding.inflate(
//                        LayoutInflater.from(parent.context),
//                        parent,
//                        false
//                    )
//                )
//            }
//            else -> {throw IllegalArgumentException("Invalid ViewType")
//            }
//        }

        return  FoodItemCardViewHolder(
                    GridFoodsItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FoodItemCardViewHolder) {
            holder.getBind(list[position])
        }
    }
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//
//        holder.bind
////        when(holder){
////            "Chicken" ->{
////                holder.imageView.setImageResource(R.drawable.chicken)
////            }
////            "Steak" ->{
////                holder.imageView.setImageResource(R.drawable.steak)
////            }
////            "Lamb" ->{
////                holder.imageView.setImageResource(R.drawable.lamb)
////            }
////            "Pasta" ->{
////                holder.imageView.setImageResource(R.drawable.pasta)
////            }
////            "Seafood" ->{
////                holder.imageView.setImageResource(R.drawable.seafood)
////            }
////            "Dessert" ->{
////                holder.imageView.setImageResource(R.drawable.dessert)
////            }
////        }
////        holder.textName.text = item.itemName
////        val resources = context?.resources
////        holder.textPrice.text = resources?.getString(R.string.food_price, item.itemPrice.toString())
//
//    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class FoodItemCardViewHolder(private var binding: GridFoodsItemBinding): RecyclerView.ViewHolder(binding.root){

        fun getBind(item: Item) {
            binding.textName.text = item.itemName
            binding.textPrice.text = item.getFormattedPrice()

            when(item.itemType) {
                "Chicken" -> {
                    binding.imageView.setImageResource(R.drawable.chicken)
                }
                "Steak" ->{
                    binding.imageView.setImageResource(R.drawable.steak)
                }
                "Lamb" ->{
                    binding.imageView.setImageResource(R.drawable.lamb)
                }
                "Pasta" ->{
                    binding.imageView.setImageResource(R.drawable.pasta)
                }
                "Seafood" ->{
                    binding.imageView.setImageResource(R.drawable.seafood)
                }
                "Dessert" ->{
                    binding.imageView.setImageResource(R.drawable.dessert)
                }
            }
            binding.root.setOnClickListener { onItemClicked.invoke(item) }
        }
    }


}

