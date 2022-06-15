package com.jbiglion22.koreanhistorynote

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jbiglion22.koreanhistorynote.databinding.ListTitlenameBinding


class TitleNameAdapter(val datas: MutableList<TitleName>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CustomViewHolder(ListTitlenameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        /*
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_titlename, parent, false)

        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val titlename: TitleName = titlenameList.get(curPos)

                val intent = Intent(parent.context, ContentActivity::class.java)
                intent.putExtra("curPos", curPos)
                parent.context.startActivity(intent)


            }
        }

         */
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CustomViewHolder).binding
        binding.tvName.text = datas[position].name
        binding.clRoot.setOnClickListener {
            val curPos : Int = position
            val titlename: TitleName = ContentDataList.get(curPos)

            val intent = Intent(holder.itemView.context, ContentActivity::class.java)
            intent.putExtra("curPos", curPos)
            holder.itemView.context.startActivity(intent)
        }
        binding.imgbtnQuestion.setOnClickListener {
            val curPos : Int = position
            val titlename: TitleName = ContentDataList.get(curPos)

            val intent = Intent(holder.itemView.context, QuestionActivity::class.java)
            intent.putExtra("curPos", curPos)
            holder.itemView.context.startActivity(intent)
        }

        /*
        holder.name.text = titlenameList.get(position).name


        holder.

        /* 퀴즈버튼 클릭하면 */
        val btnImage = itemView.findViewById<ImageButton>(R.id.imgbtn_question)
        btnImage.setOnClickListener {
            val curPos : Int = adapterPosition
            val titlename: TitleName = com.jbiglion22.koreanhistorynote.titlenameList.get(curPos)

            val intent = Intent(parent.context, QuestionActivity::class.java)
            intent.putExtra("curPos", curPos)
            parent.context.startActivity(intent)
//           Toast.makeText(parent.context, "test", Toast.LENGTH_LONG).show()
        }

         */
    }

    /*
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name=itemView.findViewById<TextView>(R.id.tv_name)
        val btnImage=itemView.findViewById<ImageButton>(R.id.imgbtn_question)



    }
   */
    class CustomViewHolder(val binding: ListTitlenameBinding) : RecyclerView.ViewHolder(binding.root)
}

